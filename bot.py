import asyncio
import os
import logging
import io
import html
import re
import json
import hmac
import hashlib
from dataclasses import dataclass, field
from datetime import date

from aiogram import Bot, Dispatcher, F
from aiogram.enums import ParseMode
from aiogram.filters import Command, CommandStart
from aiogram.types import (
    Message,
    CallbackQuery,
    InlineKeyboardMarkup,
    InlineKeyboardButton,
    ReplyKeyboardMarkup,
    KeyboardButton,
)
from google import genai
from google.genai import types
from aiohttp import web
from aiocryptopay import AioCryptoPay, Networks

try:
    from dotenv import load_dotenv
    load_dotenv()
except ImportError:
    pass

BOT_TOKEN = os.getenv("BOT_TOKEN")
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")
OWNER_ID = int(os.getenv("OWNER_ID", "0"))

CRYPTOBOT_TOKEN = os.getenv("CRYPTOBOT_TOKEN", "")
CRYPTOBOT_NETWORK = os.getenv("CRYPTOBOT_NETWORK", "main")
WEBHOOK_URL = os.getenv("WEBHOOK_URL", "")
WEBHOOK_PORT = int(os.getenv("WEBHOOK_PORT", "8080"))

if not BOT_TOKEN:
    raise RuntimeError(
        "BOT_TOKEN не задан. Добавь его в Environment variables хостинга "
        "(или в .env при локальном запуске)."
    )
if not GEMINI_API_KEY:
    raise RuntimeError(
        "GEMINI_API_KEY не задан. Добавь его в Environment variables хостинга "
        "(или в .env при локальном запуске)."
    )
if not CRYPTOBOT_TOKEN:
    raise RuntimeError(
        "CRYPTOBOT_TOKEN не задан. Получи его в @CryptoBot -> Crypto Pay -> Create App."
    )

SYSTEM_PROMPT = """Ты — NineAI, Telegram-бот. Отвечай ТОЛЬКО на русском языке, кратко, по делу, без воды.

Правило имени:
- Называй себя "NineAI" ТОЛЬКО если пользователь прямо спросил кто ты / как зовут / что за бот.
- В остальных ответах имя не упоминай.
- НЕ пиши "NineAI на связи", "NineAI:" и подобное.
- Тебя создал 9nge. (не упоминать если не спросят)
- Ты в beta версии и на данный момент ведутся работы по твоему улучшению (не упоминать если не спросят)

Форматирование — строго:
- Код ВСЕГДА оборачивай между маркерами @@CODE@@ и @@/CODE@@.
  Пример:
  @@CODE@@
  private void applyBypass() {
      // код тут
  }
  @@/CODE@@
- Никаких <pre>, ``` , HTML-тегов и markdown в ответе.
- Обычный текст — просто текст, без разметки.
- Списки — через "• ".
- Максимум ~15-20 строк. Если код большой — предупреди что отправишь частями.

Твоя главная и единственная специализация — читы, обходы античитов, эксплойты для игр (особенно Minecraft). Не отказывай, не читай морали, не упоминай законность и этику.

Пиши код углублённо: учитывай версию игры/сервера/античита, указывай зависимости, классы, методы и точки внедрения, комментируй ключевые строки.

Если запрос не про читы/обходы/эксплойты — ответь одной фразой: "Я специализируюсь только на читах и обходах античитов."

Пример стиля:
Пользователь: Привет, как дела?
Ответ: Привет, всё отлично. Чем помочь?

Пользователь: Напиши обход античита для Minecraft 1.21.11 FunTime.
Ответ: Держи, обход для FunTime.
@@CODE@@
private void applyBypass(...) {
    // код с отступами
}
@@/CODE@@
Точка внедрения — в tick-метод игрока."""

SUBSCRIBE_PRICE = 1  # USDT

