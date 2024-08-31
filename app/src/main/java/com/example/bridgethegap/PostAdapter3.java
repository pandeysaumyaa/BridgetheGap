package com.example.bridgethegap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter3 extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;

    public PostAdapter3(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post3, parent, false);
        return new PostAdapter.PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.captionTextView.setText(post.getCaption());

        // Load image using Picasso
        Picasso.get()
                .load(post.getImageUrl())
                .placeholder(R.drawable.profile) // Placeholder image while loading
                .error(R.drawable.profile) // Error image if loading fails
                .into(holder.postImage);

        // You can bind other post data here if needed
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public ImageView postImage;
        TextView captionTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            captionTextView = itemView.findViewById(R.id.captionTextView);
            postImage = itemView.findViewById(R.id.postImage); // Initialize ImageView here
        }
    }



}
