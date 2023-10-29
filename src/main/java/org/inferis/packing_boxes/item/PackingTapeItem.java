package org.inferis.packing_boxes.item;

import org.inferis.packing_boxes.block.BoxBlock;
import org.inferis.packing_boxes.block.BoxBlockEntity;
import org.inferis.packing_boxes.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PackingTapeItem extends Item {

    public PackingTapeItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient || context.getPlayer().isSpectator()) {
            return ActionResult.SUCCESS;
        }

        BlockPos blockPos = context.getBlockPos();
        BlockState targetState = world.getBlockState(blockPos);
        
        if (targetState.getBlock() instanceof BoxBlock) {
            return ActionResult.PASS;
        }

        Block targetBlock = targetState.getBlock();
        BlockEntity targetEntity = world.getBlockEntity(blockPos);

        // turn block into box
        BlockState destState = ModBlocks.PACKING_BOX.getDefaultState();
        world.setBlockState(blockPos, destState);
        if (world.getBlockEntity(blockPos) instanceof BoxBlockEntity boxEntity) {
            boxEntity.populateContainer(targetBlock, targetState, targetEntity);
        }
        context.getStack().decrement(1);

        return ActionResult.SUCCESS;
    }
}
