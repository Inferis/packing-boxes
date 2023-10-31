package org.inferis.packing_boxes.block;

import org.inferis.packing_boxes.PackingBoxes;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<BoxBlockEntity> PACKING_BOX_ENTITY;

    public static void registerBlockEntities() {
        PACKING_BOX_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(PackingBoxes.MODID, "packing_box_block_entity"),
            FabricBlockEntityTypeBuilder.create(BoxBlockEntity::new, ModBlocks.PACKING_BOX).build()
        );    
    }
}
