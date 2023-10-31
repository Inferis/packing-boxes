package org.inferis.packing_boxes;

import org.inferis.packing_boxes.block.BoxBlockEntityRenderer;
import org.inferis.packing_boxes.block.ModBlockEntities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class PackingBoxesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(ModBlockEntities.PACKING_BOX_ENTITY, BoxBlockEntityRenderer::new);
    }
    
}
