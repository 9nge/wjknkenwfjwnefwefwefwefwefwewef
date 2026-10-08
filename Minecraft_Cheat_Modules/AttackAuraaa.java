// Module: AttackAura
// Category: Combat
// Original obfuscated class: sg.ec.Й (й.java)
package sg.ec.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_1268;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1738;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1893;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2815;
import net.minecraft.class_2846;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_5134;
import net.minecraft.class_6880;
import net.minecraft.class_746;
import net.minecraft.class_9285;
import net.minecraft.class_9304;
import net.minecraft.class_9334;
import net.minecraft.class_2846.class_2847;
import net.minecraft.class_9285.class_9287;

public class AttackAura extends Module {
   int я;
   double д;
   ТЙ д;
   static String иI = "Builder";
   ТЙ Т;
   static String и2 = "Старый";
   static String иЫ = "Neuro";
   Ь л;
   Ч3 Д;
   ФЯ н;
   static String ЙИ = "Builder";
   static String Й西 = "Новый";
   Ту н;
   double ЙЯ;
   static String 弟н = "Polar";
   static String 弟Ш = "Legit";
   String ЙЭ;
   Фи I;
   static String иб = "ReallyWorld";
   static String иъ = "Builder";
   String иы;
   Ч3 л;
   static String и< = "Legit";
   Т4 2;
   static String Йч = "Builder";
   static String Йр = "Новый";
   static class_310 I;
   Ь у;
   Ь 必;
   static class_1309 Ш;
   Фи 2;
   static String ЙФ = "Отжимать щит";
   boolean l;
   static String Йз = "Spooky Test";
   int с;
   Ь Б;
   static String Йт = "Polar";
   Ь 弟;
   long ЙМ;
   long Йя;
   long К;
   float Йс;
   float Йп;
   float Б;
   long У;
   static long ЙО = 460L;
   static String ЙД = "Ломать щит";
   static float Йа = 0.88F;
   static float ЙС = 0.94F;
   float 必;
   static String иЖ = "Builder";
   String и诶;
   2Ш н;
   static String иГ = "Builder";
   String иг;
   Ч3 衣;
   String лВ;
   Ч3 и;
   double 7;
   static String ЙВ = "Builder";
   static String ЙЗ = "Новый";
   static String иф = "Builder";
   static String иЕ = "Старый";
   Ч3 弟;
   Ч3 В;
   static String ии = "Builder";
   static String и弟 = "Старый";
   Ч3 Г;
   double ЙЕ;
   static double Йв = 3.0;
   static double ЙЩ = 2.0;
   static double Йд = 1.5;
   double Й7;
   double ЙН;
   float ЙР;
   double ЙТ;
   static double ЙК = 2.0;
   Ч3 ъ;
   Ь l;
   static String иФ = "Builder";
   static String из = "Старый";
   Ь и;
   ТЙ 7;
   static String Йб = "Builder";
   static String Йо = "Старый";
   static String иЮ = "Builder";
   String иl;
   boolean ш;
   Ч3 Я;
   static String ит = "Builder";
   static String иМ = "Старый";
   static String ив = "Builder";
   static String иЩ = "Старый";
   static String иХ = "Builder";
   String иж;
   Ч3 З;
   Ь O;
   Ч3 п;
   Ч3 П;
   Ч3 О;
   static String лб = "Legit";
   static String ло = "Post";
   static String Й5 = "Polar";
   static String Йн = "Legit";
   Ч3 Ф;
   static String ЙШ = "Polar";
   static String Йк = "Builder";
   static String ЙЪ = "Новый";
   Ч3 е;
   ТЙ Р;
   Ч3 э;
   String 弟5;
   static String Йг = "Builder";
   static String Й3 = "Новый";
   static String ЙЧ = "Builder";
   static String ЙЖ = "Новый";
   static String ил = "Builder";
   static String иЙ = "Старый";
   Ч3 т;
   static String иЦ = "Builder";
   String ич;
   static String йЯ = "AttackAura";
   static String йЧ = "Автоматически атакует сущностей в радиусе действия, выбирая цели и вращая камеру";
   static String йЖ = "Обход";
   static String й诶;
   static String йЦ;
   static String йч;
   static String йр;
   static String й4;
   static String йц;
   static String йИ;
   static String й西;
   static String й1 = "Приоритет";
   static String й0 = "Оптимальный";
   static String й>;
   static String йХ = "Дистанция";
   static String йж = "Здоровье";
   static String йГ = "Угол поворота";
   static String йг = "Цели";
   static String й3 = "Игроки";
   static String йю = "Голые";
   static String йэ = "Мобы";
   static String йе = "Друзья";
   static String й衣 = "Опции";
   static String йВ = "Только криты";
   static String йЗ = "Ломать щит";
   static String йк = "Отжимать щит";
   static String йЪ = "Тип коррекции";
   static String й6 = "Свободная";
   static String й9 = "Свободная";
   static String й_ = "Строгая";
   ТЙ Н;
   static String й< = "Отображение цели";
   static String йЫ = "Призраки";
   static String й8 = "Ромб";
   static String йЬ = "Призраки";
   static String йщ = "Кристаллы";
   static String йб = "Кружок";
   static String йо = "Пентаграмма";
   static String П5 = "Сияние";
   static String Пн = "Не отображать";
   static String ПШ = "Дистанция аттаки";
   static float ПЭ = 3.0F;
   static float ПI = 5.0F;
   static float П2 = 0.1F;
   static String Пь = "Дистанция ротации";
   static float Пм = 1.5F;
   static float Пф = 5.0F;
   static float ПЕ = 0.05F;
   Ч3 з;
   static String Пв = "Элитра ротация";
   static float ПЩ = 30.0F;
   static float Пд = 30.0F;
   static float П7 = 0.05F;
   static String ПН = "Проверка на луч";
   static String ПР = "Только с хотбара";
   static String ПТ = "Только с пробелом";
   Ь Й;
   static String ПК = "Синхронизация с ТПС";
   static String ПУ = "Легитное кд ударов";
   static String ПЛ = "Не бить если ешь";
   static String ПФ = "Бить через стены";
   static String Пз = "Бить через стены RW";
   static String Пт = "Neuro Train";
   static String ПМ = "Yaw Speed";
   static String Пя = "Pitch Speed";
   static String Пс = "Hit Point";
   static String Пп = "Rotation Point";
   static String ПО = "Behavior";
   Фи ь;
   static String ПД = "Прогресс обучения";
   Ь х;
   static String Па = "Neuro: семплов до готовности";
   static float ПС = 30000.0F;
   static float Пй = 1000.0F;
   static float ПП = 100000.0F;
   static float ПO = 1000.0F;
   Ч3 М;
   static String Пл = "Neuro: отводка при разрыве";
   Ь ъ;
   static String ПЙ = "Legit: отводка при разрыве";
   Ь ы;
   static String Пи = "Neuro Reset";
   Ь Ю;
   static String П弟 = "Версия билдера";
   static String Пу = "Старый";
   static String П必 = "Старый";
   static String ПБ = "Новый";
   static String Пх = "Земля: клиент лук";
   static String Пъ = "Земля: высота точки %";
   static float Пы = 72.0F;
   static float ПЮ = 15.0F;
   static float Пl = 100.0F;
   Ч3 я;
   static String Пш = "Земля: смещение плеча %";
   static float ПА = 100.0F;
   static float ПЯ = 150.0F;
   Ч3 с;
   static String ПЧ = "Земля: разброс вбок %";
   static float ПЖ = 38.0F;
   static float П诶 = 120.0F;
   static String ПЦ = "Земля: разброс вглубь %";
   static float Пч = 42.0F;
   static float Пр = 120.0F;
   static String П4 = "Земля: смена точки мс мин";
   static float Пц = 140.0F;
   static float ПИ = 40.0F;
   static float П西 = 900.0F;
   static float П1 = 5.0F;
   static String П0 = "Земля: смена точки мс макс";
   static float П> = 380.0F;
   static float ПХ = 60.0F;
   static float Пж = 1400.0F;
   static float ПГ = 5.0F;
   Ч3 а;
   static String Пг = "Земля: инерция yaw %";
   static float П3 = 78.0F;
   static float Пю = 30.0F;
   static float Пэ = 98.0F;
   Ч3 С;
   static String Пе = "Земля: инерция pitch %";
   static float П衣 = 74.0F;
   static float ПВ = 30.0F;
   static float ПЗ = 98.0F;
   Ч3 й;
   static String Пк = "Земля: сила слежения yaw %";
   static float ПЪ = 38.0F;
   static float П6 = 100.0F;
   static String П9 = "Земля: сила слежения pitch %";
   static float П_ = 30.0F;
   static float П< = 100.0F;
   Ч3 O;
   static String ПЫ = "Земля: макс шаг yaw °/тик";
   static float П8 = 6.0F;
   static float ПЬ = 0.3F;
   static float Пщ = 25.0F;
   static float Пб = 0.05F;
   static String По = "Земля: макс шаг pitch °/тик";
   static float O5 = 2.9F;
   static float Oн = 0.15F;
   static float OШ = 15.0F;
   static float OЭ = 0.05F;
   Ч3 Й;
   static String OI = "Земля: ускорение yaw °/тик²";
   static float O2 = 0.42F;
   static float Oь = 0.05F;
   static float Oм = 1.2F;
   static float Oф = 0.01F;
   static String OЕ = "Земля: ускорение pitch °/тик²";
   static float Oв = 0.28F;
   static float OЩ = 0.03F;
   static float Oд = 0.9F;
   static float O7 = 0.01F;
   static String OН = "Земля: мёртвая зона pitch °";
   static float OР = 0.65F;
   static float OТ = 4.0F;
   static float OК = 0.05F;
   Ч3 у;
   static String OУ = "Земля: джиттер yaw %";
   static float OЛ = 14.0F;
   static float OФ = 100.0F;
   Ч3 必;
   static String Oз = "Земля: джиттер pitch %";
   static float Oт = 11.0F;
   static float OМ = 100.0F;
   Ч3 Б;
   static String Oя = "Земля: отклик джиттера %";
   static float Oс = 48.0F;
   static float Oп = 10.0F;
   static float OО = 95.0F;
   Ч3 х;
   static String OД = "Земля: усиление от камеры %";
   static float Oа = 32.0F;
   static float OС = 100.0F;
   static String Oй = "Земля: хаос / вариации %";
   static float OП = 14.0F;
   static float OO = 55.0F;
   Ч3 ы;
   static String Oл = "Земля: шанс микропаузы %";
   static float OЙ = 6.0F;
   static float Oи = 28.0F;
   Ч3 Ю;
   static String O弟 = "Земля: сглаживание jerk yaw";
   static float Oу = 0.22F;
   static float O必 = 0.03F;
   static float OБ = 0.95F;
   static float Oх = 0.01F;
   Ч3 l;
   static String Oъ = "Земля: сглаживание jerk pitch";
   static float Oы = 0.14F;
   static float OЮ = 0.02F;
   static float Ol = 0.75F;
   static float Oш = 0.01F;
   Ч3 ш;
   static String OА = "Земля: приоритет ротации";
   static float OЯ = 5.0F;
   Ч3 А;
   static String OЧ = "Земля: таймаут ротации";
   static float OЖ = 40.0F;
   static String O诶 = "Земля: шаг по GCD";
   Ь ш;
   static String OЦ = "Элитра: клиент лук";
   Ь А;
   static String Oч = "Элитра: сила предикта";
   static float Oр = 2.15F;
   static float O4 = 0.2F;
   static float Oц = 5.5F;
   static float OИ = 0.05F;
   Ч3 Ч;
   static String O西 = "Элитра: шум %";
   static float O1 = 24.0F;
   static float O0 = 100.0F;
   Ч3 Ж;
   static String O> = "Элитра: смесь look/move %";
   static float OХ = 52.0F;
   static float Oж = 100.0F;
   Ч3 诶;
   static String OГ = "Элитра: высота точки %";
   static float Oг = 50.0F;
   static float O3 = 100.0F;
   Ч3 Ц;
   static String Oю = "Элитра: скорость yaw (прицел)";
   static float Oэ = 200.0F;
   static float Oе = 5.0F;
   static float O衣 = 360.0F;
   Ч3 ч;
   static String OВ = "Элитра: скорость pitch (прицел)";
   static float OЗ = 200.0F;
   static float Oк = 5.0F;
   static float OЪ = 360.0F;
   Ч3 р;
   static String O6 = "Элитра: возврат yaw";
   static float O9 = 200.0F;
   static float O_ = 5.0F;
   static float O< = 360.0F;
   Ч3 4;
   static String OЫ = "Элитра: возврат pitch";
   static float O8 = 200.0F;
   static float OЬ = 5.0F;
   static float Oщ = 360.0F;
   Ч3 ц;
   static String Oб = "Элитра: таймаут ротации";
   static float Oо = 14.0F;
   static float л5 = 40.0F;
   Ч3 И;
   static String лн = "Элитра: порог скорости цели";
   static float лШ = 0.07F;
   static float лЭ = 0.02F;
   static float лI = 0.35F;
   static float л2 = 0.01F;
   Ч3 西;
   static String ль = "Элитра: приоритет ротации";
   static float лм = 5.0F;
   Ч3 1;
   static String лф = "Элитра: + предикт ElytraTarget";
   Ь Я;
   static String лЕ = "Земля: клиент лук";
   Ь Ч;
   static String лв = "Земля: высота точки";
   static float лЩ = 80.0F;
   static float лд = 20.0F;
   static float л7 = 100.0F;
   Ч3 0;
   static String лН = "Земля: упреждение скорости";
   static float лР = 1.2F;
   static float лТ = 4.0F;
   static float лК = 0.05F;
   Ч3 >;
   static String лУ = "Земля: отклонение от точки по яву";
   static float лЛ = 0.22F;
   static float лФ = 0.05F;
   static float лз = 0.6F;
   static float лт = 0.01F;
   Ч3 Х;
   static String лМ = "Земля: отклонение от точки по питчу";
   static float ля = 0.18F;
   static float лс = 0.05F;
   static float лп = 0.6F;
   static float лО = 0.01F;
   Ч3 ж;
   static String лД = "Земля: макс шаг yaw";
   static float ла = 8.0F;
   static float лС = 0.5F;
   static float лй = 25.0F;
   static float лП = 0.1F;
   static String лO = "Земля: макс шаг pitch";
   static float лл = 4.0F;
   static float лЙ = 0.3F;
   static float ли = 15.0F;
   static float л弟 = 0.1F;
   Ч3 г;
   static String лу = "Земля: шум yaw";
   static float л必 = 0.6F;
   static float лБ = 3.0F;
   static float лх = 0.05F;
   Ч3 3;
   static String лъ = "Земля: шум pitch";
   static float лы = 0.35F;
   static float лЮ = 0.05F;
   Ч3 ю;
   static String лl = "Земля: дыхание частота";
   static float лш = 1.8F;
   static float лА = 0.3F;
   static float лЯ = 4.5F;
   static float лЧ = 0.05F;
   static String лЖ = "Земля: дыхание амплитуда";
   static float л诶 = 0.4F;
   static float лЦ = 0.05F;
   static String лч = "Земля: микропауза";
   static float лр = 30.0F;
   static float л4 = 100.0F;
   static String лц = "Земля: мёртвая зона";
   static float лИ = 0.4F;
   static float л西 = 3.0F;
   static float л1 = 0.05F;
   static String л0 = "Земля: приоритет ротации";
   static float л> = 5.0F;
   static String лХ = "Земля: таймаут ротации";
   static float лж = 40.0F;
   Ч3 к;
   static String лГ = "Земля: шаг по GCD";
   Ь Ж;
   static float лг = 0.95F;
   static float л3 = 0.94F;
   static String лю = "Neuro";
   static String ЙЬ = "Builder";
   static String Йщ = "Старый";
   static String иД = "Builder";
   static String иа = "Старый";
   static String и西 = "Builder";
   String и1;
   static String ир = "Builder";
   String и4;
   static double ЙУ = 2.0;
   static double ЙЛ = 90.0;
   static String ия = "Builder";
   static String ис = "Старый";
   static String иШ = "Builder";
   static String иЭ = "Старый";
   static String Йж = "Builder";
   static String ЙГ = "Новый";
   static String ип = "Builder";
   static String иО = "Старый";
   static String ид = "Builder";
   static String и7 = "Старый";
   static String и0 = "Builder";
   String и>;
   static String Й> = "Builder";
   static String ЙХ = "Новый";
   static String иЬ = "Neuro";
   static String Й4 = "Builder";
   static String Йц = "Новый";
   static String иц = "Builder";
   String иИ;
   static String ищ = "Neuro";
   double Йй;
   double ЙП;
   float ЙI;
   static String иТ = "Builder";
   static String иК = "Старый";
   static String Йе = "Builder";
   static String Й衣 = "Новый";
   static String иУ = "Builder";
   static String иЛ = "Старый";
   static String и8 = "Neuro";
   static double л衣 = 20.0;
   static String ЙO = "Ломать щит";
   float Йл;
   long ЙЙ;
   long Йи;
   static String и3 = "Builder";
   String ию;
   static String иС = "Builder";
   static String ий = "Старый";
   static String ЙЫ = "Builder";
   static String Й8 = "Новый";
   static String лщ = "Polar";
   static String лэ = "Свободная";
   static double ле = 90.0;
   static String иН = "Builder";
   static String иР = "Старый";
   static String иь = "Builder";
   static String им = "Старый";
   static String и5 = "Builder";
   static String ин = "Старый";
   static String ио = "Только криты";
   static String Й6 = "Builder";
   static String Й9 = "Новый";
   static String иБ = "Builder";
   String их;
   static String иЗ = "Builder";
   String ик;
   static String иЪ = "Builder";
   String и6;
   static String иш = "Builder";
   String иА;
   static String Й1 = "Builder";
   static String Й0 = "Новый";
   static String иу = "Builder";
   static String и必 = "Старый";
   static String Й诶 = "Builder";
   static String ЙЦ = "Новый";
   static String иэ = "Builder";
   String ие;
   String Й弟;
   static String Йу = "Игроки";
   static String Й必 = "Голые";
   static String ЙБ = "Мобы";
   double Йы;
   double ЙЮ;
   double Йl;
   double Йш;
   double ЙА;
   static String лЗ = "Neuro";
   static float лк = 200.0F;
   static float лЪ = 5.0F;
   static float л6 = 14.0F;
   static float л9 = 0.5F;
   static String л_ = "Neuro: %d / %d  (%d%%)";
   static float л< = 100.0F;
   static float лЫ = 0.5F;
   static float л8 = 14.0F;
   static float лЬ = 0.22F;
   static float Йх = 0.9F;
   static float Йъ = 0.985F;
   static String иП = "Builder";
   static String иO = "Старый";
   String Й2;
   String Йь;
   String Йм;
   String Йф;
   static String и_ = "Neuro";
   static String Йю = "Builder";
   static String Йэ = "Новый";
   static String иЯ = "Builder";
   String иЧ;
   static String Й_ = "Builder";
   static String Й< = "Новый";
   static String и衣 = "Builder";
   String иВ;
   static String и9 = "Builder";

