package com.joshua.xpbottler;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class BottleUtil {
    private BottleUtil() {
    }

    public static void replaceOneHeldBottle(Player player, InteractionHand hand, Item replacementItem) {
        ItemStack heldStack = player.getItemInHand(hand);
        ItemStack replacementStack = new ItemStack(replacementItem);

        if (heldStack.getCount() == 1) {
            player.setItemInHand(hand, replacementStack);
            return;
        }

        heldStack.shrink(1);
        if (!player.addItem(replacementStack)) {
            player.drop(replacementStack, false);
        }
    }
}
