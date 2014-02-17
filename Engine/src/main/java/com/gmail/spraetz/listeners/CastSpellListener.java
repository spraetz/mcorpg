package com.gmail.spraetz.listeners;

import com.gmail.spraetz.com.gmail.spraetz.spells.Explosion;
import com.gmail.spraetz.com.gmail.spraetz.spells.FireBlast;
import com.gmail.spraetz.com.gmail.spraetz.spells.LightningBolt;
import com.gmail.spraetz.com.gmail.spraetz.spells.Teleport;
import com.gmail.spraetz.plugin.Engine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by spraetz on 2/16/14.
 */
public class CastSpellListener implements Listener {

    Engine plugin;

    public CastSpellListener(Engine plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void castSpell(PlayerInteractEvent event) {

        // Cast an explosion spell
        if(Explosion.validate(event)){
            Explosion explosion = new Explosion(event.getPlayer(), plugin);
            explosion.cast(event);
        }
        else if(FireBlast.validate(event)){
            FireBlast fireblast = new FireBlast(event.getPlayer(), plugin);
            fireblast.cast(event);
        }
        else if(LightningBolt.validate(event)){
            LightningBolt lb = new LightningBolt(event.getPlayer(), plugin);
            lb.cast(event);
        }
        else if(Teleport.validate(event)){
            Teleport tp = new Teleport(event.getPlayer(), plugin);
            tp.cast(event);
        }
    }
}