   public AttackAura() {
      super(йЯ, йЧ, sg.ec.Пй.Combat);
      this.д = new ТЙ(йЖ, й诶, йЦ, йч, йр, й4, йц, йИ, й西);
      this.7 = new ТЙ(й1, й0, й>, йХ, йж, йГ);
      this.I = new Фи(йг, new Ь(й3, true), new Ь(йю, true), new Ь(йэ, false), new Ь(йе, false));
      this.2 = new Фи(й衣, new Ь(йВ, true), new Ь(йЗ, false), new Ь(йк, false));
      this.Н = new ТЙ(йЪ, й6, й9, й_);
      this.Р = new ТЙ(й<, йЫ, й8, йЬ, йщ, йб, йо, П5, Пн);
      this.Ф = new Ч3(ПШ, ПЭ, 2.0F, ПI, П2);
      this.з = new Ч3(Пь, Пм, 0.0F, Пф, ПЕ);
      this.т = new Ч3(Пв, ПЩ, 0.0F, Пд, П7);
      this.O = new Ь(ПН, false, () -> !this.д.Я(弟н) && !this.д.Я(弟Ш));
      this.л = new Ь(ПР, false, () -> this.2.4(弟5));
      this.Й = new Ь(ПТ, true, () -> this.2.4(ио));
      this.и = new Ь(ПК, false);
      this.弟 = new Ь(ПУ, false);
      this.у = new Ь(ПЛ, false);
      this.必 = new Ь(ПФ, true);
      this.Б = new Ь(Пз, true, () -> this.д.Я(иб));
      this.ь = new Фи(Пт, () -> this.д.Я(ищ), new Ь(ПМ, true), new Ь(Пя, true), new Ь(Пс, true), new Ь(Пп, true), new Ь(ПО, true));
      this.х = new Ь(ПД, true, () -> this.д.Я(иЬ));
      this.М = new Ч3(Па, ПС, Пй, ПП, ПO, () -> this.д.Я(и8));
      this.ъ = new Ь(Пл, true, () -> this.д.Я(иЫ));
      this.ы = new Ь(ПЙ, true, () -> this.д.Я(и<));
      this.Ю = new Ь(Пи, false, () -> this.д.Я(и_));
      this.Т = new ТЙ(П弟, Пу, () -> this.д.Я(и9), П必, ПБ);
      this.l = new Ь(Пх, true, () -> this.д.Я(иЪ) && this.Т.Я(и6));
      this.я = new Ч3(Пъ, Пы, ПЮ, Пl, 1.0F, () -> this.д.Я(иЗ) && this.Т.Я(ик));
      this.с = new Ч3(Пш, ПА, 0.0F, ПЯ, 1.0F, () -> this.д.Я(и衣) && this.Т.Я(иВ));
      this.п = new Ч3(ПЧ, ПЖ, 0.0F, П诶, 1.0F, () -> this.д.Я(иэ) && this.Т.Я(ие));
      this.О = new Ч3(ПЦ, Пч, 0.0F, Пр, 1.0F, () -> this.д.Я(и3) && this.Т.Я(ию));
      this.Д = new Ч3(П4, Пц, ПИ, П西, П1, () -> this.д.Я(иГ) && this.Т.Я(иг));
      this.а = new Ч3(П0, П>, ПХ, Пж, ПГ, () -> this.д.Я(иХ) && this.Т.Я(иж));
      this.С = new Ч3(Пг, П3, Пю, Пэ, 1.0F, () -> this.д.Я(и0) && this.Т.Я(и>));
      this.й = new Ч3(Пе, П衣, ПВ, ПЗ, 1.0F, () -> this.д.Я(и西) && this.Т.Я(и1));
      this.П = new Ч3(Пк, ПЪ, 2.0F, П6, 1.0F, () -> this.д.Я(иц) && this.Т.Я(иИ));
      this.O = new Ч3(П9, П_, 2.0F, П<, 1.0F, () -> this.д.Я(ир) && this.Т.Я(и4));
      this.л = new Ч3(ПЫ, П8, ПЬ, Пщ, Пб, () -> this.д.Я(иЦ) && this.Т.Я(ич));
      this.Й = new Ч3(По, O5, Oн, OШ, OЭ, () -> this.д.Я(иЖ) && this.Т.Я(и诶));
      this.и = new Ч3(OI, O2, Oь, Oм, Oф, () -> this.д.Я(иЯ) && this.Т.Я(иЧ));
      this.弟 = new Ч3(OЕ, Oв, OЩ, Oд, O7, () -> this.д.Я(иш) && this.Т.Я(иА));
      this.у = new Ч3(OН, OР, 0.0F, OТ, OК, () -> this.д.Я(иЮ) && this.Т.Я(иl));
      this.必 = new Ч3(OУ, OЛ, 0.0F, OФ, 1.0F, () -> this.д.Я(иъ) && this.Т.Я(иы));
      this.Б = new Ч3(Oз, Oт, 0.0F, OМ, 1.0F, () -> this.д.Я(иБ) && this.Т.Я(их));
      this.х = new Ч3(Oя, Oс, Oп, OО, 1.0F, () -> this.д.Я(иу) && this.Т.Я(и必));
      this.ъ = new Ч3(OД, Oа, 0.0F, OС, 1.0F, () -> this.д.Я(ии) && this.Т.Я(и弟));
      this.ы = new Ч3(Oй, OП, 0.0F, OO, 1.0F, () -> this.д.Я(ил) && this.Т.Я(иЙ));
      this.Ю = new Ч3(Oл, OЙ, 0.0F, Oи, 1.0F, () -> this.д.Я(иП) && this.Т.Я(иO));
      this.l = new Ч3(O弟, Oу, O必, OБ, Oх, () -> this.д.Я(иС) && this.Т.Я(ий));
      this.ш = new Ч3(Oъ, Oы, OЮ, Ol, Oш, () -> this.д.Я(иД) && this.Т.Я(иа));
      this.А = new Ч3(OА, 0.0F, 0.0F, OЯ, 1.0F, () -> this.д.Я(ип) && this.Т.Я(иО));
      this.Я = new Ч3(OЧ, 0.0F, 0.0F, OЖ, 1.0F, () -> this.д.Я(ия) && this.Т.Я(ис));
      this.ш = new Ь(O诶, true, () -> this.д.Я(ит) && this.Т.Я(иМ));
      this.А = new Ь(OЦ, false, () -> this.д.Я(иФ) && this.Т.Я(из));
      this.Ч = new Ч3(Oч, Oр, O4, Oц, OИ, () -> this.д.Я(иУ) && this.Т.Я(иЛ));
      this.Ж = new Ч3(O西, O1, 0.0F, O0, 1.0F, () -> this.д.Я(иТ) && this.Т.Я(иК));
      this.诶 = new Ч3(O>, OХ, 0.0F, Oж, 1.0F, () -> this.д.Я(иН) && this.Т.Я(иР));
      this.Ц = new Ч3(OГ, Oг, 0.0F, O3, 1.0F, () -> this.д.Я(ид) && this.Т.Я(и7));
      this.ч = new Ч3(Oю, Oэ, Oе, O衣, 1.0F, () -> this.д.Я(ив) && this.Т.Я(иЩ));
      this.р = new Ч3(OВ, OЗ, Oк, OЪ, 1.0F, () -> this.д.Я(иф) && this.Т.Я(иЕ));
      this.4 = new Ч3(O6, O9, O_, O<, 1.0F, () -> this.д.Я(иь) && this.Т.Я(им));
      this.ц = new Ч3(OЫ, O8, OЬ, Oщ, 1.0F, () -> this.д.Я(иI) && this.Т.Я(и2));
      this.И = new Ч3(Oб, Oо, 1.0F, л5, 1.0F, () -> this.д.Я(иШ) && this.Т.Я(иЭ));
      this.西 = new Ч3(лн, лШ, лЭ, лI, л2, () -> this.д.Я(и5) && this.Т.Я(ин));
      this.1 = new Ч3(ль, 0.0F, 0.0F, лм, 1.0F, () -> this.д.Я(Йб) && this.Т.Я(Йо));
      this.Я = new Ь(лф, true, () -> this.д.Я(ЙЬ) && this.Т.Я(Йщ));
      this.Ч = new Ь(лЕ, true, () -> this.д.Я(ЙЫ) && this.Т.Я(Й8));
      this.0 = new Ч3(лв, лЩ, лд, л7, 1.0F, () -> this.д.Я(Й_) && this.Т.Я(Й<));
      this.> = new Ч3(лН, лР, 0.0F, лТ, лК, () -> this.д.Я(Й6) && this.Т.Я(Й9));
      this.Х = new Ч3(лУ, лЛ, лФ, лз, лт, () -> this.д.Я(Йк) && this.Т.Я(ЙЪ));
      this.ж = new Ч3(лМ, ля, лс, лп, лО, () -> this.д.Я(ЙВ) && this.Т.Я(ЙЗ));
      this.Г = new Ч3(лД, ла, лС, лй, лП, () -> this.д.Я(Йе) && this.Т.Я(Й衣));
      this.г = new Ч3(лO, лл, лЙ, ли, л弟, () -> this.д.Я(Йю) && this.Т.Я(Йэ));
      this.3 = new Ч3(лу, л必, 0.0F, лБ, лх, () -> this.д.Я(Йг) && this.Т.Я(Й3));
      this.ю = new Ч3(лъ, лы, 0.0F, 2.0F, лЮ, () -> this.д.Я(Йж) && this.Т.Я(ЙГ));
      this.э = new Ч3(лl, лш, лА, лЯ, лЧ, () -> this.д.Я(Й>) && this.Т.Я(ЙХ));
      this.е = new Ч3(лЖ, л诶, 0.0F, 2.0F, лЦ, () -> this.д.Я(Й1) && this.Т.Я(Й0));
      this.衣 = new Ч3(лч, лр, 0.0F, л4, 1.0F, () -> this.д.Я(ЙИ) && this.Т.Я(Й西));
      this.В = new Ч3(лц, лИ, 0.0F, л西, л1, () -> this.д.Я(Й4) && this.Т.Я(Йц));
      this.З = new Ч3(л0, 0.0F, 0.0F, л>, 1.0F, () -> this.д.Я(Йч) && this.Т.Я(Йр));
      this.к = new Ч3(лХ, 0.0F, 0.0F, лж, 1.0F, () -> this.д.Я(Й诶) && this.Т.Я(ЙЦ));
      this.Ж = new Ь(лГ, true, () -> this.д.Я(ЙЧ) && this.Т.Я(ЙЖ));
      this.К = 0L;
      this.必 = лг;
      this.д = 0.0;
      this.7 = 0.0;
      this.н = new 2Ш();
      this.н = new ФЯ();
      this.н = new Ту();
      this.2 = new Т4();
      this.я = 0;
      this.Б = л3;
      this.У = 0L;
      if (this.д.Я(лю)) {
         Фа.ъФ();
      }

      this.ф(
         new ФЮ[]{
            this.д,
            this.I,
            this.7,
            this.Н,
            this.2,
            this.Р,
            this.ь,
            this.х,
            this.М,
            this.ъ,
            this.ы,
            this.Ю,
            this.Т,
            this.l,
            this.я,
            this.с,
            this.п,
            this.О,
            this.Д,
            this.а,
            this.С,
            this.й,
            this.П,
            this.O,
            this.л,
            this.Й,
            this.и,
            this.弟,
            this.у,
            this.必,
            this.Б,
            this.х,
            this.ъ,
            this.ы,
            this.Ю,
            this.l,
            this.ш,
            this.А,
            this.Я,
            this.ш,
            this.А,
            this.Ч,
            this.Ж,
            this.诶,
            this.Ц,
            this.ч,
            this.р,
            this.4,
            this.ц,
            this.И,
            this.西,
            this.1,
            this.Я,
            this.Ч,
            this.0,
            this.>,
            this.Х,
            this.ж,
            this.Г,
            this.г,
            this.3,
            this.ю,
            this.э,
            this.е,
            this.衣,
            this.В,
            this.З,
            this.к,
            this.Ж,
            this.Ф,
            this.з,
            this.O,
            this.Й,
            this.и,
            this.弟,
            this.у,
            this.必,
            this.Б
         }
      );
   }

