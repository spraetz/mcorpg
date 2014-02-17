package com.gmail.spraetz.com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.RpgEngine;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by spraetz on 2/16/14.
 */
public class LightningBolt extends Spell {

    public LightningBolt(Player p, RpgEngine plugin){
        super(p, plugin);
    }

    @Override
    public boolean cast(Event event) {
        player.getWorld().strikeLightning(player.getTargetBlock(null, 20).getLocation());
        return true;
    }

    public static boolean validate(PlayerInteractEvent event){
        return (event.getPlayer().getItemInHand().getType() == Material.YELLOW_FLOWER) &&
                event.getAction().equals(Action.RIGHT_CLICK_AIR);
    }
}
