package com.froobworld.froobmechs.data;

import com.froobworld.frooblib.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Mess {
    private File file;
    private int id;
    private Location location;
    private UUID reporter;
    private UUID responder;
    private boolean open;
    private String description;

    public Mess(File file) {
        this.file = file;
        load();
    }

    public Mess(File file, int id, Location location, UUID reporter, String description) {
        this.file = file;
        this.id = id;
        this.location = location;
        this.reporter = reporter;
        this.open = true;
        this.description = description;
        save();
    }


    private void save() {
        YamlConfiguration config = new YamlConfiguration();

        config.set("id", id);
        config.set("location", LocationUtils.serialiseLocation(location));
        config.set("reporter", reporter.toString());
        config.set("open", open);
        config.set("description", description);
        if(!open) {
            config.set("responder", responder.toString());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        id = config.getInt("id");
        location = LocationUtils.deserialiseLocation(config.getString("location"));
        reporter = UUID.fromString(config.getString("reporter"));
        open = config.getBoolean("open");
        description = config.getString("description");

        if(!open){
            responder = UUID.fromString(config.getString("responder"));
        }
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public UUID getReporter() {
        return reporter;
    }

    public boolean isOpen() {
        return open;
    }

    public String getDescription() {
        return description;
    }

    public void close(UUID responder) {
        open = false;
        this.responder = responder;
        save();
    }
}