   public int о() {
      return this.я;
   }

   public double Ы() {
      return this.д;
   }

   public Ь Ы() {
      return this.л;
   }

   public Ч3 р() {
      return this.Д;
   }

   private boolean ЕЩ() {
      return this.н.и(this);
   }

   public Пд г() {
      return this.н.7() == null ? null : this.н.7().必();
   }

   private double I(class_1309 var1) {
      double var2 = (double)(var1.method_6032() + var1.method_6067());
      if (var1 instanceof class_1657) {
         class_1657 var4 = (class_1657)var1;
         double var5 = this.П(var4);
         return var2 * (1.0 + var5 / ЙЯ);
      } else {
         return var2;
      }
   }

   public boolean Ев() {
      return this.д.Я(ЙЭ);
   }

   public Фи с() {
      return this.I;
   }

   private double П(class_1309 var1) {
      if (var1 instanceof class_1657) {
         class_1657 var2 = (class_1657)var1;
         double var3 = 0.0;

         for (class_1799 var6 : var2.method_5661()) {
            if (var6.method_7909() instanceof class_1738) {
               var3 += this.г(var6);
            }
         }

         return var3;
      } else {
         return (double)var1.method_6096();
      }
   }

   public Ч3 М() {
      return this.л;
   }

   public Т4 с() {
      return this.2;
   }

