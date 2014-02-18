package com.gmail.spraetz.commands;

import com.gmail.spraetz.plugin.Engine;
import com.gmail.spraetz.spells.Spell;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by spraetz on 2/17/14.
 */
public class ChargeSpellbook implements CommandExecutor {

    private final Engine plugin;

    public ChargeSpellbook(Engine plugin){
        this.plugin = plugin;
    }

    /*
        Command: chargeSpellbook
        Requirements: Must be holding a book in hand.  If that book is already charged,
            you must be adding the same type of spell.
        args:
            spellName - String, name of spell
            charges - Integer, optional number of charges to add to the spellbook.
                Default will add as many as it can up to 64.
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player)commandSender;

        ItemStack book = p.getItemInHand();

        // Check if they have a book in hand
        if(book.getType() != Material.BOOK){
            p.sendMessage("Must be holding a book to charge it!");
            return true;
        }

        // Make sure they have a spell name in the args.
        if(strings.length < 1){
            p.sendMessage("Not enough arguments.");
            return false;
        }

        //Get the spell they'd like to charge.
        String spellName = strings[0];

        //Check if there's a spell with that spell name.
        Class spellClass = Spell.getSpells().get(spellName);

        if(spellClass == null){
            p.sendMessage("There is no spell with that name.  /help spells");
            return true;
        }

        //TODO: Make sure if the book is already charged that we're adding the same spell on top.

        //Add metadata to the book
        ItemMeta itemMeta = book.getItemMeta();
        itemMeta.setDisplayName(spellName);
        itemMeta.setLore(new ArrayList<String>(){{
            add("charges: 64");
        }});

        book.setItemMeta(itemMeta);

        return true;
    }
}
