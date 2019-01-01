package com.froobworld.froobmechs.commands;

import com.froobworld.frooblib.command.CommandExecutor;
import com.froobworld.frooblib.utils.CommandUtils;
import com.froobworld.frooblib.uuid.UUIDManager;
import com.froobworld.froobmechs.data.Playerdata;
import com.froobworld.froobmechs.managers.PlayerManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class OrestatsCommand extends CommandExecutor {
    private PlayerManager playerManager;
    private UUIDManager uuidManager;

    public OrestatsCommand(PlayerManager playerManager, UUIDManager uuidManager) {
        this.playerManager = playerManager;
        this.uuidManager = uuidManager;
    }

    @Override
    public boolean onCommandProcess(CommandSender sender, Command command, String cl, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("/" + cl + " player>");
            return false;
        }
        Playerdata data = playerManager.commandSearchForPlayer(args[0], sender, uuidManager);
        if (data == null) {
            return false;
        }

        sender.sendMessage(ChatColor.YELLOW + "Ore stats for " +
                uuidManager.getUUIDData(data.getUUID()).getLastName() + ":");
        sender.sendMessage(ChatColor.GRAY + "Stone" + ChatColor.WHITE + ": " + data.getStone() +
                "(" + data.pct(data.getStone()) + "%)");
        sender.sendMessage(ChatColor.BLACK + "Coal" + ChatColor.WHITE + ": " + data.getCoal() +
                "(" + data.pct(data.getCoal()) + "%)");
        sender.sendMessage(ChatColor.DARK_GRAY + "Iron" + ChatColor.WHITE + ": " + data.getIron() +
                "(" + data.pct(data.getIron()) + "%)");
        sender.sendMessage(ChatColor.GOLD + "Gold" + ChatColor.WHITE + ": " + data.getGold() +
                "(" + data.pct(data.getGold()) + "%)");
        sender.sendMessage(ChatColor.BLUE + "Lapis" + ChatColor.WHITE + ": " + data.getLapis() +
                "(" + data.pct(data.getLapis()) + "%)");
        sender.sendMessage(ChatColor.RED + "Redstone" + ChatColor.WHITE + ": " + data.getRedstone() +
                "(" + data.pct(data.getRedstone()) + "%)");
        sender.sendMessage(ChatColor.GREEN + "Emerald" + ChatColor.WHITE + ": " + data.getEmerald() +
                "(" + data.pct(data.getEmerald()) + "%)");
        sender.sendMessage(ChatColor.AQUA + "Diamond" + ChatColor.WHITE + ": " + data.getDiamond() +
                "(" + data.pct(data.getDiamond()) + "%)");

        return true;
    }

    @Override
    public String command() {

        return "orestats";
    }

    @Override
    public String perm() {

        return "froobmechs.orestats";
    }

    @Override
    public List<String> tabCompletions(CommandSender sender, Command command, String cl, String[] args) {
        ArrayList<String> completions = new ArrayList<String>();
        if (args.length == 1) {
            return CommandUtils.tabCompletePlayerList(args[0], true, true, uuidManager);
        }

        return completions;
    }

}
