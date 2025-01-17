package net.draycia.carbon.bukkit.users;

import net.draycia.carbon.common.users.CarbonPlayerCommon;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class CarbonPlayerBukkit extends CarbonPlayerCommon {

  public CarbonPlayerBukkit(
    final @NonNull String username,
    final @NonNull Component displayName,
    final @NonNull UUID uuid
  ) {
    super(username, displayName, uuid, Identity.identity(uuid));
  }

  @Override
  public void displayName(final @Nullable Component displayName) {
    super.displayName(displayName);

    final Player player = this.player();

    if (player != null) {
      player.displayName(displayName);
    }
  }

  @Override
  public @NonNull Audience audience() {
    final Player player = this.player();

    if (player == null) {
      return Audience.empty();
    }

    return player;
  }

  private @Nullable Player player() {
    return Bukkit.getPlayer(this.uuid);
  }

  @Override
  public @NonNull Component createItemHoverComponent() {
    final Player player = this.player(); // This is temporary (it's not)

    if (player == null) {
      return Component.empty();
    }

    final ItemStack itemStack;

    final ItemStack mainHand = player.getInventory().getItemInMainHand();

    if (!mainHand.getType().isAir()) {
      itemStack = mainHand;
    } else {
      final ItemStack offHand = player.getInventory().getItemInMainHand();

      if (!offHand.getType().isAir()) {
        itemStack = offHand;
      } else {
        itemStack = null;
      }
    }

    if (itemStack == null) {
      return Component.empty();
    }

    if (itemStack.getType().isAir()) {
      return Component.empty();
    }

    return itemStack.displayName();
  }

}
