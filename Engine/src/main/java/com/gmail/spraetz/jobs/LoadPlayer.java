package com.gmail.spraetz.jobs;

import com.gmail.spraetz.models.User;
import com.gmail.spraetz.plugin.Engine;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by spraetz on 2/17/14.
 */
public class LoadPlayer extends BukkitRunnable{

    Engine plugin;
    Player player;

    public LoadPlayer(Engine plugin, Player p){
        this.plugin = plugin;
        this.player = p;
    }

    public void load(){
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this);
    }

    @Override
    public void run() {
        // Check to see if that player exists in the database.
        User user = User.getUser(player, plugin);

        if(user == null){
            user = User.createUser(player, plugin);
        }

        plugin.getServer().getScheduler().runTask(plugin, new SetPlayerMetadata(plugin, player, user));
    }

    public class SetPlayerMetadata extends BukkitRunnable{

        private final Engine plugin;
        private final Player player;
        private final User user;

        public SetPlayerMetadata(Engine plugin, Player player, User user){
            this.plugin = plugin;
            this.user = user;
            this.player = player;
        }

        public void run(){
            plugin.setUser(player, user);
            Player p = plugin.getServer().getPlayer(user.name.toLowerCase());
            if(p != null){
                p.sendMessage("Character loaded successfully!");
            }
        }

    }
}
