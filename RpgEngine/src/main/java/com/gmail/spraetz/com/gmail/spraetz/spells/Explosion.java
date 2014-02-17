package com.gmail.spraetz.com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.RpgEngine;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by spraetz on 2/16/14.
 */
public class Explosion extends Spell{

    public Explosion(Player p, RpgEngine plugin) {
        super(p, plugin);
    }

    @Override
    public boolean cast(Event e){

        //Spawn a new fireball at the next tick at the player's current location.
        Fireball explosion = player.getWorld().spawn(player.getEyeLocation(), Fireball.class);

        //Explosions have no fire.
        explosion.setIsIncendiary(false);

        //Explosions have a radius (yield) of 3.
        explosion.setYield(3);

        //Set the shooter to be the current player.
        explosion.setShooter(player);

        //Add metadata to the fireball so we know what it is and where it is going.
        addMetadata(explosion, player, plugin);

        return true;
    }

    public static boolean validate(PlayerInteractEvent event){
        return (event.getPlayer().getItemInHand().getType() == Material.BOOK) &&
                event.getAction().equals(Action.RIGHT_CLICK_AIR);
    }
}
