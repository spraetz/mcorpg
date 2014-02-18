package com.gmail.spraetz.commands;

import com.gmail.spraetz.plugin.Engine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by spraetz on 2/16/14.
 */
public class TestCommand implements CommandExecutor {

    private Engine plugin;

    public TestCommand(Engine plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        String commandString = cmd.getName().toLowerCase();

        if(commandString.equals("test")){
            Player p = (Player)sender;
            p.sendMessage(plugin.getUser(p).name);
            return true;
        }

        return false;
    }
}
