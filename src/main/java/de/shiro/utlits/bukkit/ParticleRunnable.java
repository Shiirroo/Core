package de.shiro.utlits.bukkit;

import de.shiro.Record;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleRunnable extends BukkitRunnable {
    private final Particle particle;
    private final Particle.DustOptions dustOptions;
    private final Location location;
    private final int maxRepetitions;
    private int repetitions;
    private final int particleCount;

    public ParticleRunnable(Particle particle,Location location, int particleCount, int maxRepetitions) {
        this.particle = particle;
        this.dustOptions = null;
        this.location = location;
        this.maxRepetitions = maxRepetitions;
        this.repetitions = 0;
        this.particleCount = particleCount;
    }

    public ParticleRunnable(Particle particle, Particle.DustOptions dustOptions,  Location location, int particleCount, int maxRepetitions) {
        this.particle = particle;
        this.dustOptions = dustOptions;
        this.location = location;
        this.maxRepetitions = maxRepetitions;
        this.repetitions = 0;
        this.particleCount = particleCount;
    }

    @Override
    public void run() {
        if (repetitions < maxRepetitions) {
            World world = location.getWorld();
            if (world != null) {
                if(dustOptions != null) world.spawnParticle(particle, location, particleCount, dustOptions);
                else world.spawnParticle(particle, location, particleCount);
            }
            repetitions++;
        } else {
            cancel(); // Stop the runnable when reaching the maximum repetitions
        }
    }

    public void start(long delay, long period) {
        runTaskTimer(Record.getInstance(), delay, period);
    }

    public void stop() {
        cancel();
    }
}





