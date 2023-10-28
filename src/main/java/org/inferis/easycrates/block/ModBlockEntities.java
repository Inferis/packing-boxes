package org.inferis.easycrates.block;

import org.inferis.easycrates.EasyCrates;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<CrateBlockEntity> CRATE_ENTITY;

    public static void registerBlockEntities() {
        CRATE_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(EasyCrates.MODID, "crate_block_entity"),
            FabricBlockEntityTypeBuilder.create(CrateBlockEntity::new, ModBlocks.CRATE).build()
        );    
    }
}
