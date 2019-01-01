package com.froobworld.froobmechs.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Fish) || event.getSpawnReason() != SpawnReason.NATURAL || event.getLocation().getY() < 46) {
            return;
        }

        if (Math.random() >= 0.95) {
            event.setCancelled(true);
            event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.SQUID);
        }
    }

}
