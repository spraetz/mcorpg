package com.gmail.spraetz.commands;

import com.gmail.spraetz.plugin.RpgEngine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by spraetz on 2/16/14.
 */
public class TestCommand implements CommandExecutor {

    private RpgEngine plugin;

    public TestCommand(RpgEngine plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {


        String commandString = cmd.getName().toLowerCase();

        if(commandString.equals("test")){
            Player p = (Player)sender;
            p.sendMessage("Test!");
            return true;
        }

        return false;
    }
}
