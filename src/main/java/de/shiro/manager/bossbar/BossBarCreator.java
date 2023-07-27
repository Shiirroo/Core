package de.shiro.manager.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class BossBarCreator {

    private final BossBar bossBar;
    private final Plugin plugin;
    private final String title;
    private Integer bossbarTime;
    private Integer taskID;
    private Consumer<Boolean> completeFunction;
    private Consumer<Boolean> shortlyFunction;
    private double progress = 1.0;
    private double time;
    private int timer;


    public BossBarCreator(Plugin plugin, String title, Integer bossbarTime) {
        this.plugin = plugin;
        this.title = title;
        this.bossBar = Bukkit.createBossBar(this.updateBossBarTitle(), BarColor.RED, BarStyle.SOLID);
        this.bossbarTime = bossbarTime;
        this.timer = bossbarTime;
        this.time = 1.0 / bossbarTime;

    }


    public void setTime(Integer time) {
        this.bossbarTime = time;
        this.timer = time;
        this.time = 1.0 / time;
    }


    public void setBossBarPlayer(Player player) {
        this.bossBar.addPlayer(player);
    }


    public void setBossBarPlayers() {
        this.bossBar.setTitle(updateBossBarTitle());
        Bukkit.getOnlinePlayers().forEach(this.bossBar::addPlayer);
        this.taskID = TastID();
    }

    public BossBar getBossBar() {
        return this.bossBar;
    }

    public String updateBossBarTitle() {
        String bossBarTitle = (this.title).replace("TIMER", String.valueOf(this.timer));
        bossBarTitle = bossBarTitle.replace("ONLINEPLAYERS", String.valueOf(Bukkit.getOnlinePlayers().stream().filter(e -> !e.getGameMode().equals(GameMode.SPECTATOR)).count()));
        return bossBarTitle;
    }

    public BossBarCreator onComplete(Consumer<Boolean> completeFunction) {
        this.completeFunction = completeFunction;
        return this;
    }

    public BossBarCreator onShortlyComplete(Consumer<Boolean> shortlyFunction) {
        this.shortlyFunction = shortlyFunction;
        return this;
    }

    public Consumer<Boolean> getCompleteFunction() {
        return completeFunction;
    }

    public Consumer<Boolean> getShortlyFunction() {
        return shortlyFunction;
    }

    public boolean isRunning() {
        return taskID != null;
    }

    public int getTimer() {
        return this.timer;
    }

    private int TastID() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
            this.bossBar.setProgress(this.progress);
            this.bossBar.setTitle(updateBossBarTitle());
            timer = timer - 1;
            progress = this.progress - this.time;
            if (timer < 2) {
                if (shortlyFunction != null)
                    shortlyFunction.accept(false);
            }
            if (progress < 0) {
                cancel();
                if (completeFunction != null) {
                        completeFunction.accept(true);
                }
            }
        }, 0, 20);
    }






    public void cancel() {
        if (this.taskID != null) {
            Bukkit.getScheduler().cancelTask(this.taskID);
            this.taskID = null;
            if (bossBar != null) {
                this.bossBar.removeAll();
            }
            this.progress = 1.0;
            assert this.bossBar != null;
            this.bossBar.setProgress(this.progress);
            this.timer = this.bossbarTime;
            this.time = 1.0 / this.bossbarTime;
            this.bossBar.setTitle(this.updateBossBarTitle());
        }
    }
}
