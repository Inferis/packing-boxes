package org.inferis.packing_boxes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.util.log.Log;

import org.inferis.packing_boxes.block.ModBlocks;
import org.inferis.packing_boxes.item.ModItemGroups;
import org.inferis.packing_boxes.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackingBoxes implements ModInitializer {
	public static final String MODID = "packing_boxes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
        PackingBoxes.LOGGER.info("Initializing " + PackingBoxes.MODID);
		System.out.println("init packing_boxes");
		
		ModBlocks.registerBlocks();
		ModBlocks.registerUseBlockEvent();

		ModItems.registerItems();
		ModItemGroups.registerItemGroups();
	}
}