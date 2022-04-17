package by.epam.vladGolubev.multiThread.logic;

import by.epam.vladGolubev.multiThread.entity.Port;
import org.apache.log4j.Logger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class StorageManager extends Thread {
    private final ReentrantLock lock = new ReentrantLock();
    private final Port port;
    private static final Logger LOGGER = Logger.getLogger(StorageManager.class);

    public StorageManager (Port port) {
        this.port = port;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (port.getStorage().get()<(Port.getStorageCapacity()/2+1)) {
                port.setStorage((Port.getStorageCapacity()/10*9));
            }
            lock.unlock();
            lock.lock();
            if (port.getStorage().get()>(Port.getStorageCapacity()/5)*4) {
                port.setStorage(Port.getStorageCapacity()/10);
            }
            lock.unlock();
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("demon error");
            }
        }
    }
}