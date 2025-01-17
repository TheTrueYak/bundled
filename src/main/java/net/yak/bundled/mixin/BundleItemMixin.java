package net.yak.bundled.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.yak.bundled.Bundled;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BundleItem.class)
public abstract class BundleItemMixin extends Item {

	public BundleItemMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "canMergeStack", at = @At("HEAD"), cancellable = true)
	private static void bundled$doNotMergeUnstackables(ItemStack stack, NbtList items, CallbackInfoReturnable<Optional<NbtCompound>> cir) {
		if (stack.getMaxCount() == 1) { // prevents the bundle from merging unstackables and creating illegal item stacks
			cir.setReturnValue(Optional.empty());
		}
	}

	@Inject(method = "getItemOccupancy", at = @At("RETURN"), cancellable = true)
	private static void bundled$unstackablesLesserWeight(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		if (stack.getMaxCount() == 1) { // makes unstackables weighted 16 instead of 64 in the bundle
			if (stack.getItem() instanceof BundleItem || stack.isIn(Bundled.MAX_WEIGHT)) { // bundles themselves are still weighted 64
				cir.setReturnValue(64);
			}
			else { // non-bundle items
				cir.setReturnValue(16);
			}
		}
		else if (stack.isIn(Bundled.MAX_WEIGHT)) {
			cir.setReturnValue(64);
		}
	}

	@WrapOperation(method = "onClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BundleItem;addToBundle(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)I"))
	private int bundled$preventItemInsertion(ItemStack bundle, ItemStack stack, Operation<Integer> original) {
		if (stack.isIn(Bundled.CANNOT_NEST)) { // prevents insertion if item is in no nest tag
			return 0;
		}
		return original.call(bundle, stack); // otherwise calls original
	}

	@WrapOperation(method = "onStackClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;canBeNested()Z"))
	private boolean bundled$preventItemInsertion2(Item instance, Operation<Boolean> original) {
		if (instance.getDefaultStack().isIn(Bundled.CANNOT_NEST)) { // prevents insertion if item is in no nest tag
			return false;
		}
		return original.call(instance); // otherwise calls original
	}

	@Override
	public boolean canBeNested() {
		return false;
	}

}