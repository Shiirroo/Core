package de.shiro.utlits;

import de.shiro.Record;
import de.shiro.system.config.ISession;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicReference;

public class Config {

    @Getter
    private static final ItemStack AIR = new ItemStack(Material.AIR);


    @Getter
    private static final ExecutorService service = getForkJoinPool();

    @Getter
    private static final UUID ShiroUUID = UUID.fromString("69f07a5c-c28f-35ac-89c6-90121cd6ef56");

    @Getter
    private static final UUID serverUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Getter
    private static final ISession serverSession = new ISession(UUID.fromString("00000000-0000-0000-0000-000000000000"), ChatColor.RED +"SERVER");


    private static ForkJoinPool getForkJoinPool() {
        final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(new ForkJoinPool());
        String[] name = worker.getName().split("-");
        worker.setName(Record.class.getSimpleName() + "Pool-" + name[1]+ "-" + name[2]+ "-" + name[3]);
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool1 -> worker;
        return new ForkJoinPool(8, factory, worker.getUncaughtExceptionHandler(), true);
    }


}
