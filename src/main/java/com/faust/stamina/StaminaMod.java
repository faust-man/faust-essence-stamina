package com.faust.stamina;
@net.neoforged.fml.common.Mod("fauststamina")
public final class StaminaMod {
    public StaminaMod(net.neoforged.fml.ModContainer container){
        if(net.neoforged.fml.loading.FMLEnvironment.dist==net.neoforged.api.distmarker.Dist.CLIENT)Client.register(container);
    }
    private static class Client {
        static void register(net.neoforged.fml.ModContainer container){container.registerExtensionPoint(net.neoforged.neoforge.client.gui.IConfigScreenFactory.class,(net.neoforged.neoforge.client.gui.IConfigScreenFactory)(c,parent)->new com.faust.core.client.CoreConfigScreen(parent,java.util.List.of("stamina","fatigue","armor","sweetFoods","client")));}
    }
}
