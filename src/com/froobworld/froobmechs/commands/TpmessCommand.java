package com.froobworld.froobmechs.commands;

import com.froobworld.frooblib.command.PlayerCommandExecutor;
import com.froobworld.frooblib.utils.TeleportUtils;
import com.froobworld.froobmechs.data.Mess;
import com.froobworld.froobmechs.managers.MessManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TpmessCommand extends PlayerCommandExecutor {
    private MessManager messManager;
    private com.froobworld.froobbasics.managers.PlayerManager fbPlayermanager;

    public TpmessCommand(MessManager messManager, com.froobworld.froobbasics.managers.PlayerManager fbPlayermanager) {
        this.messManager = messManager;
        this.fbPlayermanager = fbPlayermanager;
    }


    @Override
    public boolean onPlayerCommandProcess(Player player, Command command, String cl, String[] args) {
        if (args.length == 0) {
            player.sendMessage("/" + cl + " <id>");
            return false;
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch(NumberFormatException ex) {
            player.sendMessage(ChatColor.RED + "The id must be an integer.");
            return false;
        }
        Mess mess = null;
        for (Mess m : messManager.getOpenMesses()) {
            if (m.getId() == id) {
                mess = m;
            }
        }
        if(mess == null) {
            player.sendMessage(ChatColor.RED + "There is no current mess with that id.");
            return false;
        }

        if(fbPlayermanager != null){
            fbPlayermanager.getPlayerdata(player).setBackLocation(player.getLocation(), false);
        }

        player.teleport(TeleportUtils.findSafeTeleportLocation(mess.getLocation()));
        player.sendMessage(ChatColor.YELLOW + "Teleported to mess #" + mess.getId() + ". Get scrubbing.");
        player.sendMessage(ChatColor.YELLOW + "Once the mess is taken care of, use '/closemess " + mess.getId() + "'.");
        return true;
    }

    @Override
    public String command() {
        return "tpmess";
    }

    @Override
    public String perm() {
        return "froobmechs.tpmess";
    }

    @Override
    public List<String> tabCompletions(Player player, Command command, String cl, String[] args) {
        ArrayList<String> completions = new ArrayList<String>();
        if (args.length == 1) {
            for (Mess mess : messManager.getOpenMesses()) {
                completions.add(mess.getId() + "");
            }
        }

        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<String>(completions.size()));
    }
}
