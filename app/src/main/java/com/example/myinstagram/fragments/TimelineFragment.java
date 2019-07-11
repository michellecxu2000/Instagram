package com.example.myinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinstagram.EndlessRecyclerViewScrollListener;
import com.example.myinstagram.InstagramAdapter;
import com.example.myinstagram.Post;
import com.example.myinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    RecyclerView rvPosts;
    InstagramAdapter instagramAdapter;
    ArrayList<Post> posts;
    protected SwipeRefreshLayout swipeContainer;
    protected EndlessRecyclerViewScrollListener scrollListener;
    public int whichFragment;
    long maxId = 0;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //swipe refresh
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                instagramAdapter.clear();
                posts.clear();
                populateTimeline();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //find the RecyclerView
        rvPosts = (RecyclerView) view.findViewById(R.id.rvPost);
        //init the arraylist (data source)
        posts = new ArrayList<>();
        //construct the adapter from this datasource

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //RecyclerView setup (layout manager, use adapter)

        //set the adapter

        setRecyclerView();

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);
        populateTimeline();
    }

    protected void setRecyclerView(){
        whichFragment = 0;
        instagramAdapter = new InstagramAdapter(posts, whichFragment);
        rvPosts.setAdapter(instagramAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void loadNextDataFromApi(int offset) {
        //maxId=tweets.get(tweets.size()-1).uid; //will get the id of the last tweet

        populateTimeline();
    }


    protected void populateTimeline(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setLimit(20); //set a limit of 20 posts
        query.addDescendingOrder("createdAt");
        query.include("user");
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
