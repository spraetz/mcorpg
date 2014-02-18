package com.gmail.spraetz.plugin;

import com.gmail.spraetz.commands.ChargeSpellbook;
import com.gmail.spraetz.commands.TestCommand;
import com.gmail.spraetz.jobs.LoadPlayer;
import com.gmail.spraetz.listeners.CastSpellListener;
import com.gmail.spraetz.listeners.PlayerJoinListener;
import com.gmail.spraetz.models.User;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.MapperOptions;

import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by spraetz on 2/16/14.
 */
public class Engine extends JavaPlugin{

    public FileConfiguration config;
    public Datastore data;

    public HashMap<String, User> userMap = new HashMap<String, User>();

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

        //Load entities into hashes
        loadPlayers();
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
    }

    public void registerCommands(){
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("charge").setExecutor(new ChargeSpellbook(this));
    }

    public void registerEventListeners(){
        new CastSpellListener(this);
        new PlayerJoinListener(this);
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
            //Create MongoDB client
            MongoClient mongo = new MongoClient(new MongoClientURI(mongoUri));
            mongo.setWriteConcern(WriteConcern.SAFE);

            //Create Morphia
            Morphia morphia = new Morphia();
            morphia.map(User.class);

            //Create Datastore
            MapperOptions opts = new MapperOptions();
            opts.objectFactory = new CustomCreator(this.getClassLoader());
            Mapper mapper = new Mapper(opts);
            this.data = new DatastoreImpl(mapper, mongo, "engine");

            return true;
        }
        catch(UnknownHostException e){
            getLogger().severe("Could not connect to MongoDB!");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
    }

    public void loadPlayers(){
        for (Player p : getServer().getOnlinePlayers()){
            LoadPlayer lp = new LoadPlayer(this, p);
            lp.load();
        }
    }

    // Utils
    public User getUser(Player p){
        return userMap.get(p.getName().toLowerCase());
    }

    public void setUser(Player p, User user){
        userMap.put(p.getName().toLowerCase(), user);
    }

    public void removeUser(Player p){
        userMap.remove(p.getName().toLowerCase());
    }

}