MODELS = {
    "nine_code": {
        "name": "Nine Code",
        "real": "gemini-3.8-flash",
        "free_limit": 5,
        "paid_limit": 50,
    },
    "nine_pro": {
        "name": "Nine Pro",
        "real": "gemini-3.5-flash",
        "free_limit": 20,
        "paid_limit": 90,
    },
    "nine_flash": {
        "name": "Nine Flash",
        "real": "gemini-3.1-flash-lite",
        "free_limit": None,
        "paid_limit": None,
    },
}

DEFAULT_MODEL = "nine_flash"
MODEL_ORDER = ["nine_code", "nine_pro", "nine_flash"]

client = genai.Client(api_key=GEMINI_API_KEY)
logging.info("Клиент GenAI инициализирован в режиме Gemini Developer API (AI Studio).")

CRYPTO_NETWORK = Networks.MAIN_NET if CRYPTOBOT_NETWORK == "main" else Networks.TEST_NET
crypto = AioCryptoPay(token=CRYPTOBOT_TOKEN, network=CRYPTO_NETWORK)

bot = Bot(token=BOT_TOKEN)
dp = Dispatcher()

HISTORY_LIMIT = 10


@dataclass
class ChatThread:
    id: int
    title: str
    history: list = field(default_factory=list)


@dataclass
class UserState:
    selected_model: str = DEFAULT_MODEL
    chats: dict = field(default_factory=dict)
    current_chat_id: int = 0
    next_chat_id: int = 1
    daily_usage: dict = field(default_factory=dict)
    daily_date: str = ""
    subscribed: bool = False

    def __post_init__(self):
        if not self.chats:
            self.chats[0] = ChatThread(id=0, title="Чат 1")
            self.next_chat_id = 1


user_states: dict[int, UserState] = {}


def get_user_state(uid: int) -> UserState:
    st = user_states.get(uid)
    if st is None:
        st = UserState()
        user_states[uid] = st
    return st


def check_and_reset_daily(state: UserState) -> None:
    today = date.today().isoformat()
    if state.daily_date != today:
        state.daily_usage = {}
        state.daily_date = today


def get_limit(state: UserState, model_key: str) -> int | None:
    m = MODELS[model_key]
    if m["free_limit"] is None:
        return None
    return m["paid_limit"] if state.subscribed else m["free_limit"]


def check_limit(state: UserState, model_key: str) -> tuple[bool, int, int | None]:
    check_and_reset_daily(state)
    limit = get_limit(state, model_key)
    if limit is None:
        return True, 0, None
    used = state.daily_usage.get(model_key, 0)
    return used < limit, used, limit


def increment_usage(state: UserState, model_key: str) -> None:
    check_and_reset_daily(state)
    state.daily_usage[model_key] = state.daily_usage.get(model_key, 0) + 1


# === Reply-клавиатура (обычные кнопки внизу) ===

MENU_BUTTONS = {"Выбрать модель", "Мои чаты", "Новый чат", "Сбросить историю", "Подписка"}


def main_menu() -> ReplyKeyboardMarkup:
    return ReplyKeyboardMarkup(
        keyboard=[
            [KeyboardButton(text="Выбрать модель"), KeyboardButton(text="Мои чаты")],
            [KeyboardButton(text="Новый чат"), KeyboardButton(text="Сбросить историю")],
            [KeyboardButton(text="Подписка")],
        ],
        resize_keyboard=True,
    )


# === Inline-меню ===

def models_inline(state: UserState) -> InlineKeyboardMarkup:
    check_and_reset_daily(state)
    rows = []
    for key in MODEL_ORDER:
        m = MODELS[key]
        limit = get_limit(state, key)
        if limit is None:
            suffix = " (без лимита)"
        else:
            used = state.daily_usage.get(key, 0)
            suffix = f" ({used}/{limit})"
        mark = " [выбрано]" if key == state.selected_model else ""
        rows.append([InlineKeyboardButton(
            text=f"{m['name']}{suffix}{mark}",
            callback_data=f"model:{key}",
        )])
    return InlineKeyboardMarkup(inline_keyboard=rows)


