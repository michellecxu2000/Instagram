package com.example.myinstagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.List;

public class InstagramAdapter extends RecyclerView.Adapter<InstagramAdapter.ViewHolder>{

    private List<Post> queries;

    //pass in the posts in the constructor
    public InstagramAdapter(List<Post> query){
        queries = query;
    }

    //for each row, inflate the layout and cache references to the ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
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

        ParseFile photo = post.getMedia();
        photo.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                Bitmap takenImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                viewHolder.ivPicture.setImageBitmap(takenImage);
            }
        });

    }

    //create ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivPicture;
        public TextView tvUser;
        public TextView tvCaption;

        public ViewHolder(View itemView){
            super(itemView);

            //perform findViewById
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvUser = itemView.findViewById(R.id.tvUserId);
            tvCaption = itemView.findViewById(R.id.tvCaption);
        }
    }

    public int getItemCount(){
        return queries.size();
    }
}
