package com.froobworld.froobmechs.commands;

import com.froobworld.frooblib.command.PlayerCommandExecutor;
import com.froobworld.froobmechs.managers.MessManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MessCommand extends PlayerCommandExecutor {
    private MessManager messManager;

    public MessCommand(MessManager messManager) {
        this.messManager = messManager;
    }

    @Override
    public boolean onPlayerCommandProcess(Player player, Command command, String cl, String[] args) {
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Please add a description, for example: '/mess beach', '/mess ugly towers'...");
            return false;
        }

        messManager.createMess(player, StringUtils.join(args, " "));
        player.sendMessage(ChatColor.YELLOW + "Thank you for reporting a mess, we will send someone to clean it up.");
        Bukkit.broadcast(player.getDisplayName() + ChatColor.YELLOW + " has a reported a messy area. Use '/listmesses' to see a list of current messes.", "froobmechs.listmesses");
        return true;
    }

    @Override
    public String command() {
        return "mess";
    }

    @Override
    public String perm() {
        return "froobmechs.mess";
    }

    @Override
    public List<String> tabCompletions(Player player, Command command, String cl, String[] args) {
        ArrayList<String> completions = new ArrayList<String>();

        return completions;
    }
}
