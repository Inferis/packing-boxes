package org.inferis.easycrates.block;

import net.minecraft.util.math.BlockPos;

import org.inferis.easycrates.ImplementedInventory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.inferis.easycrates.item.CrateBlockItem;

public class CrateBlock extends Block implements BlockEntityProvider {
    public CrateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrateBlockEntity(pos, state);
    }

    // @Override
    // public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    //     // don't do anything if its the same type of block
    //     if (state.isOf(newState.getBlock())) {
    //         return;
    //     }

    //     // if there is no air in the new state, it means the block isn't breaking
    //     if (newState.isOf(Blocks.AIR)) {
    //         BlockEntity blockEntity = world.getBlockEntity(pos);
    //         if (blockEntity instanceof CrateBlockEntity boxEntity) {
    //             Block crate = state.getBlock();
    //             CrateBlockItem crateItem = (CrateBlockItem)crate.asItem();
   
    //             DropInventory inventory = new DropInventory(crateItem, boxEntity.createNbtWithId());
    //             ItemScatterer.spawn(world, pos, inventory);
    //             world.updateComparators(pos, this);
    //         }
    //     }
    // }
    
    private class DropInventory implements ImplementedInventory {
        private ItemStack itemStack;

        public DropInventory(Item item, NbtCompound nbt) {
            itemStack = new ItemStack(item, 1);
            BlockItem.setBlockEntityNbt(itemStack, ModBlockEntities.CRATE_ENTITY, nbt);
        }

        @Override
        public DefaultedList<ItemStack> getItems() {
            return DefaultedList.ofSize(1, itemStack);
        }
    }

    // @Override   
    // public void onPlaced(World world, BlockPos blockPos, BlockState state, LivingEntity placer, ItemStack itemStack) {
    //     if (world.isClient || placer.isSpectator() || !placer.isSneaking()) {
    //         return;
    //     }

    //     BlockEntity entity = world.getBlockEntity(blockPos);
    //     if (entity instanceof CrateBlockEntity boxEntity) {
    //         int itemId = boxEntity.container.itemId;
    //         if (itemId != 0) {
    //             BlockEntity currentEntity = world.getBlockEntity(blockPos);
    //             currentEntity.markRemoved();

    //             Block targetBlock = Block.getBlockFromItem(Item.byRawId(itemId));
    //             BlockState targetState = targetBlock.getDefaultState();

    //             world.setBlockState(blockPos, targetState);
                
    //             if (boxEntity.container.entityNbt != null) {
    //                 BlockEntity.createFromNbt(blockPos, targetState, boxEntity.container.entityNbt);
    //             }
    //         }
    //     }
    // }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos blockPos, BlockState state, BlockEntity blockEntity,
            ItemStack tool) {
        if (blockEntity instanceof CrateBlockEntity boxEntity) {
            Block crate = state.getBlock();
            CrateBlockItem crateItem = (CrateBlockItem)crate.asItem();

            DropInventory inventory = new DropInventory(crateItem, boxEntity.createNbtWithId());
            ItemScatterer.spawn(world, blockPos, inventory);
            world.updateComparators(blockPos, this);
        }
    
        super.afterBreak(world, player, blockPos, state, blockEntity, tool);
    }

    public static ActionResult useBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (world.isClient || player.isSpectator()) {
            return ActionResult.SUCCESS;
        }

        BlockPos blockPos = hitResult.getBlockPos();
        BlockEntity entity = world.getBlockEntity(blockPos);

        if (entity instanceof CrateBlockEntity boxEntity) {
            if (!player.isSneaking()) {
                return ActionResult.PASS;
            }

            int itemId = boxEntity.container.itemId;
            if (itemId != 0) {
                BlockEntity currentEntity = world.getBlockEntity(blockPos);
                currentEntity.markRemoved();

                Block targetBlock = Block.getBlockFromItem(Item.byRawId(itemId));
                BlockState targetState = targetBlock.getDefaultState();

                world.setBlockState(blockPos, targetState);
                
                if (boxEntity.container.entityNbt != null) {
                    BlockEntity.createFromNbt(blockPos, targetState, boxEntity.container.entityNbt);
                }
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }
}

