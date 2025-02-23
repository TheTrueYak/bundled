package net.yak.bundled.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.yak.bundled.Bundled;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContentsComponent.class)
public class BundleContentsComponentMixin {

    @Inject(method = "getOccupancy(Lnet/minecraft/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;", at = @At("RETURN"), cancellable = true)
    private static void bundled$unstackablesLesserWeight(ItemStack stack, CallbackInfoReturnable<Fraction> cir) {
        if (stack.get(DataComponentTypes.BUNDLE_CONTENTS) == null && stack.get(DataComponentTypes.BEES) == null) {
            if (stack.isIn(Bundled.MAX_WEIGHT) || stack.getItem() instanceof BundleItem) { // max weight tag
                cir.setReturnValue(Fraction.getFraction(1, 1));
            }
            else if (stack.isIn(Bundled.QUARTER_WEIGHT) || (stack.getMaxCount() == 1 && !stack.isIn(Bundled.SIXTEENTH_WEIGHT))) { // quarter weight tag and default for unstackables
                cir.setReturnValue(Fraction.getFraction(1, 4));
            }
            else if (stack.isIn(Bundled.SIXTEENTH_WEIGHT)) { // sixteenth weight tag
                cir.setReturnValue(Fraction.getFraction(1, 16));
            }
        }
    }

}