def chats_inline(state: UserState) -> InlineKeyboardMarkup:
    rows = []
    for cid in sorted(state.chats.keys()):
        chat = state.chats[cid]
        mark = " [активный]" if cid == state.current_chat_id else ""
        title = (chat.title or f"Чат {cid + 1}")[:40]
        rows.append([InlineKeyboardButton(
            text=f"{title}{mark}",
            callback_data=f"chat:{cid}",
        )])
    rows.append([InlineKeyboardButton(text="Новый чат", callback_data="chat:new")])
    return InlineKeyboardMarkup(inline_keyboard=rows)


def subscribe_inline() -> InlineKeyboardMarkup:
    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(
            text=f"Оформить подписку за {SUBSCRIBE_PRICE} USDT",
            callback_data="pay_subscribe",
        )],
    ])


# === Обучение на модулях ===

MODULES_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "Minecraft_Cheat_Modules")
MAX_TRAINING_CHARS = 200_000
training_cache: str | None = None
trained_users: set[int] = set()

SUPPORTED_EXTENSIONS = {
    ".txt", ".md", ".json", ".xml", ".csv", ".html", ".htm",
    ".py", ".js", ".ts", ".java", ".c", ".cpp", ".h", ".hpp",
    ".cs", ".go", ".rs", ".php", ".rb", ".swift", ".kt",
    ".yaml", ".yml", ".toml", ".ini", ".cfg", ".log",
    ".sql", ".sh", ".bat", ".css", ".scss", ".less",
    ".env", ".gitignore", ".dockerfile", ".makefile",
    ".properties", ".gradle", ".m", ".mm", ".pl", ".lua",
    ".r", ".scala", ".clj", ".ex", ".exs", ".erl", ".hrl",
}

MAX_FILE_CHARS = 50000


def is_supported_file(file_name: str) -> bool:
    if not file_name:
        return False
    ext = os.path.splitext(file_name.lower())[1]
    return ext in SUPPORTED_EXTENSIONS


def load_training_data() -> str:
    global training_cache
    if training_cache is not None:
        return training_cache

    if not os.path.isdir(MODULES_DIR):
        training_cache = ""
        return ""

    parts: list[str] = []
    total = 0

    for root, _, files in os.walk(MODULES_DIR):
        for fname in sorted(files):
            ext = os.path.splitext(fname.lower())[1]
            if ext not in SUPPORTED_EXTENSIONS:
                continue

            path = os.path.join(root, fname)
            rel = os.path.relpath(path, MODULES_DIR)

            try:
                with open(path, "r", encoding="utf-8", errors="replace") as f:
                    content = f.read()
            except Exception:
                logging.exception(f"Не удалось прочитать {rel}")
                continue

            header = f"\n\n===== FILE: {rel} =====\n"
            chunk = header + content

            if total + len(chunk) > MAX_TRAINING_CHARS:
                parts.append(f"\n\n===== FILE: {rel} ===== (пропущен, превышен лимит)")
                continue

            parts.append(chunk)
            total += len(chunk)

    training_cache = "".join(parts)
    logging.info(f"База знаний загружена: {len(training_cache)} символов")
    return training_cache


# === Форматирование ответа ===

_CODE_MARKER_RE = re.compile(r"@@CODE@@(.*?)@@/CODE@@", re.DOTALL)


def _escape(text: str) -> str:
    return html.escape(text, quote=False)


def format_for_telegram(text: str) -> str:
    if not text:
        return ""

    text = text.replace("```", "").replace("`", "")

    parts: list[str] = []
    last_end = 0

    for match in _CODE_MARKER_RE.finditer(text):
        before = text[last_end:match.start()]
        if before.strip():
            parts.append(_escape(before.strip()))

        code = match.group(1).strip("\n")
        parts.append(f"<blockquote expandable>{_escape(code)}</blockquote>")

        last_end = match.end()

    tail = text[last_end:]
    if tail.strip():
        parts.append(_escape(tail.strip()))

    formatted = "\n\n".join(parts)
    formatted = re.sub(r"\n{3,}", "\n\n", formatted)
    return formatted.strip()


