package net.yak.bundled;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bundled implements ModInitializer {
	public static final String MOD_ID = "bundled";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TagKey<Item> MAX_WEIGHT = TagKey.of(Registries.ITEM.getKey(), id("max_weight"));
	public static final TagKey<Item> CANNOT_NEST = TagKey.of(Registries.ITEM.getKey(), id("cannot_nest"));

	@Override
	public void onInitialize() {

		LOGGER.info("inspired by unstackabundles <3");

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
			entries.addAfter(Items.SPYGLASS, Items.BUNDLE);
		});
	}

	public static Identifier id(String value) {
		return new Identifier(MOD_ID, value);
	}
}