   private void ЕЧ() {
      if (I.field_1724 != null && I.field_1687 != null && I.field_1761 != null) {
         if (!(Boolean)this.у.о() || !I.field_1724.method_6115() || I.field_1724.method_6079().method_7909().equals(class_1802.field_8255)) {
            if ((Boolean)this.必.о() || I.field_1724.method_6057(Ш)) {
               if (!(I.field_1724.method_5739(Ш) > this.ЕЕ())) {
                  boolean var1 = I.field_1724.method_5799() || I.field_1724.method_5771() || I.field_1724.method_5681() || I.field_1724.method_6128();
                  if (this.2.4(ЙФ) && I.field_1724.method_6039()) {
                     I.field_1761.method_2897(I.field_1724);
                  }

                  if (!var1 && I.field_1724.method_5624()) {
                     this.l = true;
                     if (this.д.Я(Йз)) {
                        this.с = 1;
                     }

                     if (((sg.mx.9)I.field_1724).getLastSprinting()) {
                        return;
                     }
                  }

                  if (Ш != null && (Boolean)this.Б.о() && !I.field_1724.method_6057(Ш)) {
                     this.ЕУ();
                  }

                  if (!this.д.Я(Йт)) {
                     if ((Boolean)this.弟.о()) {
                        this.К = System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(ЙМ, Йя);
                        this.Б = ThreadLocalRandom.current().nextFloat(Йс, Йп);
                        this.У = 0L;
                     } else {
                        this.К = System.currentTimeMillis() + ЙО;
                     }
                  }

                  this.н.ю(this, Ш);
                  I.field_1761.method_2918(I.field_1724, Ш);
                  ЧШ var2 = (ЧШ)sg.ec.Ч.getInstance().getModuleManager().ь(ЧШ.class);
                  if (var2 != null && var2.7()) {
                     var2.5(Ш);
                  }

                  I.field_1724.method_6104(class_1268.field_5808);
                  if (this.2.4(ЙД)) {
                     this.ЕБ();
                  }

                  this.2.кЬ();
                  this.я++;
                  this.必 = ThreadLocalRandom.current().nextFloat(Йа, ЙС);
               }
            }
         }
      }
   }

