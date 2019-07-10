package com.example.myinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {
    RecyclerView rvPosts;
    InstagramAdapter instagramAdapter;
    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //find the RecyclerView
        rvPosts = (RecyclerView) findViewById(R.id.rvPost);
        //init the arraylist (data source)
        posts = new ArrayList<>();
        //construct the adapter from this datasource
        instagramAdapter = new InstagramAdapter(posts);
        //RecyclerView setup (layout manager, use adapter)
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        //set the adapter
        rvPosts.setAdapter(instagramAdapter);
        populateTimeline();
    }

    private void populateTimeline(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setLimit(20); //set a limit of 20 posts
        query.include("user");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null){
                    for(int i = 0; i < objects.size(); i++){
                        posts.add(objects.get(i));
                        instagramAdapter.notifyItemInserted(posts.size() - 1);
                    }
                }else{
                    //something went wrong
                    Log.e("TimelineActivity", "failure");
                }
            }
        });
    }
}
