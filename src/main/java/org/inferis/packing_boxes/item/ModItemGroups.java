package org.inferis.packing_boxes.item;

import org.inferis.packing_boxes.PackingBoxes;
import org.inferis.packing_boxes.block.ModBlocks;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static ItemGroup PACKING_BOXES;

    public static void registerItemGroups() {
        PackingBoxes.LOGGER.info("Registering item groups for " + PackingBoxes.MODID);

        PACKING_BOXES = Registry.register(Registries.ITEM_GROUP,
            new Identifier(PackingBoxes.MODID, "packing_boxes"),
            FabricItemGroup.builder()
                .displayName(Text.translatable("itemgroup.packing_boxes"))
                .icon(() -> new ItemStack(ModItems.PACKING_BOX))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.PACKING_TAPE);
                    entries.add(ModBlocks.PACKING_BOX);
                })
                .build());
    }
}
