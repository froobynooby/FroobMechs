package com.froobworld.froobmechs.commands;

import com.froobworld.frooblib.command.CommandExecutor;
import com.froobworld.froobmechs.data.Mess;
import com.froobworld.froobmechs.managers.MessManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListmessesCommand extends CommandExecutor {
    private MessManager messManager;

    public ListmessesCommand(MessManager messManager) {
        this.messManager = messManager;
    }


    @Override
    public boolean onCommandProcess(CommandSender sender, Command command, String cl, String[] args) {
        ArrayList<Mess> messes = messManager.getOpenMesses();

        if (messes.size() == 0) {
            sender.sendMessage(ChatColor.YELLOW + "There are no open messses.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "There " + (messes.size() == 1 ? "is one mess.":("are " + messes.size() + " messes.")));
        for(Mess mess : messes) {
            sender.sendMessage(ChatColor.YELLOW + "Mess #" + mess.getId() + ChatColor.WHITE + ": " + mess.getDescription());
        }
        sender.sendMessage(ChatColor.YELLOW + "Use '/tpmess #' to teleport to the mess.");
        return true;
    }

    @Override
    public String command() {
        return "listmesses";
    }

    @Override
    public String perm() {
        return "froobmechs.listmesses";
    }

    @Override
    public List<String> tabCompletions(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<String>();
    }
}
