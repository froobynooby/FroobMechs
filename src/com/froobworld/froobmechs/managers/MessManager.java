package com.froobworld.froobmechs.managers;

import com.froobworld.frooblib.data.Manager;
import com.froobworld.frooblib.data.Storage;
import com.froobworld.froobmechs.FroobMechs;
import com.froobworld.froobmechs.data.Mess;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MessManager extends Manager {
    private Storage storage;
    private ArrayList<Mess> messes;

    public MessManager() {
        storage = new Storage(FroobMechs.getPlugin().getDataFolder() + "/messes");
    }

    @Override
    public void ini() {
        messes = new ArrayList<Mess>();
        for(File file : storage.listFiles()) {
            messes.add(new Mess(file));
        }
    }

    public ArrayList<Mess> getOpenMesses() {

        return (ArrayList<Mess>)messes.stream().filter( m -> m.isOpen() ).collect(Collectors.toList());
    }

    public void createMess(Player player, String description) {
        int nextId = 0;
        for(Mess mess : messes) {
            if(mess.getId() >= nextId) {
                nextId = mess.getId()+1;
            }
        }

        messes.add(new Mess(storage.getFile(nextId + ".yml"), nextId, player.getLocation(), player.getUniqueId(), description));
    }
}
