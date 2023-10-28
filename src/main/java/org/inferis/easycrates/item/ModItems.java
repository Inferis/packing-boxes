package org.inferis.easycrates.item;

import org.inferis.easycrates.EasyCrates;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.inferis.easycrates.block.ModBlocks;

public class ModItems {
    public static Item PACKING_TAPE;
    public static CrateBlockItem CRATE;

    private static <T extends Item> T registerItem(String name, T item) {
        EasyCrates.LOGGER.info("Registering item: " + EasyCrates.MODID + ":" + name);

        return Registry.register(Registries.ITEM, new Identifier(EasyCrates.MODID, name), item);
    }

    public static void registerItems() {
        EasyCrates.LOGGER.info("Registering mod items for " + EasyCrates.MODID);

        PACKING_TAPE = registerItem("packing_tape", new PackingTapeItem(new FabricItemSettings()));
        CRATE = registerItem("crate", new CrateBlockItem(ModBlocks.CRATE, new FabricItemSettings().maxCount(1)));
    }
}
