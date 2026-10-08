package Modules.Combat;

@ModuleInfo(L = "NoSlotChange", y = Category.COMBAT, N = Tag.OTHER)
public class NoSlotChange extends Module {
   private static short[] L = new short[]{0, 4};
@EventTarget
   private void N(Rs var1) {
      if (llilIl00<"qatffhrv",-431877989,-687270320,-687270319,-687270318,781692792>(var1) instanceof NSe var2) {
         NSe var10000 = var2;

         try {
            var7 = llilIl00<"glbzvijf",-431877989,-687270317,-687270325,-687270324,781692792>(var10000);
         } catch (Throwable var6) {
            throw new MatchException(llilIl00<"ttmwt",-431877989,-687270310,-687270309,-687270308,781692792>(var6), var6);
         }

         int var5 = var7;
         int var3 = var5;
         llilIl00<"lxvskdav",-431877989,-687270320,-687270325,-687270316,781692792>(var1);
         llilIl00<"qatffhrv",-431877989,-687270312,-687270325,-687270311,781692792>(
            llilIl00<"bdrmsr",-431877995,-687270315,-687270314,-687270313,781692792>(),
            () -> {
               int var2x = llilIl00<"lkheso",-431877989,-687270326,-687270325,-687270324,781692792>(
                  llilIl00<"jenubko",-431877989,-687270329,-687270328,-687270327,781692792>(
                     (NNNwS)llilIl00<"bdrmsr",-431877991,-687270331,-687270330,-687270334,-1750961535>(
                        (NNuU)llilIl00<"ficpfgd",-431877991,-687270336,-687270335,-687270334,-895855882>(this)[llilIl00<"lxvskdav",-431877985,-687270336,-687270333,-687270332,2121357227>()[0]]
                     )[llilIl00<"igui",-431877985,-687270336,-687270333,-687270332,127393980>()[1]]
                  )
               );
               llilIl00<"igui",-431877995,-687270323,-687270322,-687270321,781692792>(var3);
               llilIl00<"nrzvwzzk",-431877995,-687270323,-687270322,-687270321,781692792>(var2x);
            }
         );
      }
   }
}
