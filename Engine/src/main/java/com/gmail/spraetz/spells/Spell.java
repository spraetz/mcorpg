package com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.Engine;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        throw new Exception("Not implemented error");
    }

    public abstract ItemStack[] getReagents();

    public static boolean validate(){
        return false;
    }

    public boolean cast(){

        PlayerInteractEvent e = (PlayerInteractEvent)event;

        System.out.println(e.getPlayer().getName());
        System.out.println(e.getPlayer().getItemInHand().getType());

        // Check if there are charges on the spellbook
        Integer charges = getCharges(event.getPlayer().getItemInHand());

        // Remove one charge
        if(charges == 0){
            player.sendMessage("Your spellbook is out of charges!");
            return false;
        }

        setCharges(event.getPlayer().getItemInHand(), charges-1);

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

    public static String chargeString(Integer numberOfCharges){
        return "charges: " + numberOfCharges;
    }

    public static Integer getCharges(ItemStack spellbook){
        ArrayList<String> loreList = (ArrayList<String>)spellbook.getItemMeta().getLore();
        return Integer.parseInt(loreList.get(0).split(" ")[1]);
    }

    public static void setCharges(ItemStack spellbook, Integer numberOfCharges){
        ItemMeta itemMeta = spellbook.getItemMeta();
        ArrayList<String> loreList = (ArrayList<String>)itemMeta.getLore();
        loreList.set(0, chargeString(numberOfCharges));
        itemMeta.setLore(loreList);
        spellbook.setItemMeta(itemMeta);
    }
}
