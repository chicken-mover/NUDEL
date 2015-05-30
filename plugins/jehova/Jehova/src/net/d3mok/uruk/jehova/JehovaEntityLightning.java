package net.d3mok.uruk.jehova;

/*
 * This class is essentially a copy of the NMS EntityLightning,
 * with the only difference being the range at which the sound
 * of it can be heard.
 * 
 */

import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.World;

public class JehovaEntityLightning extends EntityLightning {
    
  private int lifeTicks;
  public long a;
  private int c;
  public boolean isEffect = true;
  
  public JehovaEntityLightning(World world, double d0, double d1, double d2) {
      super(world, d0, d1, d2);
  }
  
  public JehovaEntityLightning(World world, double d0, double d1, double d2, boolean isEffect) {
      super(world, d0, d1, d2, isEffect);
  }
  
  @Override
  public void t_() {
      
      super.t_();
    
      this.lifeTicks -= 1;
      if (this.lifeTicks < 0) {
          if (this.c == 0) {
              die();
          } else if (this.lifeTicks < -this.random.nextInt(10)) {
              this.c -= 1;
              this.lifeTicks = 1;
              this.a = this.random.nextLong();
          }
      }
      
  }
  
}