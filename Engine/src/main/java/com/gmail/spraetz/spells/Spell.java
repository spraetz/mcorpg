package com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.Engine;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

/**
 * Created by spraetz on 2/16/14.
 */
public abstract class Spell {

    Engine plugin;
    PlayerEvent event;
    Player player;

    public Spell(PlayerEvent event, Engine plugin){
        this.player = event.getPlayer();
        this.plugin = plugin;
        this.event = event;
    }

    public static String getName() throws Exception {
        throw new NotImplementedException();
    }

    public static ItemStack[] getReagents() throws Exception {
        throw new NotImplementedException();
    }

    public static boolean validate(){
        return false;
    }

    public boolean cast(){

        PlayerInteractEvent e = (PlayerInteractEvent)event;

        // Check if there are charges on the spellbook
        Integer charges = Spellbook.getCharges(event.getPlayer().getItemInHand());

        // Remove one charge
        if(charges == 0){
            player.sendMessage("Your spellbook is out of charges!");
            return false;
        }

        Spellbook.setCharges(event.getPlayer().getItemInHand(), charges - 1);

        // Cause spell effects
        spellEffects(event);

        return true;
    }

    public abstract void spellEffects(PlayerEvent event);

    public void addMetadata(Entity object, Player caster, Engine plugin){
        object.setMetadata("isSpell", new FixedMetadataValue(plugin, true));
        object.setMetadata("caster", new FixedMetadataValue(plugin, caster.getUniqueId()));
    }

    public static HashMap<String, Class> getSpells(){
        HashMap<String, Class> spellMap = new HashMap<String, Class>();

        spellMap.put(LightningBolt.getName(), LightningBolt.class);
        spellMap.put(FireBlast.getName(), FireBlast.class);
        spellMap.put(Explosion.getName(), Explosion.class);
        spellMap.put(Teleport.getName(), Teleport.class);

        return spellMap;
    }
}
