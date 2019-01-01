package com.froobworld.froobmechs.managers;

import com.froobworld.frooblib.data.Storage;
import com.froobworld.frooblib.data.TaskManager;
import com.froobworld.frooblib.utils.PageUtils;
import com.froobworld.frooblib.uuid.UUIDManager;
import com.froobworld.frooblib.uuid.UUIDManager.UUIDData;
import com.froobworld.froobmechs.FroobMechs;
import com.froobworld.froobmechs.data.Playerdata;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager extends TaskManager {
    private Storage storage;

    private ArrayList<Playerdata> playerdata;
    private ArrayList<Playerdata> saveQueue;

    public PlayerManager() {
        super(FroobMechs.getPlugin());
    }


    @Override
    public void ini() {
        storage = new Storage(FroobMechs.getPlugin().getDataFolder().getPath() + "/playerdata");
        playerdata = new ArrayList<Playerdata>();
        saveQueue = new ArrayList<Playerdata>();

        for (File file : storage.listFiles()) {
            playerdata.add(new Playerdata(file, this));
        }

        addTask(0, 600, new Runnable() {

            @Override
            public void run() {
                task();
            }
        });
    }

    public Playerdata getPlayerdata(Player player) {
        for (Playerdata data : playerdata) {
            if (data.getUUID().equals(player.getUniqueId())) {
                return data;
            }
        }
        Playerdata data = new Playerdata(player, storage.getFile(player.getUniqueId().toString() + ".yml"), this);
        playerdata.add(data);

        return data;
    }

    public Playerdata getPlayerdata(UUID uuid) {
        for (Playerdata data : playerdata) {
            if (data.getUUID().equals(uuid)) {
                return data;
            }
        }

        return null;
    }

    public void addToSaveQueue(Playerdata data) {
        if (!saveQueue.contains(data)) {
            saveQueue.add(data);
        }
    }

    public Playerdata commandSearchForPlayer(String name, CommandSender sender, UUIDManager uuidManager) {
        Playerdata data = null;

        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            data = getPlayerdata(player);
        } else {
            ArrayList<UUIDData> uuids = uuidManager.getUUIDData(name);
            if (uuids.size() > 1) {
                sender.sendMessage(ChatColor.RED + "There are multiple people who last played with that name:");
                sender.sendMessage(PageUtils.toString(uuids));
                sender.sendMessage(ChatColor.RED + "Please use their UUID in place of their name.");
                return null;
            }
            if (uuids.size() == 1) {
                data = getPlayerdata(uuids.get(0).getUUID());
            }
        }
        if (data == null) {
            UUID uuid = null;
            try {
                uuid = UUID.fromString(name);
            } catch (IllegalArgumentException ex) {
                sender.sendMessage(ChatColor.RED + "A player by that name could not be found.");
                return null;
            }
            if (uuid != null) {
                data = getPlayerdata(uuid);
            } else {
                sender.sendMessage(ChatColor.RED + "A player with that UUID could not be found.");
                return null;
            }
        }
        if (data == null) {
            sender.sendMessage(ChatColor.RED + "A player by that name could not be found.");
            return null;
        }

        return data;
    }

    public void task() {
        for (Playerdata data : saveQueue) {
            data.save();
        }

        saveQueue.clear();
    }


}
