package org.inferis.packing_boxes.block;

import net.minecraft.util.math.BlockPos;

import org.inferis.packing_boxes.helper.ImplementedInventory;
import org.inferis.packing_boxes.item.BoxBlockItem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class BoxBlock extends Block implements BlockEntityProvider {
    public BoxBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BoxBlockEntity(pos, state);
    }

    private class DropInventory implements ImplementedInventory {
        private ItemStack itemStack;

        public DropInventory(Item item, NbtCompound nbt) {
            itemStack = new ItemStack(item, 1);
            BlockItem.setBlockEntityNbt(itemStack, ModBlockEntities.PACKING_BOX_ENTITY, nbt);
        }

        @Override
        public DefaultedList<ItemStack> getItems() {
            return DefaultedList.ofSize(1, itemStack);
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos blockPos, BlockState state, BlockEntity blockEntity,
            ItemStack tool) {
        if (blockEntity instanceof BoxBlockEntity boxEntity) {
            Block box = state.getBlock();
            BoxBlockItem boxItem = (BoxBlockItem)box.asItem();

            DropInventory inventory = new DropInventory(boxItem, boxEntity.createNbt());
            ItemScatterer.spawn(world, blockPos, inventory);
            world.updateComparators(blockPos, this);
        }
    
        super.afterBreak(world, player, blockPos, state, blockEntity, tool);
    }
    
    public static ActionResult useBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (player.isSpectator()) {
            return ActionResult.SUCCESS;
        }

        BlockEntity entity = world.getBlockEntity(blockPos);

        if (entity instanceof BoxBlockEntity boxEntity) {
            if (!player.isSneaking()) {
                return ActionResult.PASS;
            }

            if (world.isClient) {
                // We're going to assume that everything went ok server side.
                world.addBlockBreakParticles(blockPos, blockState);
                return ActionResult.SUCCESS;
            }

            int itemId = boxEntity.container.itemId;
            if (itemId != 0) {
                // Remove current box block entity, but we need to reconstruct
                // our saved block.
                BlockEntity currentEntity = world.getBlockEntity(blockPos);
                currentEntity.markRemoved();

                Block savedBlock = Block.getBlockFromItem(Item.byRawId(itemId));

                // Restore saved block
                BlockState savedState;
                if (boxEntity.container.blockState != null) {
                    savedState = boxEntity.container.blockState;
                }
                else {
                    savedState = savedBlock.getDefaultState();                    
                }
                world.setBlockState(blockPos, savedState);
                
                // resture the block's nbt
                if (boxEntity.container.entityNbt != null) {
                    BlockEntity savedEntity = BlockEntity.createFromNbt(blockPos, savedState, boxEntity.container.entityNbt);
                    world.addBlockEntity(savedEntity);
                }
                
                // and we need a sound of course
                world.playSound(null, blockPos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS);
                
                return ActionResult.SUCCESS;
            }
            else {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            }
        }

        return ActionResult.PASS;
    }
}

