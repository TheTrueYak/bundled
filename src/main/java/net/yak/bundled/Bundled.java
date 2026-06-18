package net.yak.bundled;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bundled implements ModInitializer {
	public static final String MOD_ID = "bundled";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TagKey<Item> CANNOT_NEST = TagKey.create(BuiltInRegistries.ITEM.key(), id("cannot_nest"));
	public static final TagKey<Item> MAX_WEIGHT = TagKey.create(BuiltInRegistries.ITEM.key(), id("max_weight"));
	public static final TagKey<Item> HALF_WEIGHT = TagKey.create(BuiltInRegistries.ITEM.key(), id("half_weight"));
	public static final TagKey<Item> QUARTER_WEIGHT = TagKey.create(BuiltInRegistries.ITEM.key(), id("quarter_weight"));
	public static final TagKey<Item> EIGHTH_WEIGHT = TagKey.create(BuiltInRegistries.ITEM.key(), id("eighth_weight"));
	public static final TagKey<Item> SIXTEENTH_WEIGHT = TagKey.create(BuiltInRegistries.ITEM.key(), id("sixteenth_weight"));
	public static final TagKey<Item> THIRTYSECOND_WEIGHT = TagKey.create(BuiltInRegistries.ITEM.key(), id("thirtysecond_weight"));
	public static final TagKey<Item> SIXTYFOURTH_WEIGHT = TagKey.create(BuiltInRegistries.ITEM.key(), id("sixtyfourth_weight"));

	@Override
	public void onInitialize() {

		LOGGER.info("inspired by unstackabundles <3");

	}

	public static Identifier id(String value) {
		return Identifier.fromNamespaceAndPath(MOD_ID, value);
	}
}