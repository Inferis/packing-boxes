package org.inferis.easycrates.block;

import org.inferis.easycrates.EasyCrates;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static CrateBlock CRATE;

    private static <T extends Block> T registerBlock(String name, T block) {
        EasyCrates.LOGGER.info("Registering block: " + EasyCrates.MODID + ":" + name);

        return Registry.register(
            Registries.BLOCK,
            new Identifier(EasyCrates.MODID, name),
            block);
    }


    public static void registerBlocks() {
        CRATE = registerBlock("crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));
        ModBlockEntities.registerBlockEntities();
    }

    public static void registerUseBlockEvent() {
		UseBlockCallback.EVENT.register(CrateBlock::useBlock);
    }
}
