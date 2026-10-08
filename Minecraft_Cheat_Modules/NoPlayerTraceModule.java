package catlavan.module.combat;

import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.McContextHolder;
import sg.ClientTickEvent;

public class NoPlayerTraceModule extends Module implements McContextHolder {
   @Override
   public void onEvent(Event event) {
      if (event instanceof ClientTickEvent) {
      }
   }
}