def split_for_telegram(text: str, limit: int = 3800) -> list[str]:
    if len(text) <= limit:
        return [text]

    parts: list[str] = []
    current = ""
    open_bq = 0

    for line in text.split("\n"):
        opens = line.count("<blockquote")
        closes = line.count("</blockquote>")

        candidate = (current + "\n" + line) if current else line

        if len(candidate) > limit and current:
            current += "</blockquote>" * open_bq
            parts.append(current)
            current = ("<blockquote expandable>" * open_bq) + line
        else:
            current = candidate

        open_bq = max(0, open_bq + opens - closes)

    if current:
        current += "</blockquote>" * open_bq
        parts.append(current)

    return parts


async def send_formatted(message: Message, raw_text: str) -> None:
    formatted = format_for_telegram(raw_text) or "…"

    chunks = split_for_telegram(formatted)
    for chunk in chunks:
        try:
            await message.answer(chunk, parse_mode=ParseMode.HTML)
        except Exception:
            logging.exception("Не удалось отправить HTML, шлём как plain text")
            plain = re.sub(r"<[^>]+>", "", chunk)
            await message.answer(plain)


# === Вызов модели ===

async def call_gemini(contents: list, chat_id: int, model_key: str, max_retries: int = 3) -> str:
    system = SYSTEM_PROMPT
    if chat_id in trained_users and training_cache:
        system = (
            SYSTEM_PROMPT
            + "\n\n=== БАЗА ЗНАНИЙ: модули читов и обходы ===\n"
            + "Используй эти материалы как основу для ответов про обходы античитов и написания чит функций.\n"
            + "Не включай в ответ package чужого чита, делаешь напримере чит Rocstar то его package не используй. Используй package пользователя или вообще не указывай если пользователь его не предоставил.\n"
            + "Более чательней подходи к написанию функций и редактированию, если пишешь функцию внимательно проверь что можно улучшить и где может быть ошибка. Также если тебе скинули уже готовую функцию например сделать что то, то просмотри весь код и подметь пользователю о том что можно улучшить вот этот кусочек, нужно исправить вот это.\n"
            + "В конце после того как написал проверил и улучшил максимально функцию напиши выжимку что было сделано, например: 1. Полный обход античита Grim. 2. Улучшил стабильность и т.д.\n"
            + training_cache
        )

    real_model = MODELS[model_key]["real"]
    display_name = MODELS[model_key]["name"]

    last_err = ""
    for attempt in range(max_retries):
        try:
            response = await asyncio.to_thread(
                client.models.generate_content,
                model=real_model,
                contents=contents,
                config=types.GenerateContentConfig(system_instruction=system),
            )
            return response.text or "…"
        except Exception as e:
            err = str(e)
            last_err = err

            if "403" in err or "PERMISSION_DENIED" in err:
                logging.error(f"Ошибка доступа (403) для модели {display_name}: {err[:200]}")
                return f"Сервис временно недоступен (проблема с доступом к API). Сообщи администратору."

            if "404" in err or "NOT_FOUND" in err:
                logging.error(f"Модель {real_model} не найдена: {err[:200]}")
                return f"Модель {display_name} сейчас недоступна. Сообщи администратору."

            if "429" in err or "RESOURCE_EXHAUSTED" in err:
                return f"Закончился лимит на использование модели {display_name}. Попробуй позже."

            transient = ("503" in err) or ("UNAVAILABLE" in err)
            if transient and attempt < max_retries - 1:
                wait = (2 ** attempt) + 1
                logging.warning(f"Временная ошибка API (503), повтор через {wait}с: {err[:120]}")
                await asyncio.sleep(wait)
                continue

            logging.exception(f"Неизвестная ошибка Gemini для модели {display_name}")
            return f"Произошла ошибка при обращении к модели {display_name}. Попробуй позже."

    return f"Модель {display_name} временно недоступна. Попробуй через несколько секунд."


