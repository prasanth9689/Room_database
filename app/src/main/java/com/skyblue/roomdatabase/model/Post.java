package com.skyblue.roomdatabase.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "post")
public class Post {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String email;

    @Ignore
    public Post(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Post(int id, String name, String email){
        this.name = name;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
