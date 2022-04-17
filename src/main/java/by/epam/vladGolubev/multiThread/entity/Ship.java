package by.epam.vladGolubev.multiThread.entity;

import by.epam.vladGolubev.multiThread.logic.PortManager;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Ship implements Callable<Integer>{
    private final AtomicInteger cargo = new AtomicInteger();
    private final AtomicBoolean isFull = new AtomicBoolean();
    private final AtomicInteger dockNumber = new AtomicInteger();
    private final PortManager portManager;

    public Ship(int cargo, boolean isFull, PortManager portManager) {
        this.cargo.set(cargo);
        this.isFull.set(isFull);
        this.portManager = portManager;
    }

    @Override
    public Integer call() {
        int i = cargo.get();
        portManager.takeShip(this);
        portManager.leaveShip(this);
        return i;
    }

    public AtomicInteger getCargo() {
        return cargo;
    }

    public AtomicBoolean getIsFull() {
        return isFull;
    }

    public AtomicInteger getDockNumber() {
        return dockNumber;
    }

    public void setDockNumber(int dockNumber) {
        this.dockNumber.set(dockNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ship ship)) return false;
        if (!cargo.equals(ship.cargo)) return false;
        if (!isFull.equals(ship.isFull)) return false;
        if (!dockNumber.equals(ship.dockNumber)) return false;
        return Objects.equals(portManager, ship.portManager);
    }

    @Override
    public int hashCode() {
        int result = cargo.hashCode();
        result = 31 * result + isFull.hashCode();
        result = 31 * result + dockNumber.hashCode();
        result = 31 * result + (portManager != null ? portManager.hashCode() : 0);
        return result;
    }
}