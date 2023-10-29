package org.inferis.packing_boxes.item;

import org.inferis.packing_boxes.PackingBoxes;
import org.inferis.packing_boxes.block.ModBlocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item PACKING_TAPE;
    public static BoxBlockItem PACKING_BOX;

    private static <T extends Item> T registerItem(String name, T item) {
        PackingBoxes.LOGGER.info("Registering item: " + PackingBoxes.MODID + ":" + name);

        return Registry.register(Registries.ITEM, new Identifier(PackingBoxes.MODID, name), item);
    }

    public static void registerItems() {
        PackingBoxes.LOGGER.info("Registering mod items for " + PackingBoxes.MODID);

        PACKING_TAPE = registerItem("packing_tape", new PackingTapeItem(new FabricItemSettings()));
        PACKING_BOX = registerItem("packing_box", new BoxBlockItem(ModBlocks.PACKING_BOX, new FabricItemSettings().maxCount(1)));
    }
}