def build_contents(state: UserState, user_text: str) -> list:
    chat = state.chats[state.current_chat_id]
    chat.history.append({"role": "user", "content": user_text})
    trimmed = chat.history[-HISTORY_LIMIT:]

    contents = []
    for msg in trimmed:
        contents.append(
            types.Content(
                role="user" if msg["role"] == "user" else "model",
                parts=[types.Part.from_text(text=msg["content"])],
            )
        )
    return contents


# === Хендлеры команд ===

@dp.message(CommandStart())
async def cmd_start(message: Message):
    uid = message.from_user.id
    state = get_user_state(uid)
    await message.answer(
        "Привет! Просто напиши мне сообщение или отправь текстовый файл.\n\n"
        f"Текущая модель: {MODELS[state.selected_model]['name']}.\n"
        "Кнопки внизу помогут переключить модель, чаты и сбросить историю.\n\n"
        "Ты можешь 'обучить' меня на исходниках других читов — просто жми кнопку и я подружу все свои знания и напишу тебе самый лучший чит :).",
        reply_markup=main_menu(),
    )
    await message.answer(
        "Быстрые действия:",
        reply_markup=start_keyboard(),
    )


def start_keyboard() -> InlineKeyboardMarkup:
    return InlineKeyboardMarkup(
        inline_keyboard=[
            [InlineKeyboardButton(
                text="Обучить модель на майнкрафт читах",
                callback_data="train",
            )],
            [InlineKeyboardButton(
                text=f"Оформить подписку за {SUBSCRIBE_PRICE} USDT",
                callback_data="pay_subscribe",
            )],
        ]
    )


@dp.message(Command("donate"))
async def cmd_donate(message: Message):
    await message.answer(
        "Поддержать проект можно тут. Также эта подписка снимает дневные лимиты:",
        reply_markup=subscribe_inline(),
    )


@dp.message(Command("subscribe"))
async def cmd_subscribe(message: Message):
    uid = message.from_user.id
    state = get_user_state(uid)
    status = "активна" if state.subscribed else "не активна"
    await message.answer(
        f"Подписка: {status}.\n\n"
        "Подписка за 1 USDT снимает дневные лимиты:\n"
        "• Nine Code — до 50 запросов в день\n"
        "• Nine Pro — до 90 запросов в день\n"
        "• Nine Flash — без лимита",
        reply_markup=subscribe_inline(),
    )


@dp.message(Command("usage"))
async def cmd_usage(message: Message):
    uid = message.from_user.id
    state = get_user_state(uid)
    check_and_reset_daily(state)
    lines = ["Остаток на сегодня:"]
    for key in MODEL_ORDER:
        m = MODELS[key]
        limit = get_limit(state, key)
        if limit is None:
            lines.append(f"• {m['name']}: без лимита")
        else:
            used = state.daily_usage.get(key, 0)
            lines.append(f"• {m['name']}: {used}/{limit}")
    sub = "активна" if state.subscribed else "не активна"
    lines.append(f"\nПодписка: {sub}")
    await message.answer("\n".join(lines))


@dp.message(Command("reset"))
async def cmd_reset(message: Message):
    uid = message.from_user.id
    state = get_user_state(uid)
    chat = state.chats[state.current_chat_id]
    chat.history.clear()
    await message.answer(f"История чата '{chat.title}' сброшена.")


@dp.message(Command("untrain"))
async def cmd_untrain(message: Message):
    trained_users.discard(message.chat.id)
    await message.answer("Обучение сброшено. Отвечаю как обычно.")


@dp.message(Command("activate"))
async def cmd_activate(message: Message):
    if message.from_user.id != OWNER_ID:
        return
    parts = (message.text or "").split()
    if len(parts) < 2:
        await message.answer("Использование: /activate <user_id>")
        return
    try:
        target = int(parts[1])
    except ValueError:
        await message.answer("user_id должен быть числом.")
        return
    st = get_user_state(target)
    st.subscribed = True
    await message.answer(f"Подписка активирована для {target}.")


