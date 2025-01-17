package net.draycia.carbon.api.users;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * Manager used to load and obtain {@link CarbonPlayer CarbonPlayers}.
 *
 * @since 2.0.0
 */
public interface UserManager {

  /**
   * Loads and returns a {@link CarbonPlayer} with the given {@link UUID}.
   *
   * @param uuid The player's UUID.
   *
   * @return The {@link CarbonPlayer}, or null if the player doesn't exist.
   *
   * @since 2.0.0
   */
  @Nullable CarbonPlayer carbonPlayer(final @NonNull UUID uuid);

  /**
   * Loads and returns a {@link CarbonPlayer} with the given username.
   *
   * @param username The player's username.
   *
   * @return The {@link CarbonPlayer}, or null if the player doesn't exist.
   *
   * @since 2.0.0
   */
  @Nullable CarbonPlayer carbonPlayer(final @NonNull String username);

}
