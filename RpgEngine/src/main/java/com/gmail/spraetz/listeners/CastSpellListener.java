package com.gmail.spraetz.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.Material.BOOK;
import static org.bukkit.Material.FIREBALL;

/**
 * Created by spraetz on 2/16/14.
 */
public class CastSpellListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void castSpell(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if(p.getItemInHand().getType() == Material.BOOK && event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            Fireball fireball = p.getWorld().spawn(p.getEyeLocation(), Fireball.class);
            fireball.setIsIncendiary(false);
            p.sendMessage(fireball.getDirection().toString());
            fireball.setShooter(p);
        }
    }
}
