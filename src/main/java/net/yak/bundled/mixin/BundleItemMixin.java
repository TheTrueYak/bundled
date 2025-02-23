package net.yak.bundled.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.yak.bundled.Bundled;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BundleItem.class)
public abstract class BundleItemMixin extends Item {

	public BundleItemMixin(Settings settings) {
		super(settings);
	}

	@WrapOperation(method = "onClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/BundleContentsComponent$Builder;add(Lnet/minecraft/item/ItemStack;)I"))
	private int bundled$preventItemInsertion(BundleContentsComponent.Builder instance, ItemStack stack, Operation<Integer> original) {
		if (stack.isIn(Bundled.CANNOT_NEST) || stack.getItem() instanceof BundleItem) { // prevents insertion if item is in no nest tag
			return 0;
		}
		return original.call(instance, stack); // otherwise calls original
	}

	@WrapOperation(method = "onStackClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;canBeNested()Z"))
	private boolean bundled$preventItemInsertion2(Item instance, Operation<Boolean> original) {
		if (instance.getDefaultStack().isIn(Bundled.CANNOT_NEST) || instance instanceof BundleItem) { // prevents insertion if item is in no nest tag
			return false;
		}
		return original.call(instance); // otherwise calls original
	}

}