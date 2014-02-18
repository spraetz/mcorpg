package com.gmail.spraetz.listeners;

import com.gmail.spraetz.jobs.LoadPlayer;
import com.gmail.spraetz.plugin.Engine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by spraetz on 2/17/14.
 */
public class PlayerJoinListener implements Listener {

    Engine plugin;

    public PlayerJoinListener(Engine plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.getPlayer().sendMessage("Loading your character...");
        LoadPlayer loadPlayer = new LoadPlayer(this.plugin, event.getPlayer());
        loadPlayer.load();
    }
}
