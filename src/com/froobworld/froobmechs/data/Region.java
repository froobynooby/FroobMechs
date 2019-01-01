package com.froobworld.froobmechs.data;

import com.froobworld.frooblib.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Region {
    private File file;
    private YamlConfiguration config;

    private ArrayList<String> locations;
    private int x, z;

    public Region(File file) {
        this.file = file;
        config = YamlConfiguration.loadConfiguration(file);
        load();
    }

    public Region(int x, int z, File file) {
        this.file = file;
        this.x = x;
        this.z = z;
        this.locations = new ArrayList<String>();

        config = YamlConfiguration.loadConfiguration(file);
        config.set("location.x", x);
        config.set("location.z", z);
        config.set("locations", locations);

        save();
    }


    public void load() {
        x = config.getInt("location.x");
        z = config.getInt("location.z");
        locations = (ArrayList<String>) config.getStringList("locations");
    }

    public void save() {
        config.set("locations", locations);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addLocation(Location location) {
        String loc = LocationUtils.serialiseLocation(location);
        if (!locations.contains(loc)) {
            locations.add(loc);
            return true;
        }
        return false;
    }

    public boolean removeLocation(Location location) {
        String loc = LocationUtils.serialiseLocation(location);
        if (locations.contains(loc)) {
            locations.remove(loc);
            return true;
        }
        return false;
    }

    public boolean isLocation(Location location) {

        return locations.contains(LocationUtils.serialiseLocation(location));
    }

    public int x() {

        return x;
    }

    public int z() {

        return z;
    }

}
