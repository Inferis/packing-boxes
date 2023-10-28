package org.inferis.easycrates.block;

import org.inferis.easycrates.ImplementedInventory;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class CrateBlockEntity extends BlockEntity implements ImplementedInventory {
    public final CrateContainer container = new CrateContainer();
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public CrateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRATE_ENTITY, pos, state);
    }

    public void copyFrom(CrateBlockEntity otherEntity) {
        container.entityNbt = otherEntity.container.entityNbt;
        container.itemId = otherEntity.container.itemId;
        // items.clear();
        // for (var item: otherEntity.items) {
        //     items.add(item);
        // }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        container.writeNbt(nbt);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        container.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }

    public class CrateContainer {
        public NbtCompound entityNbt = null;
        public int itemId = 0;

        public void writeNbt(NbtCompound nbt) {
            NbtCompound containerNbt = new NbtCompound();
            if (entityNbt != null) {
                containerNbt.put("entityNbt", entityNbt);
            }
            containerNbt.putInt("itemId", itemId);
            nbt.put("easycrates", containerNbt);
        }
    
        public void readNbt(NbtCompound nbt) {
            NbtCompound containerNbt = nbt.getCompound("easycrates");
            if (containerNbt.contains("entityNbt")) {
                entityNbt = (NbtCompound)containerNbt.get("entityNbt");
            }
            itemId = containerNbt.getInt("itemId");
        }
    }
}
