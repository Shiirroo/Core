package de.shiro.utlits;

import de.shiro.Core;
import de.shiro.system.config.ISession;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class Config {

    @Getter
    private static final ExecutorService service = getForkJoinPool();

    @Getter
    private static final UUID ShiroUUID = UUID.fromString("69f07a5c-c28f-35ac-89c6-90121cd6ef56");

    @Getter
    private static final ISession serverSession = new ISession(UUID.fromString("00000000-0000-0000-0000-000000000000"), ChatColor.RED +"SERVER");



    private static ForkJoinPool getForkJoinPool() {
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            String[] name = worker.getName().split("-");
            worker.setName(Core.class.getSimpleName() + "Pool-" + name[1]+ "-" + name[2]+ "-" + name[3]);
            return worker;
        };

        return new ForkJoinPool(8, factory, null, true);
    }


}
