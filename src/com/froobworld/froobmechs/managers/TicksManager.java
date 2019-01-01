package com.froobworld.froobmechs.managers;

import com.froobworld.frooblib.data.TaskManager;
import com.froobworld.froobmechs.FroobMechs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class TicksManager extends TaskManager {
    private int sampleTime;

    private long lastTick;

    private ArrayList<Long> deltas;

    public TicksManager(int sampleTime) {
        super(FroobMechs.getPlugin());
        this.sampleTime = sampleTime;
    }


    @Override
    public void ini() {
        lastTick = System.currentTimeMillis();
        deltas = new ArrayList<Long>();

        addTask(1, 1, new Runnable() {

            @Override
            public void run() {
                task();
            }
        });
    }

    public void task() {
        if (deltas.size() == sampleTime) {
            deltas.remove(0);
        }
        deltas.add(System.currentTimeMillis() - lastTick);
        lastTick = System.currentTimeMillis();
    }

    public double getTPS() {
        long total = 0;
        for (Long l : deltas) {
            total += l;
        }

        return round(Double.valueOf(deltas.size()) / (Double.valueOf(total)) * 1000);
    }

    private double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

}
