package com.froobworld.froobmechs.listeners;

import com.froobworld.froobmechs.data.Playerdata;
import com.froobworld.froobmechs.managers.MessManager;
import com.froobworld.froobmechs.managers.PlayerManager;
import com.froobworld.froobmechs.managers.TreeManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private PlayerManager playerManager;
    private TreeManager treeManager;
    private MessManager messManager;

    public PlayerListener(PlayerManager playerManager, TreeManager treeManager, MessManager messManager) {
        this.playerManager = playerManager;
        this.treeManager = treeManager;
        this.messManager = messManager;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission("froobmechs.listmesses")) {
            if (messManager.getOpenMesses().size() > 0) {
               event.getPlayer().sendMessage(ChatColor.YELLOW + "There are reported messy areas. Use '/listmesses' to view a list.");
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e) {
        if (TreeManager.isLog(e.getBlock())) {
            treeManager.handleBreak(e.getBlock());
            return;
        }

        if (e.getBlock().getType() == Material.STONE) {
            playerManager.getPlayerdata(e.getPlayer()).incrementStone();
            return;
        }
        if (e.getBlock().getType() == Material.DIAMOND_ORE) {
            if (e.getPlayer().getInventory().getItemInMainHand()
                    .getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                return;
            }
            playerManager.getPlayerdata(e.getPlayer()).incrementDiamond();
            return;
        }
        if (e.getBlock().getType() == Material.IRON_ORE) {
            playerManager.getPlayerdata(e.getPlayer()).incrementIron();
            return;
        }
        if (e.getBlock().getType() == Material.COAL_ORE) {
            if (e.getPlayer().getInventory().getItemInMainHand()
                    .getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                return;
            }
            playerManager.getPlayerdata(e.getPlayer()).incrementCoal();
            return;
        }
        if (e.getBlock().getType() == Material.GOLD_ORE) {
            playerManager.getPlayerdata(e.getPlayer()).incrementGold();
            return;
        }
        if (e.getBlock().getType() == Material.EMERALD_ORE) {
            if (e.getPlayer().getInventory().getItemInMainHand()
                    .getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                return;
            }
            playerManager.getPlayerdata(e.getPlayer()).incrementEmerald();
            return;
        }
        if (e.getBlock().getType() == Material.REDSTONE_ORE) {
            if (e.getPlayer().getInventory().getItemInMainHand()
                    .getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                return;
            }
            playerManager.getPlayerdata(e.getPlayer()).incrementRedstone();
            return;
        }
        if (e.getBlock().getType() == Material.LAPIS_ORE) {
            if (e.getPlayer().getInventory().getItemInMainHand()
                    .getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                return;
            }
            playerManager.getPlayerdata(e.getPlayer()).incrementLapis();
            return;
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (TreeManager.isLog(e.getBlock())) {
            treeManager.handlePlace(e.getBlock());
            return;
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity target = e.getEntity();
        if (damager instanceof Projectile) {
            if (((Projectile) damager).getShooter() instanceof Entity) {
                damager = (Entity) ((Projectile) damager).getShooter();
            }
        }

        if (damager instanceof Player && target instanceof Player && damager != target) {
            Player playerDamager = (Player) damager;
            Player playerTarget = (Player) target;

            Playerdata dataDamager = playerManager.getPlayerdata(playerDamager);
            Playerdata dataTarget = playerManager.getPlayerdata(playerTarget);

            if (!dataDamager.isPvp()) {
                e.setCancelled(true);
                playerDamager.sendMessage(ChatColor.RED + "You don't have PvP enabled.");
                return;
            }
            if (!dataTarget.isPvp()) {
                e.setCancelled(true);
                playerDamager.sendMessage(ChatColor.RED + "That player doesn't have PvP enabled.");
                return;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (!(entity instanceof LivingEntity) || entity instanceof Tameable || !(entity instanceof Animals || entity instanceof Monster || entity instanceof Villager)) {
            return;
        }
        LivingEntity lEntity = (LivingEntity) entity;

        if (!lEntity.hasAI()) {
            lEntity.setAI(true);
        }
    }

}
