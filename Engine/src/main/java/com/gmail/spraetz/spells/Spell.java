package com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.Engine;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

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
    }

    public static String getName() throws Exception {
        throw new Exception("Not implemented error");
    }

    public abstract ItemStack[] getReagents();

    public static boolean validate(){
        return false;
    }

    public boolean cast(){
        // Check if there are charges on the spellbook

        // Remove one charge

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
