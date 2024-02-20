package com.skyblue.roomdatabase.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.skyblue.roomdatabase.model.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM POST ORDER BY ID")
    List<Post> loadAllPosts();

    @Insert
    void insertPost(Post post);

    @Update
    void updatePost(Post post);

    @Delete
    void deletePost(Post post);

    @Query("SELECT * FROM POST WHERE id = :id")
    Post loadPostById(int id);
}