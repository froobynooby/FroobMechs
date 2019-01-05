package com.froobworld.froobmechs;

import com.froobworld.froobbasics.FroobBasics;
import com.froobworld.frooblib.FroobPlugin;
import com.froobworld.frooblib.uuid.UUIDManager;
import com.froobworld.froobmechs.commands.*;
import com.froobworld.froobmechs.listeners.EntityListener;
import com.froobworld.froobmechs.listeners.PlayerListener;
import com.froobworld.froobmechs.managers.EntityManager;
import com.froobworld.froobmechs.managers.MessManager;
import com.froobworld.froobmechs.managers.PlayerManager;
import com.froobworld.froobmechs.managers.TreeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class FroobMechs extends FroobPlugin {
    public static final String NO_PERM = ChatColor.RED + "You don't have permission to use this command.";
    public static final String NO_CONSOLE = "This command is unusable from the console.";

    private UUIDManager uuidManager;
    private PlayerManager playerManager;
    private TreeManager treeManager;
    private MessManager messManager;
    private com.froobworld.froobbasics.managers.PlayerManager fbPlayermanager;

    public static Plugin getPlugin() {

        return getPlugin(FroobMechs.class);
    }

    public void onEnable() {
        initiateManagers();
        registerCommands();
        registerEvents();
    }

    public void onDisable() {
        playerManager.task();
        treeManager.task();
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(playerManager, treeManager, messManager), this);
        pm.registerEvents(new EntityListener(), this);
    }

    public void registerCommands() {
        registerCommand(new OrestatsCommand(playerManager, uuidManager));
        registerCommand(new PvpCommand(playerManager));
        registerCommand(new MessCommand(messManager));
        registerCommand(new ListmessesCommand(messManager));
        registerCommand(new TpmessCommand(messManager, fbPlayermanager));
        registerCommand(new ClosemessCommand(messManager));
    }

    public void initiateManagers() {
        uuidManager = uuidManager();
        playerManager = new PlayerManager();
        treeManager = new TreeManager();
        messManager = new MessManager();

        FroobBasics froobBasics = (FroobBasics) getServer().getPluginManager().getPlugin("FroobBasics");
        if (froobBasics != null) {
            fbPlayermanager = froobBasics.getPlayerManager();
        } else {
            fbPlayermanager = null;
        }

        registerManager(playerManager);
        registerManager(treeManager);
        registerManager(new EntityManager());
        registerManager(messManager);
    }

}
