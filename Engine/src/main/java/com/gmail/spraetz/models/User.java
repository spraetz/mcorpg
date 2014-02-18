package com.gmail.spraetz.models;

import com.gmail.spraetz.plugin.Engine;
import org.bson.types.ObjectId;
import org.bukkit.entity.Player;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by spraetz on 2/16/14.
 */

@Entity
public class User {

    @Id
    private ObjectId id;

    public String name;

    public static User getUser(Player p, Engine plugin){
        return plugin.data.createQuery(User.class).field("name").equal(p.getName().toLowerCase()).get();
    }

    public static User createUser(Player p, Engine plugin){
        User user = new User();
        user.name = p.getName().toLowerCase();
        plugin.data.save(user);
        return user;
    }
}
