package com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.Engine;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by spraetz on 2/17/14.
 */
public class StoneWall extends Spell {

    public StoneWall(PlayerInteractEvent event, Engine plugin){
        super(event, plugin);
    }

    @Override
    public void spellEffects(PlayerEvent event) {

    }
}
