package net.yak.bundled.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(BundleContents.Mutable.class)
public abstract class BundleContentsComponentBuilderMixin {

    @Shadow @Final private List<ItemStack> items;

    @WrapOperation(method = "tryInsert", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copyWithCount(I)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack bundled$stackInsertionRespectsMaxCount(ItemStack instance, int count, Operation<ItemStack> original, @Local(ordinal = 1) LocalIntRef removalIndex) {
        if (count > instance.getMaxStackSize()) {
            int remainderCount = count - instance.getMaxStackSize();
            instance.setCount(instance.getMaxStackSize());
            this.items.add(removalIndex.get(), instance);

            return original.call(instance, remainderCount);
        }
        return original.call(instance, count);
    }

}
