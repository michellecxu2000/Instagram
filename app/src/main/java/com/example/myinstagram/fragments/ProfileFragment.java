package com.example.myinstagram.fragments;

import android.util.Log;

import com.example.myinstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends TimelineFragment {
    @Override
    protected void populateTimeline() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setLimit(20); //set a limit of 20 posts
        query.include("user");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null){
                    for(int i = 0; i < objects.size(); i++){
                        posts.add(objects.get(i));
                        instagramAdapter.notifyItemInserted(posts.size() - 1);
                    }
                    swipeContainer.setRefreshing(false);
                }else{
                    //something went wrong
                    Log.e("TimelineActivity", "failure");
                }
            }
        });
    }
}
