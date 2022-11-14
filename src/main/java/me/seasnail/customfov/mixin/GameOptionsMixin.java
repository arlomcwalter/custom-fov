package me.seasnail.customfov.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Shadow @Final
    private SimpleOption<Integer> fov;

    @Unique
    private static final int CUSTOM_MIN = 1, CUSTOM_MAX = 180;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void modifyFovOption(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        fov.textGetter = (value) -> GameOptions.getGenericValueText(fov.text, value);
        fov.callbacks = new SimpleOption.ValidatingIntSliderCallbacks(CUSTOM_MIN, CUSTOM_MAX);
    }
}
