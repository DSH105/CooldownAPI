package com.dsh105.cooldown;

import com.dsh105.commodus.IdentUtil;
import com.dsh105.commodus.TimeUnit;
import com.google.common.collect.MapMaker;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CooldownAPI {

    private static final String DEFAULT = "DEFAULT";

    private static final ConcurrentMap<String, Map<String, Cooldown>> COOLDOWNS = new ConcurrentHashMap<>();

    public static Cooldown create(Player player, long cooldownTime) {
        return create(player, cooldownTime, TimeUnit.SECONDS);
    }

    public static Cooldown create(Player player, long cooldownTime, TimeUnit timeUnit) {
        if (player == null || !player.isOnline()) {
            throw new IllegalArgumentException("Player is not online!");
        }
        return create(IdentUtil.getIdentificationForAsString(player), DEFAULT, cooldownTime, timeUnit);
    }

    public static Cooldown create(Player player, String key, long cooldownTime) {
        return create(player, key, cooldownTime, TimeUnit.SECONDS);
    }

    public static Cooldown create(Player player, String key, long cooldownTime, TimeUnit timeUnit) {
        if (player == null || !player.isOnline()) {
            throw new IllegalArgumentException("Player is not online!");
        }
        return create(IdentUtil.getIdentificationForAsString(player), key, cooldownTime, timeUnit);
    }

    private static Cooldown create(String playerIdent, String key, long cooldownTime) {
        return create(playerIdent, key, cooldownTime, TimeUnit.SECONDS);
    }

    private static Cooldown create(String playerIdent, String key, long cooldownTime, TimeUnit timeUnit) {
        Cooldown cooldown = new Cooldown(playerIdent, cooldownTime, timeUnit);
        Map<String, Cooldown> cooldowns = getCooldowns(playerIdent);
        if (cooldowns == null) {
            cooldowns = new HashMap<>();
        }
        cooldowns.put(key, cooldown);
        COOLDOWNS.put(playerIdent, cooldowns);
        return cooldown;
    }

    public static Map<String, Map<String, Cooldown>> getAllCooldowns() {
        return Collections.unmodifiableMap(COOLDOWNS);
    }

    public static Map<String, Cooldown> getCooldowns(Player player) {
        return getCooldowns(IdentUtil.getIdentificationForAsString(player));
    }

    private static Map<String, Cooldown> getCooldowns(String playerIdent) {
        return Collections.unmodifiableMap(getAllCooldowns().get(playerIdent));
    }

    public static Cooldown getCooldownFor(Player player) {
        return getCooldownFor(player, DEFAULT);
    }

    public static Cooldown getCooldownFor(Player player, String key) {
        return getCooldownFor(IdentUtil.getIdentificationForAsString(player), key);
    }

    private static Cooldown getCooldownFor(String playerIdent, String key) {
        Cooldown cooldown = getCooldowns(playerIdent).get(key);
        if (cooldown == null) {
            cooldown = new Cooldown(playerIdent, 0, TimeUnit.SECONDS);
        }
        return cooldown;
    }

    public static void cancel(Cooldown cooldown) {
        COOLDOWNS.remove(cooldown.getPlayerIdent());
    }
}