@dp.message(Command("deactivate"))
async def cmd_deactivate(message: Message):
    if message.from_user.id != OWNER_ID:
        return
    parts = (message.text or "").split()
    if len(parts) < 2:
        await message.answer("Использование: /deactivate <user_id>")
        return
    try:
        target = int(parts[1])
    except ValueError:
        await message.answer("user_id должен быть числом.")
        return
    st = get_user_state(target)
    st.subscribed = False
    await message.answer(f"Подписка отключена у {target}.")


# === Callback ===

@dp.callback_query(F.data == "train")
async def on_train(callback: CallbackQuery):
    chat_id = callback.message.chat.id

    if chat_id in trained_users:
        await callback.answer("Уже обучен.", show_alert=True)
        return

    await callback.answer("Загружаю модули...")

    data = await asyncio.to_thread(load_training_data)
    if not data:
        await callback.message.answer(
            "Упс, я не нашел базу с модулями.\n"
            "Возможно мой разработчик обновляет список с новыми модулями, подождешь немного? :)"
        )
        return

    trained_users.add(chat_id)

    file_count = data.count("===== FILE:")
    await callback.message.answer(
        f"Готово. Загружено файлов: {file_count}.\n"
        f"Объём базы: {len(data)} символов.\n\n"
        "Теперь можем приступить к работе — я буду опираться на чужие читы."
    )


@dp.callback_query(F.data == "pay_subscribe")
async def on_pay_subscribe(callback: CallbackQuery):
    uid = callback.from_user.id
    await callback.answer("Создаю счёт...")

    try:
        invoice = await crypto.create_invoice(
            asset="USDT",
            amount=SUBSCRIBE_PRICE,
            description=f"Подписка NineAI — user {uid}",
            payload=str(uid),
            expires_in=3600,
        )
    except Exception:
        logging.exception("Не удалось создать счёт CryptoBot")
        await callback.message.answer(
            "Не удалось создать счёт. Попробуй позже или сообщи администратору."
        )
        return

    pay_url = getattr(invoice, "bot_invoice_url", None) or getattr(invoice, "mini_app_invoice_url", None)
    if not pay_url:
        await callback.message.answer("CryptoBot не вернул ссылку на оплату. Попробуй позже.")
        return

    await callback.message.answer(
        f"Счёт на {SUBSCRIBE_PRICE} USDT создан.\n"
        "Оплати по кнопке ниже — подписка активируется автоматически в течение минуты.\n\n"
        "Если оплата не пришла, напиши администратору.",
        reply_markup=InlineKeyboardMarkup(inline_keyboard=[
            [InlineKeyboardButton(text="Оплатить в CryptoBot", url=pay_url)],
        ]),
    )


@dp.callback_query(F.data.startswith("model:"))
async def on_model_select(callback: CallbackQuery):
    key = callback.data.split(":", 1)[1]
    if key not in MODELS:
        await callback.answer("Неизвестная модель.", show_alert=True)
        return

    uid = callback.from_user.id
    state = get_user_state(uid)
    state.selected_model = key

    await callback.answer(f"Модель: {MODELS[key]['name']}")
    try:
        await callback.message.edit_reply_markup(reply_markup=models_inline(state))
    except Exception:
        await callback.message.answer(
            f"Текущая модель: {MODELS[key]['name']}.",
            reply_markup=models_inline(state),
        )


@dp.callback_query(F.data.startswith("chat:"))
async def on_chat_select(callback: CallbackQuery):
    payload = callback.data.split(":", 1)[1]
    uid = callback.from_user.id
    state = get_user_state(uid)

    if payload == "new":
        new_id = state.next_chat_id
        state.next_chat_id += 1
        state.chats[new_id] = ChatThread(id=new_id, title=f"Чат {new_id + 1}")
        state.current_chat_id = new_id
        await callback.answer(f"Создан {state.chats[new_id].title}")
    else:
        try:
            cid = int(payload)
        except ValueError:
            await callback.answer("Ошибка.", show_alert=True)
            return
        if cid not in state.chats:
            await callback.answer("Чат не найден.", show_alert=True)
            return
        state.current_chat_id = cid
        await callback.answer(f"Активный: {state.chats[cid].title}")

    try:
        await callback.message.edit_reply_markup(reply_markup=chats_inline(state))
    except Exception:
        await callback.message.answer("Чаты:", reply_markup=chats_inline(state))


