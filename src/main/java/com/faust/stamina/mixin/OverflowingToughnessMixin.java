package com.faust.stamina.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Optional client config default; explicit values loaded from disk still win. */
@Pseudo
@Mixin(targets="fuzs.overflowingbars.config.ClientConfig$ToughnessRowConfig", remap=false)
public abstract class OverflowingToughnessMixin {
    @Shadow public boolean armorToughnessBar;
    @Inject(method="<init>", at=@At("RETURN"))
    private void stamina$defaultHidden(CallbackInfo ci) { armorToughnessBar = false; }
}
