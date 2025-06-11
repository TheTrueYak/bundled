package net.yak.bundled.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(BundleContentsComponent.Builder.class)
public abstract class BundleContentsComponentBuilderMixin {

    @Shadow @Final private List<ItemStack> stacks;

    @WrapOperation(method = "add(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copyWithCount(I)Lnet/minecraft/item/ItemStack;"))
    private ItemStack bundled$stackInsertionRespectsMaxCount(ItemStack instance, int count, Operation<ItemStack> original, @Local(ordinal = 1) LocalIntRef removalIndex) {
        if (count > instance.getMaxCount()) {
            int remainderCount = count - instance.getMaxCount();
            instance.setCount(instance.getMaxCount());
            this.stacks.add(removalIndex.get(), instance);

            return original.call(instance, remainderCount);
        }
        return original.call(instance, count);
    }

}
