package by.epam.vladGolubev.multiThread;

import by.epam.vladGolubev.multiThread.entity.Port;
import by.epam.vladGolubev.multiThread.entity.Ship;
import by.epam.vladGolubev.multiThread.logic.PortManager;
import by.epam.vladGolubev.multiThread.logic.StorageManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class Main {
    private static final Port PORT = Port.getInstance();
    private static final Semaphore SEMAPHORE = new Semaphore(Port.getDockCount(), true);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final PortManager portManager = new PortManager(SEMAPHORE, PORT);

    public static void main(String[] args) {
        List<Callable<Integer>> callable = new ArrayList<>();
        for (int i = 0; i<15; i++) {
            callable.add(new Ship((int) (Math.random()*20+2), true, portManager));
        }
        for (int i = 0; i<15; i++) {
            callable.add(new Ship((int) (Math.random()*20+2), false, portManager));
        }
        try {
            StorageManager storageManager = new StorageManager(PORT);
            storageManager.setDaemon(true);
            storageManager.start();
            executorService.invokeAll(callable);
            executorService.shutdown();
        } catch (InterruptedException e) {
            LOGGER.error("error");
        }
    }
}