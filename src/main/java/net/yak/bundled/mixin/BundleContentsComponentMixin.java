package net.yak.bundled.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.yak.bundled.Bundled;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BundleContentsComponent.class)
public class BundleContentsComponentMixin {

    @ModifyReturnValue(method = "getOccupancy(Lnet/minecraft/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;", at = @At("RETURN"))
    private static Fraction bundled$unstackablesLesserWeight(Fraction original, ItemStack stack) {
        if (stack.get(DataComponentTypes.BUNDLE_CONTENTS) == null && stack.get(DataComponentTypes.BEES) == null) {
            if (stack.isIn(Bundled.MAX_WEIGHT)) { // max weight tag
                return Fraction.getFraction(1, 1);
            }
            else if (stack.isIn(Bundled.QUARTER_WEIGHT) || (stack.getMaxCount() == 1 && !stack.isIn(Bundled.SIXTEENTH_WEIGHT))) { // quarter weight tag and default for unstackables
                return Fraction.getFraction(1, 4);
            }
            else if (stack.isIn(Bundled.SIXTEENTH_WEIGHT)) { // sixteenth weight tag
                return Fraction.getFraction(1, 16);
            }
        }
        return original;
    }

    @ModifyReturnValue(method = "canBeBundled", at = @At("RETURN"))
    private static boolean bundled$canBundle(boolean original, ItemStack stack) {
        return original && !stack.isIn(Bundled.CANNOT_NEST) && BundleItem.getAmountFilled(stack) == 0f;
    }

}
