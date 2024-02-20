package com.skyblue.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.skyblue.roomdatabase.constants.Constants;
import com.skyblue.roomdatabase.database.AppDatabase;
import com.skyblue.roomdatabase.database.AppExecutors;
import com.skyblue.roomdatabase.databinding.ActivityEditBinding;
import com.skyblue.roomdatabase.model.Post;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {
    private ActivityEditBinding binding;
    private final Context context = this;
    int mPostId;
    Intent intent;
    private AppDatabase mAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();

        if (intent != null && intent.hasExtra(Constants.UPDATE_POST_ID)){
            binding.button.setText("Update");

            mPostId = intent.getIntExtra(Constants.UPDATE_POST_ID, -1);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Post post = mAppDatabase.postDao().loadPostById(mPostId);
                    populateUI(post);
                }
            });
        }

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {
        final Post post = new Post(
                binding.editName.getText().toString(),
                binding.editEmail.getText().toString()
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_POST_ID)) {
                    mAppDatabase.postDao().insertPost(post);
                } else {
                    post.setId(mPostId);
                    mAppDatabase.postDao().updatePost(post);
                }
                finish();
            }
        });
    }

    private void populateUI(Post post) {
        if (post == null){
            return;
        }

        binding.editName.setText(post.getName());
        binding.editEmail.setText(post.getEmail());
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}