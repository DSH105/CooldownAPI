package com.dsh105.cooldown;

import com.dsh105.commodus.IdentUtil;
import org.bukkit.entity.Player;

// TODO: JavaDocs
public class Cooldown {

    private String playerIdent;
    private long startTime;
    private long cooldownTime;

    protected Cooldown(String playerIdent, long cooldownTime, TimeUnit timeUnit) {
        this.playerIdent = playerIdent;
        this.startTime = System.currentTimeMillis();
        this.cooldownTime = timeUnit.toMilliseconds(cooldownTime);
    }

    public String getPlayerIdent() {
        return playerIdent;
    }

    public Player getPlayer() {
        return IdentUtil.getPlayerOf(getPlayerIdent());
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.getTime(getStartTime());
    }

    public long getTimeLeft() {
        long timePassed = System.currentTimeMillis() - getStartTime();
        long timeLeft = timePassed - getCooldownTime();
        return timeLeft <= 0 ? 0 : timeLeft;
    }

    public boolean finished() {
        boolean finished = getTimeLeft() > 1;
        if (finished) {
            cancel();
        }
        return finished;
    }

    public void cancel() {
        CooldownAPI.cancel(this);
    }

    public void restart() {
        startTime = System.currentTimeMillis();
    }
}