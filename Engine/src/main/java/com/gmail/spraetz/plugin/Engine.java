package com.gmail.spraetz.plugin;

import com.gmail.spraetz.commands.TestCommand;
import com.gmail.spraetz.listeners.CastSpellListener;
import com.gmail.spraetz.models.User;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.DefaultCreator;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.MapperOptions;

import java.io.File;
import java.net.UnknownHostException;

/**
 * Created by spraetz on 2/16/14.
 */
public class Engine extends JavaPlugin{

    FileConfiguration config;
    Datastore data;

    @Override
    public void onEnable() {
        getLogger().info("onEnable has been invoked!");

        //Set up the config
        setupConfig();

        // Fire up the database.
        boolean success = startDatabase();

        if(!success){
            return;
        }

        //Register commands
        registerCommands();

        //Register Event Listeners
        registerEventListeners();
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
    }

    public void registerCommands(){
        getCommand("test").setExecutor(new TestCommand(this));
    }

    public void registerEventListeners(){
        new CastSpellListener(this);
    }

    public void setupConfig(){
        //Look for a config.yml file in the plugins folder.
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }


    public boolean startDatabase(){

        // Get the MongoURI from config.yml
        String mongoUri = getConfig().getString("MongoURI");

        // Make sure we have a MongoURI in config.yml
        if(mongoUri == null){
            getLogger().severe("No MongoURI specified in config.yml.  Please add one.");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        try{
            MongoClient mongo = new MongoClient(new MongoClientURI(mongoUri));
            mongo.setWriteConcern(WriteConcern.SAFE);
            Morphia morphia = new Morphia();
            morphia.map(User.class);

            data = morphia.createDatastore(mongo, "engine");
            return true;
        }
        catch(UnknownHostException e){
            getLogger().severe("Could not connect to MongoDB!");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
    }

}
