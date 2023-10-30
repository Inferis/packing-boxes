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
            BlockItem.setBlockEntityNbt(itemStack, ModBlockEntities.BOX_ENTITY, nbt);
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
                world.addBlockBreakParticles(blockPos, blockState);
                return ActionResult.SUCCESS;
            }

            int itemId = boxEntity.container.itemId;
            if (itemId != 0) {
                BlockEntity currentEntity = world.getBlockEntity(blockPos);
                currentEntity.markRemoved();

                Block targetBlock = Block.getBlockFromItem(Item.byRawId(itemId));
                BlockState targetState = targetBlock.getDefaultState();

                if (boxEntity.container.blockState != null) {
                    world.setBlockState(blockPos, boxEntity.container.blockState);
                }
                
                if (boxEntity.container.entityNbt != null) {
                    BlockEntity newEntity = BlockEntity.createFromNbt(blockPos, targetState, boxEntity.container.entityNbt);
                    world.addBlockEntity(newEntity);
                }
                
                world.playSound(null, blockPos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS);
                return ActionResult.SUCCESS;
            }
            else if (!world.isClient) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            }
        }

        return ActionResult.PASS;
    }
}

