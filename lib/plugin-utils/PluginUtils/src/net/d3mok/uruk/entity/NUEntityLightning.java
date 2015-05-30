package net.d3mok.uruk.entity;

/*
 * This class is essentially a copy of the NMS EntityLightning,
 * with the only difference being the range at which the sound
 * of it can be heard.
 * 
 */

import java.util.List;
import java.util.Random;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.event.block.BlockIgniteEvent;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Material;

public class NUEntityLightning extends EntityLightning {
  private int lifeTicks;
  public long a;
  private int c;
  public boolean isEffect = false;
  
  public NUEntityLightning(World world, double d0, double d1, double d2) {
      super(world, d0, d1, d2);
  }
  
  public NUEntityLightning(World world, double d0, double d1, double d2, boolean isEffect) {
      super(world, d0, d1, d2, isEffect);
  }
  
  @Override
  public void t_() {
    super.t_();
    this.lifeTicks -= 1;
    if (this.lifeTicks < 0) {
      if (this.c == 0)
      {
        die();
      }
      else if (this.lifeTicks < -this.random.nextInt(10))
      {
        this.c -= 1;
        this.lifeTicks = 1;
        this.a = this.random.nextLong();
        BlockPosition blockposition = new BlockPosition(this);
        if ((!this.world.isClientSide) && (this.world.getGameRules().getBoolean("doFireTick")) && (this.world.areChunksLoaded(blockposition, 10)) && (this.world.getType(blockposition).getBlock().getMaterial() == Material.AIR) && (Blocks.FIRE.canPlace(this.world, blockposition))) {
          if ((!this.isEffect) && (!CraftEventFactory.callBlockIgniteEvent(this.world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this).isCancelled())) {
            this.world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
          }
        }
      }
    }
    if ((this.lifeTicks >= 0) && (!this.isEffect)) {
      if (this.world.isClientSide)
      {
        this.world.d(2);
      }
      else
      {
        double d0 = 3.0D;
        List list = this.world.getEntities(this, new AxisAlignedBB(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + 6.0D + d0, this.locZ + d0));
        for (int i = 0; i < list.size(); i++)
        {
          Entity entity = (Entity)list.get(i);
          
          entity.onLightningStrike(this);
        }
      }
    }
  }
  
}