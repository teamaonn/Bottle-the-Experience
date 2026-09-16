package com.joshua.xpbottler.mixin;

import com.joshua.xpbottler.BottleUtil;
import com.joshua.xpbottler.XpBottlerMod;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BottleItem.class)
public class BottleItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void xpbottler$bottleExperience(
            Level level,
            Player player,
            InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> cir
    ) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isShiftKeyDown() || stack.getItem() != Items.GLASS_BOTTLE) {
            return;
        }

        if (level.isClientSide()) {
            cir.setReturnValue(InteractionResult.CONSUME);
            return;
        }

        if (!player.isCreative() && player.totalExperience < XpBottlerMod.XP_PER_BOTTLE) {
            cir.setReturnValue(InteractionResult.FAIL);
            return;
        }

        if (!player.isCreative()) {
            player.giveExperiencePoints(-XpBottlerMod.XP_PER_BOTTLE);
        }

        BottleUtil.replaceOneHeldBottle(player, hand, Items.EXPERIENCE_BOTTLE);
        cir.setReturnValue(InteractionResult.CONSUME);
    }
}
