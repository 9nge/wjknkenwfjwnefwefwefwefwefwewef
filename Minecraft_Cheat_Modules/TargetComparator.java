package catlavan.module.combat;

import catlavan.event.Event;
import catlavan.render.MatrixStack;
import java.util.Comparator;
import sg.Matrix4f;
import sg.MainWindow;

public class TargetComparator extends Event implements Comparator {
   float threshold;
   MainWindow targetSelector;
   MatrixStack range;
   Matrix4f sortKey;
   int sortMode;

   public TargetComparator(float f, MatrixStack matrixstack, MainWindow SgClass480, Matrix4f Button, int i) {
      this.threshold = f;
      this.targetSelector = SgClass480;
      this.range = matrixstack;
      this.sortKey = Button;
      this.sortMode = i;
   }
}
