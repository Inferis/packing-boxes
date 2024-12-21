package org.inferis.packing_boxes.block;

import org.inferis.packing_boxes.PackingBoxes;
import org.inferis.packing_boxes.block.ModBlocks.BlockMaker;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static BoxBlock PACKING_BOX;

    interface BlockMaker<T extends Block> {
        T makeBlock(RegistryKey<Block> key);
    }

    private static <T extends Block> T registerBlock(String name, BlockMaker<T> blockMaker) {
        PackingBoxes.LOGGER.info("Registering block: " + PackingBoxes.MODID + ":" + name);

        Identifier identifier = Identifier.of(PackingBoxes.MODID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, identifier);
        return Registry.register(
            Registries.BLOCK,
            identifier,
            blockMaker.makeBlock(key));
    }


    public static void registerBlocks() {
        PACKING_BOX = registerBlock("packing_box", key -> { 
            return new BoxBlock(AbstractBlock.Settings.copy(Blocks.CHEST).registryKey(key)); 
        });
        ModBlockEntities.registerBlockEntities();
    }

    public static void registerUseBlockEvent() {
		UseBlockCallback.EVENT.register(BoxBlock::useBlock);
    }
}
