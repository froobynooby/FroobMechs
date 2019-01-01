package com.froobworld.froobmechs.managers;

import com.froobworld.frooblib.data.TaskManager;
import com.froobworld.froobmechs.FroobMechs;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.ArrayList;

public class EntityManager extends TaskManager {
    private TicksManager ticksManager;

    private int ticks;

    public EntityManager() {
        super(FroobMechs.getPlugin());
    }


    @Override
    public void ini() {
        ticksManager = new TicksManager(1200);
        ticksManager.ini();
        ticks = 0;

        addTask(1200, 1200, new Runnable() {

            @Override
            public void run() {
                task();
            }
        });
    }

    private void task() {
        Double tps = ticksManager.getTPS();
        ticks++;
        if (ticks >= 5 && tps > 19) {
            ticks = 0;
            for (World world : Bukkit.getWorlds()) {
                for (LivingEntity entity : world.getLivingEntities()) {
                    if (!entity.hasAI()) {
                        entity.setAI(true);
                    }
                }
            }
        }
        if (tps > 18.5) {
            return;
        }
        int affectedEntities = 0;
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (entity instanceof Tameable || entity instanceof Enderman || !(entity instanceof Animals || entity instanceof Monster || entity instanceof Villager)) {
                    continue;
                }
                if (entity.hasAI()) {
                    if (entity instanceof Turtle || entity instanceof Fish) {
                        entity.setAI(false);
                        affectedEntities++;
                    }
                    ArrayList<LivingEntity> nearEntities = nearEntities(entity);
                    if (nearEntities.size() > 30.0 * Math.pow((tps / 18.5), 2)) {
                        for (LivingEntity e : nearEntities) {
                            if (e.hasAI()) {
                                e.setAI(false);
                                affectedEntities++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("TPS low (" + tps + "). Set " + affectedEntities + " to no AI.");
    }

    private ArrayList<LivingEntity> nearEntities(LivingEntity entity) {
        ArrayList<Chunk> nearChunks = new ArrayList<Chunk>();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Chunk adjacentChunk = entity.getLocation().clone().add(x, 0, z).getChunk();
                if (adjacentChunk.isLoaded() && !nearChunks.contains(adjacentChunk)) {
                    nearChunks.add(adjacentChunk);
                }
            }
        }
        ArrayList<LivingEntity> entities = new ArrayList<LivingEntity>();
        for (Chunk chunk : nearChunks) {
            for (Entity chunkEntity : chunk.getEntities()) {
                if (chunkEntity instanceof LivingEntity) {
                    entities.add((LivingEntity) chunkEntity);
                }
            }
        }

        return entities;
    }

}
