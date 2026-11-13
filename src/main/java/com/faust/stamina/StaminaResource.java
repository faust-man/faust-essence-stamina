package com.faust.stamina;
import com.faust.essence.api.*;
import com.faust.core.runtime.CoreRuntime;
import net.minecraft.server.level.ServerPlayer;
public final class StaminaResource implements ResourceProvider {
 public String id(){return "fauststamina:stamina";}
 public ResourceState state(ServerPlayer player){EssenceApi.requireServerThread(player);var p=CoreRuntime.data(player);return new ResourceState(p.stamina,p.maxStamina(CoreRuntime.balance),p.fatigue,p.actionsBlocked(CoreRuntime.balance));}
 public boolean canSpend(ServerPlayer player,double amount){EssenceApi.requireServerThread(player);Resources.amount(amount);return player.isCreative()||player.isSpectator()||CoreRuntime.balance.stamina.get("enabled")==0||!state(player).exhausted();}
 public double spend(ServerPlayer player,double amount){EssenceApi.requireServerThread(player);Resources.amount(amount);if(amount==0||player.isCreative()||player.isSpectator()||CoreRuntime.balance.stamina.get("enabled")==0||!canSpend(player,amount))return 0;double before=state(player).current();CoreRuntime.pay(player,amount);return before-state(player).current();}
 public boolean pay(ServerPlayer player,double amount,boolean exact){EssenceApi.requireServerThread(player);Resources.amount(amount);return exact?CoreRuntime.payStaminaExact(player,amount):CoreRuntime.pay(player,amount);}
 public void restore(ServerPlayer player,double amount){EssenceApi.requireServerThread(player);Resources.amount(amount);var p=CoreRuntime.data(player);p.stamina=Math.min(p.maxStamina(CoreRuntime.balance),p.stamina+amount);p.updateExhaustion(CoreRuntime.balance);CoreRuntime.sync(player);}
}
