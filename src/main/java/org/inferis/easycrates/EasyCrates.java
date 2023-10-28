package org.inferis.easycrates;

import net.fabricmc.api.ModInitializer;

import org.inferis.easycrates.block.ModBlocks;
import org.inferis.easycrates.item.ModItems;
import org.inferis.easycrates.item.ModItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyCrates implements ModInitializer {
	public static final String MODID = "easycrates";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModBlocks.registerBlocks();
		ModBlocks.registerUseBlockEvent();

		ModItems.registerItems();
		ModItemGroups.registerItemGroups();
	}
}