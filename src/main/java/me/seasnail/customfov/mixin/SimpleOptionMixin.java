package me.seasnail.customfov.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin<T> {
    @Shadow
    T value;

    @Shadow @Final
    private Consumer<T> changeCallback;

    @Inject(
            method = "setValue",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption$Callbacks;validate(Ljava/lang/Object;)Ljava/util/Optional;", shift = At.Shift.BEFORE),
            cancellable = true
    )
    private void earlyReturn(T value, CallbackInfo ci) {
        this.value = value;

        if (MinecraftClient.getInstance().isRunning()) {
            changeCallback.accept(value);
        }

        ci.cancel();
    }
}
