package com.froobworld.froobmechs.commands;

import com.froobworld.frooblib.command.PlayerCommandExecutor;
import com.froobworld.froobmechs.data.Playerdata;
import com.froobworld.froobmechs.managers.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PvpCommand extends PlayerCommandExecutor {

    private PlayerManager playerManager;

    public PvpCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onPlayerCommandProcess(Player player, Command command, String cl, String[] args) {
        Playerdata data = playerManager.getPlayerdata(player);

        data.togglePvp();
        player.sendMessage(ChatColor.YELLOW + "PvP toggled. " +
                (data.isPvp() ? "You can now fight other players." : "You can no longer fight other players."));

        return true;
    }

    @Override
    public String command() {

        return "pvp";
    }

    @Override
    public String perm() {

        return "froobmechs.pvp";
    }

    @Override
    public List<String> tabCompletions(Player player, Command command, String cl, String[] args) {
        ArrayList<String> completions = new ArrayList<String>();

        return completions;
    }

}