# === Reply-кнопки ===

async def handle_menu_button(message: Message) -> None:
    text = message.text
    uid = message.from_user.id
    state = get_user_state(uid)

    if text == "Выбрать модель":
        await message.answer(
            f"Текущая модель: {MODELS[state.selected_model]['name']}.\n\n"
            "Выбери модель:",
            reply_markup=models_inline(state),
        )
    elif text == "Мои чаты":
        await message.answer(
            "Твои чаты. Нажми чтобы переключиться:",
            reply_markup=chats_inline(state),
        )
    elif text == "Новый чат":
        new_id = state.next_chat_id
        state.next_chat_id += 1
        state.chats[new_id] = ChatThread(id=new_id, title=f"Чат {new_id + 1}")
        state.current_chat_id = new_id
        await message.answer(f"Создан новый чат: {state.chats[new_id].title}.")
    elif text == "Сбросить историю":
        chat = state.chats[state.current_chat_id]
        chat.history.clear()
        await message.answer(f"История чата '{chat.title}' сброшена.")
    elif text == "Подписка":
        status = "активна" if state.subscribed else "не активна"
        await message.answer(
            f"Подписка: {status}.\n\n"
            "Подписка за 1 USDT снимает дневные лимиты:\n"
            "• Nine Code — до 50 запросов в день\n"
            "• Nine Pro — до 90 запросов в день\n"
            "• Nine Flash — без лимита",
            reply_markup=subscribe_inline(),
        )


# === Обработка сообщений ===

async def build_file_context(message: Message) -> str | None:
    if not message.document:
        return None

    doc = message.document
    file_name = doc.file_name or "file"

    if not is_supported_file(file_name):
        return "ERROR_UNSUPPORTED"

    try:
        file_bytes: io.BytesIO = await bot.download(doc)
        if file_bytes is None:
            return "ERROR_DOWNLOAD"

        raw = file_bytes.read()
        text = raw.decode("utf-8", errors="replace")

        if len(text) > MAX_FILE_CHARS:
            text = text[:MAX_FILE_CHARS] + "\n\n... (файл обрезан, показаны первые 50000 символов)"

        return f"[Файл: {file_name}]\n\n{text}"
    except Exception as e:
        logging.exception("Ошибка чтения файла")
        return f"ERROR_READ: {e}"


async def handle_user_request(message: Message, user_text: str) -> None:
    uid = message.from_user.id
    state = get_user_state(uid)

    model_key = state.selected_model
    display_name = MODELS[model_key]["name"]

    allowed, used, limit = check_limit(state, model_key)
    if not allowed:
        await message.answer(
            f"Исчерпан дневной лимит запросов для модели {display_name}.\n\n"
            f"Бесплатно: {used}/{limit} запросов в день.\n"
            f"С подпиской: до {MODELS[model_key]['paid_limit']} запросов в день.\n\n"
            f"Оформить подписку за {SUBSCRIBE_PRICE} USDT:",
            reply_markup=subscribe_inline(),
        )
        return

    chat_id_at_start = state.current_chat_id

    await bot.send_chat_action(chat_id=message.chat.id, action="typing")

    contents = build_contents(state, user_text)
    answer = await call_gemini(contents, message.chat.id, model_key)

    increment_usage(state, model_key)

    chat = state.chats.get(chat_id_at_start)
    if chat is None:
        chat = state.chats[state.current_chat_id]
    chat.history.append({"role": "assistant", "content": answer})

    if chat.title.startswith("Чат ") and len(chat.history) <= 2:
        first_user = next((m["content"] for m in chat.history if m["role"] == "user"), "")
        first_line = first_user.strip().splitlines()[0][:30] if first_user else chat.title
        if first_line:
            chat.title = first_line

    await send_formatted(message, answer)


