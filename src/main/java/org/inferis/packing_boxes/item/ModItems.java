package org.inferis.packing_boxes.item;

import org.inferis.packing_boxes.PackingBoxes;
import org.inferis.packing_boxes.block.ModBlocks;
import org.inferis.packing_boxes.item.ModItems.ItemMaker;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item PACKING_TAPE;
    public static BoxBlockItem PACKING_BOX;

    interface ItemMaker<T extends Item> {
        T makeItem(RegistryKey<Item> key);
    }

    private static <T extends Item> T registerItem(String name, ItemMaker<T> itemMaker) {
        PackingBoxes.LOGGER.info("Registering item: " + PackingBoxes.MODID + ":" + name);

        Identifier identifier = Identifier.of(PackingBoxes.MODID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, identifier);
        T item = itemMaker.makeItem(key);
        return Registry.register(Registries.ITEM, identifier, item);
    }

    public static void registerItems() {
        PackingBoxes.LOGGER.info("Registering mod items for " + PackingBoxes.MODID);

        PACKING_TAPE = registerItem("packing_tape", key -> { 
            return new PackingTapeItem(new Item.Settings().registryKey(key)); 
        });
        PACKING_BOX = registerItem("packing_box", key -> { 
            return new BoxBlockItem(ModBlocks.PACKING_BOX, new Item.Settings().maxCount(1).useBlockPrefixedTranslationKey().registryKey(key)); 
        });
    }
}
