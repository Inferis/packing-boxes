package org.inferis.packing_boxes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class BoxBlockEntity extends BlockEntity {
    public final BoxContainer container = new BoxContainer();

    public BoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PACKING_BOX_ENTITY, pos, state);
    }

    public void copyFrom(BoxBlockEntity otherEntity) {
        container.entityNbt = otherEntity.container.entityNbt;
        container.itemId = otherEntity.container.itemId;
        container.blockState = otherEntity.container.blockState;
    }

    public void populateContainer(Block block, BlockState state, BlockEntity entity) {
        // Store Item Id
        int itemId = Item.getRawId(block.asItem());
        container.itemId = itemId;

        // Store target state
        container.blockState = state;

        // Store entity nbt
        if (entity != null) {
            RegistryWrapper.WrapperLookup registryLookup = BuiltinRegistries.createWrapperLookup();
            container.entityNbt = entity.createNbtWithId(registryLookup);
        }
        else {
            container.entityNbt = null;
        }

        markDirty();
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        container.writeNbt(nbt);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        container.readNbt(nbt);
    }

    public class BoxContainer {
        public int itemId = 0;
        public NbtCompound entityNbt = null;
        public BlockState blockState = null;

        public void writeNbt(NbtCompound nbt) {
            NbtCompound containerNbt = new NbtCompound();

            // Save item ite
            containerNbt.putInt("itemId", itemId);

            // Save entity nbt
            if (entityNbt != null) {
                containerNbt.put("entityNbt", entityNbt);
            }

            if (blockState != null) {
                NbtCompound stateNbt = NbtHelper.fromBlockState(blockState);
                if (stateNbt != null) {
                    containerNbt.put("blockStateNbt", stateNbt);
                }
            }

            nbt.put("packing_boxes", containerNbt);
        }

        public void readNbt(NbtCompound nbt) {
            NbtCompound containerNbt = nbt.getCompound("packing_boxes");
            if (containerNbt.contains("entityNbt")) {
                entityNbt = (NbtCompound)containerNbt.get("entityNbt");
            }

            itemId = containerNbt.getInt("itemId");

            if (containerNbt.contains("blockStateNbt")) {
                NbtCompound blockStateNbt = containerNbt.getCompound("blockStateNbt");
                blockState = NbtHelper.toBlockState(Registries.BLOCK, blockStateNbt);
            }
        }
    }
}
