package org.inferis.easycrates.item;

import org.inferis.easycrates.EasyCrates;
import org.inferis.easycrates.block.ModBlocks;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static ItemGroup CRATES;

    public static void registerItemGroups() {
        EasyCrates.LOGGER.info("Registering item groups for " + EasyCrates.MODID);

        CRATES = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EasyCrates.MODID, "packing"),
            FabricItemGroup.builder()
                .displayName(Text.translatable("itemgroup.packing"))
                .icon(() -> new ItemStack(ModItems.PACKING_TAPE))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.PACKING_TAPE);
                    entries.add(ModBlocks.CRATE);
                })
                .build());
    }
}
