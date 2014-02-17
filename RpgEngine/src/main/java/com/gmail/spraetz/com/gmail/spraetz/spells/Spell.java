package com.gmail.spraetz.com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.RpgEngine;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by spraetz on 2/16/14.
 */
public abstract class Spell {

    RpgEngine plugin;
    Player player;

    public Spell(Player p, RpgEngine plugin){
        this.player = p;
        this.plugin = plugin;
    }

    public static boolean validate(){
        return false;
    }

    public abstract boolean cast(Event event);

    public void addMetadata(Entity object, Player caster, RpgEngine plugin){
        object.setMetadata("isSpell", new FixedMetadataValue(plugin, true));
        object.setMetadata("caster", new FixedMetadataValue(plugin, caster.getUniqueId()));
    }
}
