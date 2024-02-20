package com.skyblue.roomdatabase.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skyblue.roomdatabase.R;
import com.skyblue.roomdatabase.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(postList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (postList == null){
            return 0;
        }
        return postList.size();
    }

    public List<Post> getTasks() {
        return postList;
    }

    public void setTasks(List<Post> personList) {
        postList = personList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.post_name);
        }
    }
}

