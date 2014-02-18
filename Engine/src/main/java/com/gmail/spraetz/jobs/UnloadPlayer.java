package com.gmail.spraetz.jobs;

import com.gmail.spraetz.models.User;
import com.gmail.spraetz.plugin.Engine;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by spraetz on 2/17/14.
 */
public class UnloadPlayer extends BukkitRunnable{

    Engine plugin;
    Player player;

    public UnloadPlayer(Engine plugin, Player p){
        this.plugin = plugin;
        this.player = p;
    }

    public void unload(){
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this);
    }

    @Override
    public void run() {

        //Save the player's metadata to the db.
        plugin.data.save(plugin.getUser(player));

        //Remove player from hashmap.
        plugin.getServer().getScheduler().runTask(plugin, new RemovePlayerMetadata(plugin, player));
    }

    public class RemovePlayerMetadata extends BukkitRunnable{

        private final Engine plugin;
        private final Player player;

        public RemovePlayerMetadata(Engine plugin, Player player){
            this.plugin = plugin;
            this.player = player;
        }

        public void run(){
            plugin.removeUser(player);
            plugin.getLogger().info(player.getName() + " removed from Map.");
        }

    }
}