   public 2Ш г() {
      return this.н;
   }

   public Ч3 д() {
      return this.衣;
   }

   public void ъ(Т8 var1) {
      Пф var2 = (Пф)sg.ec.Ч.getInstance().getModuleManager().ь(Пф.class);
      if (var2 != null
         && var2.7()
         && var2.я().Я(лВ)
         && I.field_1724.method_6059(class_1294.field_5906)
         && !I.field_1724.method_5799()
         && I.field_1724.field_6017 > 0.0F
         && I.field_1724.field_6017 < 1.0F) {
         if (Ш != null) {
            if (this.ЕЩ() && this.Ед() && !this.н.7(this, Ш)) {
               this.ЕЧ();
            }
         } else {
            this.К = System.currentTimeMillis();
         }
      }
   }

   public Ь б() {
      return this.Б;
   }

   public Ч3 б() {
      return this.и;
   }

   public void I(double var1) {
      this.7 = var1;
   }

   public void г(2х var1) {
      this.ЕЩ();
   }

   public Ч3 З() {
      return this.弟;
   }

   public Ч3 Г() {
      return this.В;
   }

   public Ч3 е() {
      return this.Г;
   }

   private double с(class_1309 var1) {
      if (I.field_1724 == null) {
         return ЙЕ;
      } else {
         double var2 = 0.0;
         var2 += (double)I.field_1724.method_5739(var1) * Йв;
         var2 += this.л(var1) * ЙЩ;
         var2 += this.I(var1) * Йд;
         var2 += this.П(var1) * 1.0;
         if (var1 instanceof class_1657) {
            class_1657 var4 = (class_1657)var1;
            if (var4.method_6039()) {
               var2 += Й7;
            }

            if (var4.method_6096() == 0) {
               var2 -= ЙН;
            }

            if (var4.method_6032() <= ЙР) {
               var2 -= ЙТ;
            }
         }

         if (I.field_1724.method_6057(var1)) {
            var2 -= ЙК;
         }

         return var2;
      }
   }