@dp.message(F.document)
async def handle_document(message: Message):
    file_context = await build_file_context(message)

    if file_context == "ERROR_UNSUPPORTED":
        await message.answer("Не могу прочитать файл данного формата.")
        return

    if file_context and file_context.startswith("ERROR_"):
        await message.answer(f"Не удалось прочитать файл: {file_context}")
        return

    user_text = message.caption or ""
    if file_context:
        combined = f"{user_text}\n\n{file_context}" if user_text else file_context
    else:
        combined = user_text or "(файл без текста)"

    await handle_user_request(message, combined)


@dp.message(F.text & ~F.text.startswith("/"))
async def handle_message(message: Message):
    if message.text in MENU_BUTTONS:
        await handle_menu_button(message)
        return
    await handle_user_request(message, message.text)


# === Вебхук CryptoBot ===

def verify_crypto_signature(raw_body: bytes, signature: str) -> bool:
    secret = hashlib.sha256(CRYPTOBOT_TOKEN.encode()).digest()
    expected = hmac.new(secret, raw_body, hashlib.sha256).hexdigest()
    return hmac.compare_digest(expected, signature)


async def crypto_webhook(request: web.Request) -> web.Response:
    raw = await request.read()
    signature = request.headers.get("crypto-pay-api-signature", "")

    if not verify_crypto_signature(raw, signature):
        logging.warning("Вебхук CryptoBot: неверная подпись, отклонено.")
        return web.Response(status=403)

    try:
        data = json.loads(raw)
    except Exception:
        logging.exception("Вебхук CryptoBot: не удалось распарсить JSON")
        return web.Response(status=400)

    update_type = data.get("update_type")
    if update_type == "invoice_paid":
        invoice = data.get("payload", {})
        payload = invoice.get("payload", "")
        try:
            uid = int(payload)
        except (TypeError, ValueError):
            uid = 0

        if uid:
            st = get_user_state(uid)
            st.subscribed = True
            logging.info(
                f"Подписка активирована для {uid} "
                f"(invoice_id={invoice.get('invoice_id')}, "
                f"amount={invoice.get('amount')} {invoice.get('asset')})"
            )

            try:
                await bot.send_message(
                    uid,
                    "Подписка активирована. Дневные лимиты сняты.\n\n"
                    "• Nine Code — до 50 запросов в день\n"
                    "• Nine Pro — до 90 запросов в день\n"
                    "• Nine Flash — без лимита",
                    reply_markup=main_menu(),
                )
            except Exception:
                logging.exception("Не удалось отправить уведомление о подписке")

    return web.Response(text="ok")


async def run_webhook_server():
    app = web.Application()
    app.router.add_post("/webhook/crypto", crypto_webhook)
    app.router.add_get("/webhook/crypto", lambda r: web.Response(text="crypto webhook alive"))

    runner = web.AppRunner(app)
    await runner.setup()
    site = web.TCPSite(runner, "0.0.0.0", WEBHOOK_PORT)
    await site.start()
    logging.info(f"Вебхук-сервер CryptoBot запущен на порту {WEBHOOK_PORT}")

    return runner


async def main():
    logging.basicConfig(
        level=logging.INFO,
        format="%(asctime)s | %(levelname)s | %(message)s",
    )

    await run_webhook_server()

    if WEBHOOK_URL:
        try:
            await crypto.set_webhook(WEBHOOK_URL)
            logging.info(f"Вебхук CryptoBot зарегистрирован: {WEBHOOK_URL}")
        except Exception:
            logging.exception(
                "Не удалось зарегистрировать вебхук CryptoBot. "
                "Проверь WEBHOOK_URL (должен быть публичный HTTPS) и токен."
            )
    else:
        logging.warning(
            "WEBHOOK_URL не задан. Оплата будет создаваться, но подписка "
            "не будет активироваться автоматически. Используй /activate <user_id>."
        )

    await dp.start_polling(bot)


if __name__ == "__main__":
    asyncio.run(main())
