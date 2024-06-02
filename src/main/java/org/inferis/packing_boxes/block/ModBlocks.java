package org.inferis.packing_boxes.block;

import org.inferis.packing_boxes.PackingBoxes;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static BoxBlock PACKING_BOX;

    private static <T extends Block> T registerBlock(String name, T block) {
        PackingBoxes.LOGGER.info("Registering block: " + PackingBoxes.MODID + ":" + name);

        return Registry.register(
            Registries.BLOCK,
            Identifier.of(PackingBoxes.MODID + ":" + name),
            block);
    }


    public static void registerBlocks() {
        PACKING_BOX = registerBlock("packing_box", new BoxBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));
        ModBlockEntities.registerBlockEntities();
    }

    public static void registerUseBlockEvent() {
		UseBlockCallback.EVENT.register(BoxBlock::useBlock);
    }
}