   public Ч3 _/* $VF was: 8*/() {
      return this.ъ;
   }

   public void _(int var1) {
      this.я = var1;
   }

   public Ь м() {
      return this.l;
   }

   public void _/* $VF was: 9*/(boolean var1) {
      this.l = var1;
   }

   public Ь _/* $VF was: <*/() {
      return this.и;
   }

   public ТЙ AttackAura() {
      return this.7;
   }

   public long I() {
      return this.У;
   }

   private void Ед() {
      Пд var1 = this.г();
      if (var1 != null && Ш != null) {
         if (!this.ш) {
            this.н.4(this, Ш);
         }

         if (this.ЕЩ() && var1.И(this) && var1.И(this, Ш) && !this.н.7(this, Ш)) {
            this.ЕЧ();
         }
      } else {
         if (var1 != null) {
            var1.йв();
         }
      }
   }

   public Ч3 з() {
      return this.Я;
   }

   public Ч3 Ч() {
      return this.З;
   }

   public Ь х() {
      return this.O;
   }

   public boolean г(double var1) {
      return false;
   }

   public Ч3 I() {
      return this.п;
   }

   public Ч3 _/* $VF was: <*/() {
      return this.П;
   }

   public boolean ЕВ() {
      return Ш != null && this.ЕЩ() && this.Ед() && !this.н.7(this, Ш);
   }

   public double _/* $VF was: 0*/() {
      return this.7;
   }

   public Ч3 ж() {
      return this.О;
   }

   private void ЕВ() {
      if (I.field_1724 != null && I.field_1687 != null && I.field_1761 != null) {
         if (this.д.Я(лб) && !(Boolean)this.O.о()) {
            this.O.Ш(Boolean.valueOf(true));
         }

         Пф var1 = (Пф)sg.ec.Ч.getInstance().getModuleManager().ь(Пф.class);
         if (var1 == null || !var1.7() || !var1.я().Я(ло) || !I.field_1724.method_6059(class_1294.field_5906) || I.field_1724.method_5799()) {
            if (Ш != null) {
               if (this.д.Я(Й5)) {
                  this.Ед();
               } else if (this.ЕЩ() && this.Ед() && !this.н.7(this, Ш)) {
                  Фш var2 = (Фш)sg.ec.Ч.getInstance().getModuleManager().ь(Фш.class);
                  boolean var3 = var2 != null && I.field_1724.method_6128() && var2.р(Ш);
                  if (!var3 && (this.д.Я(Йн) || (Boolean)this.O.о())) {
                     if (г(I.field_1724.method_36454(), I.field_1724.method_36455(), (double)((Float)this.Ф.о()).floatValue(), Ш)) {
                        this.ЕЧ();
                     }
                  } else {
                     this.ЕЧ();
                  }

                  if (!this.ш) {
                     this.н.4(this, Ш);
                  }
               } else if (!this.ш) {
                  this.н.4(this, Ш);
               }
            } else {
               this.К = System.currentTimeMillis();
               this.ш = false;
               if (this.д.Я(ЙШ)) {
                  Пд var4 = this.г();
                  if (var4 != null) {
                     var4.йв();
                  }
               }

               this.н.4(this);
            }
         }
      }
   }

   public Ч3 В() {
      return this.е;
   }

   public ТЙ л() {
      return this.Р;
   }

   public Ч3 Щ() {
      return this.э;
   }

   public Ч3 AttackAura() {
      return this.т;
   }

   public Ч3 и() {
      return this.1;
   }

   public boolean ЕЧ() {
      return this.ш;
   }

   public ТЙ ъ() {
      return this.д;
   }

   public long л() {
      return this.К;
   }

   public boolean ЕГ() {
      return this.l;
   }

   public Ч3 _/* $VF was: 2*/() {
      return this.Б;
   }

   public Ч3 О() {
      return this.3;
   }

   private double л(class_1309 var1) {
      if (I.field_1724 == null) {
         return 0.0;
      } else {
         class_243 var2 = I.field_1724.method_33571();
         class_243 var3 = var1.method_19538().method_1031(0.0, (double)var1.method_17682() / ЙУ, 0.0);
         class_243 var4 = var3.method_1020(var2);
         double var5 = Math.toDegrees(Math.atan2(var4.field_1350, var4.field_1352)) - ЙЛ;
         double var7 = -Math.toDegrees(Math.atan2(var4.field_1351, Math.sqrt(var4.field_1352 * var4.field_1352 + var4.field_1350 * var4.field_1350)));
         double var9 = class_3532.method_15338(var5 - (double)I.field_1724.method_36454());
         double var11 = var7 - (double)I.field_1724.method_36455();
         return Math.sqrt(var9 * var9 + var11 * var11);
      }
   }

   public Ч3 П() {
      return this.с;
   }

   public Ч3 ш() {
      return this.ш;
   }

   public Ч3 х() {
      return this.а;
   }

   public Ч3 ц() {
      return this.l;
   }

   public Ч3 щ() {
      return this.Й;
   }

   public void П(double var1) {
      this.д = var1;
   }

   public Ч3 н() {
      return this.西;
   }

   public Ь _/* $VF was: 2*/() {
      return this.Ю;
   }

   public Ч3 _/* $VF was: 0*/() {
      return this.й;
   }

   public Ь щ() {
      return this.必;
   }

   public ТЙ П() {
      return this.Т;
   }

   public Ч3 诶() {
      return this.ч;
   }

   public Ь _/* $VF was: 0*/() {
      return this.Й;
   }

   public Ь _/* $VF was: 8*/() {
      return this.ш;
   }

   public void н(boolean var1) {
      this.ш = var1;
   }

   public Ч3 Ы() {
      return this.С;
   }

   public Ч3 У() {
      return this.к;
   }

