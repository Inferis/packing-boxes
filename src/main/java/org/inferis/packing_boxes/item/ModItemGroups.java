package org.inferis.packing_boxes.item;

import org.inferis.packing_boxes.PackingBoxes;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class ModItemGroups {
    public static ItemGroup PACKING_TAPE;
    public static ItemGroup PACKING_BOX;

    public static void registerItemGroups() {
        PackingBoxes.LOGGER.info("Registering " + PackingBoxes.MODID + " items for creative inventory");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.addAfter(Items.ARMOR_STAND, ModItems.PACKING_TAPE);
            content.addAfter(ModItems.PACKING_TAPE, ModItems.PACKING_BOX);
        });

    }
}
