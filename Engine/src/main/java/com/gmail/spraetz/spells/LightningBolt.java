package com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.Engine;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by spraetz on 2/16/14.
 */
public class LightningBolt extends Spell {

    public LightningBolt(PlayerInteractEvent event, Engine plugin){
        super(event, plugin);
    }

    public static String getName(){
        return "lightning_bolt";
    }

    public static ItemStack[] getReagents() {
        return new ItemStack[]{
            new ItemStack(Material.INK_SACK, 1, (byte)4)
        };
    }

    @Override
    public void spellEffects(PlayerEvent event) {
        player.getWorld().strikeLightning(player.getTargetBlock(null, 20).getLocation());
    }
}
