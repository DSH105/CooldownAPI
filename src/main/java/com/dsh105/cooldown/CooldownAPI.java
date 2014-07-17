package com.dsh105.cooldown;

import com.dsh105.commodus.IdentUtil;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownAPI {

    private static final ConcurrentHashMap<String, Cooldown> COOLDOWNS = new ConcurrentHashMap<>();

    public static Cooldown create(Player player, long cooldownTime) {
        return create(player, cooldownTime, TimeUnit.SECONDS);
    }

    public static Cooldown create(Player player, long cooldownTime, TimeUnit timeUnit) {
        if (player == null || !player.isOnline()) {
            throw new IllegalArgumentException("Player is NULL!");
        }

        String playerIdent = IdentUtil.getIdentificationForAsString(player);
        Cooldown cooldown = new Cooldown(playerIdent, cooldownTime, timeUnit);
        COOLDOWNS.put(playerIdent, cooldown);
        return cooldown;
    }

    public static Map<String, Cooldown> getCooldowns() {
        return Collections.unmodifiableMap(COOLDOWNS);
    }

    public static Cooldown getCooldownFor(Player player) {
        return getCooldownFor(IdentUtil.getIdentificationForAsString(player));
    }

    private static Cooldown getCooldownFor(String playerIdent) {
        Cooldown cooldown = getCooldowns().get(playerIdent);
        if (cooldown == null) {
            cooldown = new Cooldown(playerIdent, 0, TimeUnit.SECONDS);
        }
        return cooldown;
    }

    public static void cancel(Cooldown cooldown) {
        COOLDOWNS.remove(cooldown.getPlayerIdent());
    }
}