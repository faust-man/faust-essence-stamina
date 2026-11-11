package com.faust.stamina.client;
public final class StaminaModMenu implements com.terraformersmc.modmenu.api.ModMenuApi {
    public com.terraformersmc.modmenu.api.ConfigScreenFactory<?> getModConfigScreenFactory(){return parent->new com.faust.core.client.CoreConfigScreen(parent,java.util.List.of("stamina","fatigue","armor","sweetFoods","client"));}
}
