package com.froobworld.froobmechs.managers;

import com.froobworld.frooblib.data.Storage;
import com.froobworld.frooblib.data.TaskManager;
import com.froobworld.froobmechs.FroobMechs;
import com.froobworld.froobmechs.data.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TreeManager extends TaskManager {
    private static final int REGION_DIMENSION = 1024;
    private Storage storage;
    private HashMap<String, Region> regions;
    private ArrayList<Region> saveQueue;

    public TreeManager() {
        super(FroobMechs.getPlugin());
    }

    public static Material getSaplingForWood(Material type) {
        switch (type) {
            case ACACIA_LOG:
                return Material.ACACIA_SAPLING;
            case BIRCH_LOG:
                return Material.BIRCH_SAPLING;
            case DARK_OAK_LOG:
                return Material.DARK_OAK_SAPLING;
            case OAK_LOG:
                return Material.OAK_SAPLING;
            case JUNGLE_LOG:
                return Material.JUNGLE_SAPLING;
            case SPRUCE_LOG:
                return Material.SPRUCE_SAPLING;
            default:
                return Material.AIR;
        }
    }

    public static boolean isLog(Block block) {

        return block.getType() == Material.ACACIA_LOG || block.getType() == Material.BIRCH_LOG || block.getType() == Material.DARK_OAK_LOG || block.getType() == Material.OAK_LOG || block.getType() == Material.JUNGLE_LOG || block.getType() == Material.SPRUCE_LOG;
    }

    @Override
    public void ini() {
        storage = new Storage(FroobMechs.getPlugin().getDataFolder().getPath() + "/trees");
        saveQueue = new ArrayList<Region>();
        regions = new HashMap<String, Region>();

        for (File file : storage.listFiles()) {
            load(file);
        }

        addTask(0, 1200, new Runnable() {

            @Override
            public void run() {
                task();
            }
        });
    }

    public void load(File file) {
        Region region = new Region(file);
        regions.put(region.x() + ";" + region.z(), region);
    }

    public Region getRegionForLocation(Location location) {
        int x = (int) (location.getX() / REGION_DIMENSION);
        int z = (int) (location.getZ() / REGION_DIMENSION);
        if (!regions.containsKey(x + ";" + z)) {
            return new Region(x, z, storage.getFile(x + " " + z));
        }

        return regions.get(x + ";" + z);
    }

    public boolean isNatural(Location location) {
        Region region = getRegionForLocation(location);

        return !region.isLocation(location);
    }

    public void addLog(Location location) {
        Region region = getRegionForLocation(location);

        if (region.addLocation(location)) {
            queueForSave(region);
        }
    }

    public void removeLog(Location location) {
        Region region = getRegionForLocation(location);

        if (region.removeLocation(location)) {
            queueForSave(region);
        }
    }

    public void queueForSave(Region region) {
        if (!saveQueue.contains(region)) {
            saveQueue.add(region);
        }
    }

    public void handleBreak(final Block block) {
        Material type = block.getType();
        if (isNatural(block.getLocation())) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FroobMechs.getPlugin(), new Runnable() {
                public void run() {
                    final Block below = block.getRelative(0, -1, 0);
                    if ((below.getType() == Material.DIRT || below.getType() == Material.GRASS || below.getType() == Material.PODZOL || below.getType() == Material.COARSE_DIRT) && block.getType() == Material.AIR) {
                        block.setType(getSaplingForWood(type));
                    }
                }
            }, 20);
        }

        removeLog(block.getLocation());
    }

    public void handlePlace(Block block) {
        addLog(block.getLocation());
    }

    public void task() {
        for (Region region : saveQueue) {
            region.save();
        }
        saveQueue.clear();
    }

}
