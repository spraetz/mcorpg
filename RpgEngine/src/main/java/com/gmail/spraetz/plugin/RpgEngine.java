package com.gmail.spraetz.plugin;

import com.gmail.spraetz.commands.TestCommand;
import com.gmail.spraetz.listeners.CastSpellListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by spraetz on 2/16/14.
 */
public class RpgEngine extends JavaPlugin{

    @Override
    public void onEnable() {
        getLogger().info("onEnable has been invoked!");

        //Register commands
        registerCommands();

        //Register Event Listeners
        registerEventListeners();
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invokes!");
    }

    public void registerCommands(){
        getCommand("test").setExecutor(new TestCommand(this));
    }

    public void registerEventListeners(){
        getServer().getPluginManager().registerEvents(new CastSpellListener(), this);
    }
}
