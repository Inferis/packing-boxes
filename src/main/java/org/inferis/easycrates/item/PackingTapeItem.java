package org.inferis.easycrates.item;

import org.inferis.easycrates.block.CrateBlock;
import org.inferis.easycrates.block.CrateBlockEntity;
import org.inferis.easycrates.block.ModBlocks;

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
        
        if (targetState.getBlock() instanceof CrateBlock) {
            return ActionResult.PASS;
        }

        Block targetBlock = targetState.getBlock();
        BlockEntity targetEntity = world.getBlockEntity(blockPos);

        // turn block into box
        BlockState destState = ModBlocks.CRATE.getDefaultState();
        world.setBlockState(blockPos, destState);
        if (world.getBlockEntity(blockPos) instanceof CrateBlockEntity boxEntity) {
            if (targetEntity != null) {
                boxEntity.container.entityNbt = targetEntity.createNbtWithId();
            }
            int itemId = Item.getRawId(targetBlock.asItem());
            boxEntity.container.itemId = itemId;
            boxEntity.markDirty();
        }
        context.getStack().decrement(1);

        return ActionResult.SUCCESS;
    }
}
