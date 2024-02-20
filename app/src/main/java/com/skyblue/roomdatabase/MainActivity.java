package com.skyblue.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.skyblue.roomdatabase.adaptor.PostAdapter;
import com.skyblue.roomdatabase.database.AppDatabase;
import com.skyblue.roomdatabase.database.AppExecutors;
import com.skyblue.roomdatabase.databinding.ActivityMainBinding;
import com.skyblue.roomdatabase.model.Post;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final Context context = this;
    private PostAdapter postAdapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, EditActivity.class));
            }
        });
        setupRecyclerView();
    }
    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this);
        binding.recyclerView.setAdapter(postAdapter);
        appDatabase = AppDatabase.getInstance(getApplicationContext());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Post> tasks = postAdapter.getTasks();
                        appDatabase.postDao().deletePost(tasks.get(position));
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(binding.recyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Post> persons = appDatabase.postDao().loadAllPosts();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postAdapter.setTasks(persons);
                    }
                });
            }
        });
    }
}