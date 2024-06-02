package org.inferis.packing_boxes.item;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;

import org.inferis.packing_boxes.PackingBoxes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BoxBlockItem extends BlockItem {
    public BoxBlockItem(Block block, Settings settings) {
        super(block, settings.maxCount(1));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType data) {
        super.appendTooltip(stack, context, tooltip, data);
        NbtCompound nbt = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA, NbtComponent.DEFAULT).getNbt();
        if (nbt != null) {
            NbtCompound modNbt = nbt.getCompound(PackingBoxes.MODID);
            if (modNbt != null) {
                int itemId = modNbt.getInt("itemId");
                if (itemId > 0) {
                    Text itemName = Item.byRawId(itemId).getName()
                        .copy()
                        .formatted(Formatting.YELLOW);
                    tooltip.add(Text.literal("Contains ").append(itemName));
                }
            }
        }
        else {
            tooltip.add(Text.literal("no itemId"));
        }
    }

    // @Override
    // public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
    //     super.appendTooltip(stack, world, tooltip, context);
    //     NbtCompound nbt = BlockItem.getBlockEntityNbt(stack);
    //     if (nbt != null) {
    //         NbtCompound modNbt = nbt.getCompound(PackingBoxes.MODID);
    //         if (modNbt != null) {
    //             int itemId = modNbt.getInt("itemId");
    //             if (itemId > 0) {
    //                 Text itemName = Item.byRawId(itemId).getName()
    //                     .copy()
    //                     .formatted(Formatting.YELLOW);
    //                 tooltip.add(Text.literal("Contains ").append(itemName));
    //             }
    //         }
    //     }
    //     else {
    //         tooltip.add(Text.literal("no itemId"));
    //     }
    // }
}
