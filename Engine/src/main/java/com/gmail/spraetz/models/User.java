package com.gmail.spraetz.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by spraetz on 2/16/14.
 */

@Entity
public class User {

    @Id
    private ObjectId id;

    private String name;

    private String bukkitId;
}