   public static boolean г(float var0, float var1, double var2, class_1297 var4) {
      class_243 var5 = I.field_1724.method_5836(I.method_61966().method_60637(false));
      class_243 var6 = I.field_1724.method_5631(var1, var0);
      class_243 var7 = var5.method_1019(var6.method_1021(var2));
      class_238 var8 = var4.method_5829();
      return var8.method_1006(var5) || var8.method_992(var5, var7).isPresent();
   }

   public void г(ТЙ var1) {
      this.Р = var1;
   }

   private void ЕУ() {
      if (Ш != null) {
         class_243 var1 = I.field_1724.method_33571();
         class_243 var2 = Ш.method_33571();
         class_243 var3 = var2.method_1020(var1);
         double var4 = var3.method_1033();
         if (!(var4 < Йй)) {
            int var6 = Math.max(1, (int)Math.ceil(var4 * ЙП));
            HashSet var7 = new HashSet();

            for (int var8 = 0; var8 <= var6; var8++) {
               double var9 = (double)var8 / (double)var6;
               class_2338 var11 = class_2338.method_49637(
                  var1.field_1352 + var3.field_1352 * var9, var1.field_1351 + var3.field_1351 * var9, var1.field_1350 + var3.field_1350 * var9
               );
               if (var7.add(var11) && !I.field_1687.method_8320(var11).method_26215()) {
                  class_243 var12 = var1.method_1020(var11.method_46558());
                  double var13 = Math.abs(var12.field_1352);
                  double var15 = Math.abs(var12.field_1351);
                  double var17 = Math.abs(var12.field_1350);
                  class_2350 var19;
                  if (var15 >= var13 && var15 >= var17) {
                     var19 = var12.field_1351 > 0.0 ? class_2350.field_11036 : class_2350.field_11033;
                  } else if (var13 >= var17) {
                     var19 = var12.field_1352 > 0.0 ? class_2350.field_11034 : class_2350.field_11039;
                  } else {
                     var19 = var12.field_1350 > 0.0 ? class_2350.field_11035 : class_2350.field_11043;
                  }

                  I.field_1724.field_3944.method_52787(new class_2846(class_2847.field_12968, var11, var19, 0));
                  I.field_1724.field_3944.method_52787(new class_2846(class_2847.field_12973, var11, var19, 0));
               }
            }
         }
      }
   }

   public Ч3 Я() {
      return this.Х;
   }

   public float ю() {
      return this.и.о() ? 2Щ.А0() : ЙI;
   }

   public Ч3 у() {
      return this.Ц;
   }

   public void М(int var1) {
      this.с = var1;
   }

   public Ч3 с() {
      return this.М;
   }

   public void г(Чэ var1) {
      if (Ш != null) {
         double var2 = Ш.method_23317() - Ш.field_6014;
         double var4 = Ш.method_23318() - Ш.field_6036;
         double var6 = Ш.method_23321() - Ш.field_5969;
         double var8 = Math.sqrt(var2 * var2 + var4 * var4 + var6 * var6);
         this.7 = this.д;
         this.д = л衣 * var8;
      }
   }

   public Ч3 _/* $VF was: 7*/() {
      return this.Ж;
   }

   private void ЕБ() {
      if (this.2.4(ЙO)) {
         int var1 = sg.ec.П4.Их();
         if (var1 != -1) {
            if (Ш.method_6079().method_7909() == class_1802.field_8255 || Ш.method_6047().method_7909() == class_1802.field_8255) {
               if (var1 >= 9) {
                  if ((Boolean)this.л.о()) {
                     return;
                  }

                  I.field_1761
                     .method_2906(I.field_1724.field_7512.field_7763, var1, I.field_1724.method_31548().field_7545, class_1713.field_7791, I.field_1724);
                  I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
                  I.field_1761.method_2918(I.field_1724, Ш);
                  I.field_1724.method_6104(class_1268.field_5808);
                  I.field_1761
                     .method_2906(I.field_1724.field_7512.field_7763, var1, I.field_1724.method_31548().field_7545, class_1713.field_7791, I.field_1724);
                  I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
               } else {
                  I.field_1724.field_3944.method_52787(new class_2868(var1));
                  I.field_1761.method_2918(I.field_1724, Ш);
                  I.field_1724.method_6104(class_1268.field_5808);
                  I.field_1724.field_3944.method_52787(new class_2868(I.field_1724.method_31548().field_7545));
               }
            }
         }
      }
   }

   public Ч3 弟() {
      return this.>;
   }

   public Ч3 м() {
      return this.х;
   }

   private boolean Ед() {
      if (I.field_1724 == null) {
         return false;
      } else {
         long var1 = System.currentTimeMillis();
         if (var1 < this.К) {
            return false;
         } else if (!(Boolean)this.弟.о()) {
            return true;
         } else {
            float var3 = I.field_1724.method_7261(Йл);
            if (var3 < this.Б) {
               this.У = 0L;
               return false;
            } else if (this.У == 0L) {
               this.У = var1 + ThreadLocalRandom.current().nextLong(ЙЙ, Йи);
               return false;
            } else {
               return var1 >= this.У;
            }
         }
      }
   }

   public float Ег() {
      return this.必;
   }

   public Ту г() {
      return this.н;
   }

   public Ч3 в() {
      return this.ю;
   }

   public void л(long var1) {
      this.У = var1;
   }

   public static class_1309 П() {
      return Ш;
   }

   public Ч3 _/* $VF was: 5*/() {
      return this.у;
   }

   private void ЕЩ() {
      if (I.field_1724 == null || I.field_1687 == null || Ш == null) {
         this.ш = false;
      } else if (this.д.Я(лщ)) {
         this.ш = false;
      } else {
         this.ш = this.н.щ(this, Ш) && Ш.field_6235 <= 0;
         if (!this.ш) {
            this.н.Ы(this, Ш);
         }
      }
   }

