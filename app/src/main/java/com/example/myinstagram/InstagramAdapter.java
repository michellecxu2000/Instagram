package com.example.myinstagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.io.Serializable;
import java.util.List;

public class InstagramAdapter extends RecyclerView.Adapter<InstagramAdapter.ViewHolder>{

    private List<Post> queries;
    Context context;

    //pass in the posts in the constructor
    public InstagramAdapter(List<Post> query){
        queries = query;
    }

    //for each row, inflate the layout and cache references to the ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.itempost, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //get the data according to position
        Post post = queries.get(i);

        //populate the views according to this data
        viewHolder.tvUser.setText(post.getUser().getUsername().toString());
        viewHolder.tvCaption.setText(post.getCaption());
        viewHolder.tvTimePosted.setText(post.getCreatedAt().toString());

        ParseFile photo = post.getMedia();
        String url= photo.getUrl();

        Glide.with(context).load(url).into(viewHolder.ivPicture);


    }

    //create ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivPicture;
        public TextView tvUser;
        public TextView tvCaption;
        public TextView tvTimePosted;

        public ViewHolder(View itemView){
            super(itemView);

            //perform findViewById
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvUser = itemView.findViewById(R.id.tvUserId);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvTimePosted = itemView.findViewById(R.id.tvTimePosted);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            //check if you can pass the image file as an intent, might be too big, but can wrap others in a parcel bc you can get the position
            int position = getAdapterPosition(); //gets item position

            if(position != RecyclerView.NO_POSITION){
                Post post = queries.get(position);

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("post", (Serializable) post);
                //intent.putExtra("objectId", post.getObjectId());
                context.startActivity(intent);
            }
        }
    }

    public int getItemCount(){
        return queries.size();
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        queries.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        queries.addAll(list);
        notifyDataSetChanged();
    }
}
