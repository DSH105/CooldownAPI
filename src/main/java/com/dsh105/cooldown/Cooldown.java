package com.dsh105.cooldown;

import com.dsh105.commodus.IdentUtil;
import com.dsh105.commodus.TimeUnit;
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
        return IdentUtil.getPlayerOf(playerIdent);
    }

    public void setCooldownTime(long cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public void setCooldownTime(long cooldownTime, TimeUnit timeUnit) {
        this.cooldownTime = timeUnit.toMilliseconds(cooldownTime);
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.getTime(startTime);
    }

    public long getTimeLeft(TimeUnit timeUnit) {
        return timeUnit.getTime(getTimeLeft());
    }

    public long getTimeLeft() {
        long timePassed = System.currentTimeMillis() - startTime;
        long timeLeft = timePassed - cooldownTime;
        return timeLeft <= 0 ? 0 : timeLeft;
    }

    public boolean isFinished() {
        boolean finished = getTimeLeft() > 1;
        if (finished) {
            cancel();
        }
        return finished;
    }

    public void cancel() {
        CooldownAPI.cancel(this);
    }

    public void restart(long cooldownTime) {
        this.restart(cooldownTime, TimeUnit.MILLISECONDS);
    }

    public void restart(long cooldownTime, TimeUnit timeUnit) {
        this.setCooldownTime(cooldownTime, timeUnit);
        restart();
    }

    public void restart() {
        startTime = System.currentTimeMillis();
    }
}