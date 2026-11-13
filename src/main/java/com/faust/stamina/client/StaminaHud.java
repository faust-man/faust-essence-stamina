package com.faust.stamina.client;
import com.faust.core.client.*;
import com.faust.core.progression.*;
import static com.faust.core.client.CoreClient.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
public final class StaminaHud implements StaminaClient {
 private boolean airDrawn,lastAirDrawn;private int airTop,vehicleTop;
 public void beginHud(){airDrawn=false;airTop=vehicleTop=Integer.MAX_VALUE;}
 public void airSprite(net.minecraft.resources.ResourceLocation sprite,int y){if(HudClearance.vehicleHeart(sprite.getNamespace(),sprite.getPath()))vehicleTop=Math.min(vehicleTop,y);if(sprite.getPath().equals("hud/air")||sprite.getPath().equals("hud/air_bursting")){airDrawn=true;airTop=Math.min(airTop,y);}}
 public void reset(){airDrawn=lastAirDrawn=false;}

 public boolean lastAirDrawn(){return lastAirDrawn;}
 public void hud(GuiGraphics g) {
  lastAirDrawn=airDrawn;var mc=Minecraft.getInstance();if(mc.player==null||mc.player.isCreative()||mc.player.isSpectator()||progress==null||balance==null||mc.options.hideGui||balance.client.get("hud")==0||balance.stamina.get("enabled")==0)return;
  int x=mc.getWindow().getGuiScaledWidth()/2+10+balance.client.get("hudX").intValue(),y=mc.getWindow().getGuiScaledHeight()-50+balance.client.get("hudY").intValue();
  y=HudClearance.staminaY(y,airDrawn?airTop:Integer.MAX_VALUE,vehicleTop);
  int total=(int)Math.ceil(progress.baseStamina(balance)),available=(int)Math.floor(progress.maxStamina(balance)),filled=(int)Math.floor(progress.stamina);
  com.mojang.blaze3d.systems.RenderSystem.enableBlend();
  boolean exhausted=progress.actionsBlocked(balance);
  try {
   boolean compact=OverflowingBarsCompat.enabled();
   for(int unit=0;unit<(compact?StaminaLayers.units(total):total);unit++) {
    int icon=unit/2,half=unit%2,dx=x+(icon%10)*8+half*4,dy=y-(icon/10)*9;
    int state=compact?StaminaLayers.state(unit,total,available,filled):StaminaSprites.state(unit,available,filled),color=StaminaSprites.color(state,exhausted);
    if(compact&&state==0&&!exhausted)color=OverflowingBarsCompat.color(StaminaLayers.layer(filled),StaminaLayers.absoluteUnit(unit,filled)/20==StaminaLayers.layer(filled));
    g.setColor(((color>>16)&255)/255f,((color>>8)&255)/255f,(color&255)/255f,1f);
    var texture=net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("faustcore",StaminaSprites.texture(state));
    g.blit(texture,dx,dy,4,8,StaminaSprites.textureX(state,half),0,4,8,8,8);
   }
  } finally {
   g.setColor(1f,1f,1f,1f);
  }
  if(OverflowingBarsCompat.enabled())OverflowingBarsCompat.count(g,x+82,y,filled,mc.font);

 }
}
