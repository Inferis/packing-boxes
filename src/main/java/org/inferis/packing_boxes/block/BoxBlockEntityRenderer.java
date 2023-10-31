package org.inferis.packing_boxes.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class BoxBlockEntityRenderer implements BlockEntityRenderer<BoxBlockEntity> {
    // A jukebox itemstack

    public BoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(BoxBlockEntity boxEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int itemId = boxEntity.container.itemId;
        boolean shouldBeDark = false;
        if (itemId == 0) {
            itemId = Item.getRawId(Items.BEDROCK);
            shouldBeDark = true;
        }

        ItemStack stack = new ItemStack(Item.byRawId(itemId), 1);

        matrices.push();
        matrices.translate(0.157f, 0.704f, 0.907f);
        matrices.scale(0.755f, 0.755f, 0.755f);

        World world = boxEntity.getWorld();
        int lightAbove = WorldRenderer.getLightmapCoordinates(world, boxEntity.getPos().south());
        if (shouldBeDark) {
            lightAbove = (int)((float)lightAbove * 0.2f);
        }

        MinecraftClient
            .getInstance()
            .getItemRenderer()
            .renderItem(stack, ModelTransformationMode.GROUND, lightAbove, overlay, matrices, vertexConsumers, world, 0);

        matrices.pop();
    }
}
