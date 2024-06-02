package org.inferis.packing_boxes.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class BoxBlockEntityRenderer implements BlockEntityRenderer<BoxBlockEntity> {
    public BoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(BoxBlockEntity boxEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = boxEntity.getWorld();
        BlockPos blockPos = boxEntity.getPos();

        int itemId = boxEntity.container.itemId;
        boolean shouldBeDark = false;
        if (itemId == 0) {
            itemId = Item.getRawId(Items.BEDROCK);
            shouldBeDark = true;
        }

        Item item = Item.byRawId(itemId);
        ItemStack stack = new ItemStack(item, 1);
        boolean isSolidBlock = Block.getBlockFromItem(item)
            .getDefaultState()
            .isOpaque();

        Vec3d offset;
        float scale;

        if (isSolidBlock) {
            offset = new Vec3d(0.157f, 0.704f, 0.930);
            scale = 0.755f;
        }
        else {
            offset = new Vec3d(0.170f, 0.784f, 1.0f);
            scale = 0.47f;
        }
 
        matrices.push();

        matrices.translate(offset.x, offset.y, offset.z);
        matrices.scale(scale, scale, scale);

        int lightAbove = WorldRenderer.getLightmapCoordinates(world, blockPos.south());
        if (shouldBeDark) {
            lightAbove = (int)((float)lightAbove * 0.2f);
        }

        MinecraftClient
            .getInstance()
            .getItemRenderer()
            .renderItem(stack, ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, world, 0);

        matrices.pop();
    }
}