   public void AttackAura(ЧН var1) {
      if (Ш != null) {
         if (this.Н.Я(лэ)) {
            Фо.р(var1, ТI.ЧВ());
         } else {
            class_243 var2 = Т2.М(Ш);
            float var3 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var2.field_1350, var2.field_1352)) - ле);
            Фо.р(var1, var3);
         }

         if (this.l) {
            var1.и(0.0F);
            var1.х(0.0F);
            this.l = false;
         }
      }
   }

   public Ь ц() {
      return this.Ч;
   }

   public ТЙ с() {
      return this.Н;
   }

   public Ь _/* $VF was: 5*/() {
      return this.ъ;
   }

   public Ч3 а() {
      return this.г;
   }

   public Ь Х() {
      return this.ы;
   }

   public Ч3 _/* $VF was: 9*/() {
      return this.И;
   }

   public Ч3 Ю() {
      return this.р;
   }

   public Ч3 _/* $VF was: 4*/() {
      return this.诶;
   }

   public Ч3 А() {
      return this.Ю;
   }

   public Ч3 Э() {
      return this.ж;
   }

   public Ь ш() {
      return this.Ж;
   }

   public Ч3 ъ() {
      return this.з;
   }

   public void Б(float var1) {
      this.必 = var1;
   }

   public ФЯ ъ() {
      return this.н;
   }

   public Ч3 о() {
      return this.ц;
   }

   public Фи л() {
      return this.2;
   }

   private boolean AttackAura(class_1309 var1) {
      if (I.field_1724 == null || I.field_1687 == null) {
         return false;
      } else if (var1 instanceof class_746) {
         return false;
      } else if (I.field_1724.method_5739(var1) >= (Float)this.Ф.о() + (Float)this.з.о() + (I.field_1724.method_6128() ? (Float)this.т.о() : 0.0F)) {
         return false;
      } else {
         if (var1 instanceof class_1657) {
            class_1657 var2 = (class_1657)var1;
            if (var2.method_5477().getString().equalsIgnoreCase(I.field_1724.method_5477().getString())) {
               return false;
            }

            if (Тр.З(var2)) {
               return false;
            }

            if (!this.I.4(Й弟) && sg.ec.Ч.getInstance().getFriendManager().>(var2.method_5477().getString())) {
               return false;
            }
         }

         if (!this.I.4(Йу) && var1 instanceof class_1657) {
            return false;
         } else if (!this.I.4(Й必) && var1 instanceof class_1657 && var1.method_6096() == 0) {
            return false;
         } else {
            return !this.I.4(ЙБ) && var1 instanceof class_1308 ? false : !var1.method_5655() && var1.method_5805() && !(var1 instanceof class_1531);
         }
      }
   }

   public Фи П() {
      return this.ь;
   }

   public Ч3 л() {
      return this.я;
   }

   private double г(class_1799 var1) {
      if (!(var1.method_7909() instanceof class_1738)) {
         return 0.0;
      } else {
         double var2 = 0.0;
         class_9285 var4 = (class_9285)var1.method_57824(class_9334.field_49636);
         if (var4 != null) {
            for (class_9287 var6 : var4.comp_2393()) {
               if (var6.comp_2395().equals(class_5134.field_23724)) {
                  var2 += var6.comp_2396().comp_2449();
               }

               if (var6.comp_2395().equals(class_5134.field_23725)) {
                  var2 += var6.comp_2396().comp_2449() * Йы;
               }
            }
         }

         class_9304 var9 = (class_9304)var1.method_57825(class_9334.field_49633, class_9304.field_49385);

         for (class_6880 var7 : var9.method_57534()) {
            int var8 = var9.method_57536(var7);
            if (var7.method_40225(class_1893.field_9111)) {
               var2 += (double)var8 * ЙЮ;
            } else if (var7.method_40225(class_1893.field_9095)) {
               var2 += (double)var8 * Йl;
            } else if (var7.method_40225(class_1893.field_9107)) {
               var2 += (double)var8 * Йш;
            } else if (var7.method_40225(class_1893.field_9096)) {
               var2 += (double)var8 * ЙА;
            }
         }

         return var2;
      }
   }

   public void в(Ц var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         if (Ш == null || !this.AttackAura(Ш)) {
            this.ЕГ();
         }

         this.ЕВ();
      }
   }

   public Ь А() {
      return this.Я;
   }

   public void р(Ф6 var1) {
      if (this.7() && I.field_1724 != null && this.д.Я(лЗ) && (Boolean)this.х.о()) {
         И var2 = Фа.ы();
         var2.У((int)((Float)this.М.о()).floatValue());
         if (!var2.ч6()) {
            int var3 = var2.З();
            int var4 = var2.Д();
            float var5 = var4 > 0 ? Math.clamp((float)var3 / (float)var4, 0.0F, 1.0F) : 0.0F;
            float var6 = (float)I.method_22683().method_4486();
            float var7 = лк;
            float var8 = лЪ;
            float var9 = л6;
            float var10 = (var6 - var7) * л9;
            String var12 = String.format(л_, var3, var4, Math.round(var5 * л<));
            float var13 = Ф西.н[14].7(var12);
            float var14 = (var6 - var13) * лЫ;
            int var15 = sg.ec.ПЙ.ю(sg.ec.ь.HEADER);
            int var16 = sg.ec.ПЙ.ю(sg.ec.ь.MODULE_VISUAL);
            int var17 = sg.ec.ПЙ.ю(sg.ec.ь.TEXT);
            Ф西.н[14].ь(var1.9(), var12, (double)var14, (double)var9, var17);
            float var18 = var9 + л8;
            int var19 = 2Д.Р(var17, лЬ);
         }
      }
   }

   public int _/* $VF was: 9*/() {
      return this.с;
   }

   public Ь З() {
      return this.х;
   }

   public Ч3 Т() {
      return this.Ч;
   }

   public Ч3 _() {
      return this.O;
   }

   public Ч3 С() {
      return this.А;
   }

   @Override
   public void ц() {
      super.ц();
      this.ш = false;
      this.Б = ThreadLocalRandom.current().nextFloat(Йх, Йъ);
      this.У = 0L;
      this.н.ю(this);
   }

   public Ч3 l() {
      return this.ы;
   }

   private void ЕГ() {
      if (I.field_1724 != null && I.field_1687 != null) {
         ArrayList var1 = new ArrayList();

         for (class_1297 var3 : I.field_1687.method_18112()) {
            if (var3 instanceof class_1309) {
               class_1309 var4 = (class_1309)var3;
               if (this.AttackAura(var4)) {
                  var1.add(var4);
               }
            }
         }

         if (var1.isEmpty()) {
            Ш = null;
         } else if (var1.size() == 1) {
            Ш = (class_1309)var1.getFirst();
         } else {
            String var6 = (String)this.7.о();
            Comparator var10000;
            switch (var6) {
               case Й2:
                  var10000 = Comparator.comparingDouble(var0 -> (double)I.field_1724.method_5739(var0));
                  break;
               case Йь:
                  var10000 = Comparator.comparingDouble(this::I);
                  break;
               case Йм:
                  var10000 = Comparator.comparingDouble(this::л);
                  break;
               default:
                  var10000 = Comparator.comparingDouble(this::с);
            }

            Comparator var5 = var10000;
            Фш var7 = (Фш)sg.ec.Ч.getInstance().getModuleManager().ь(Фш.class);
            if (var7 != null && var7.7() && I.field_1724.method_6128() && var7.必.Я(Йф)) {
               var1.sort(Comparator.<class_1309, Boolean>comparing(var0 -> !var0.method_6128()).thenComparing(var5));
            } else {
               var1.sort(var5);
            }

            Ш = (class_1309)var1.getFirst();
         }
      }
   }

   public Ь М() {
      return this.у;
   }

   public float ЕЕ() {
      return (Float)this.Ф.о();
   }

   public Ч3 Н() {
      return this.0;
   }

   public void с(long var1) {
      this.К = var1;
   }

   @Override
   public void Щ() {
      super.Щ();
      Ш = null;
      this.К = System.currentTimeMillis();
      this.я = 0;
      this.с = 0;
      this.ш = false;
      this.У = 0L;
      this.н.а(this);
   }

   public Ь l() {
      return this.А;
   }

   public Ч3 Х() {
      return this.必;
   }

   public Ь _() {
      return this.弟;
   }

   public Ч3 Д() {
      return this.4;
   }

   public void я(float var1) {
      this.Б = var1;
   }

   public float Еъ() {
      return this.Б;
   }
}
