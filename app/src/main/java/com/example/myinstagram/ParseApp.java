package com.example.myinstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("stanford-cardinal")
                .clientKey("i-like-noodles-and-boba")
                .server("http://michellecxu2000-fbu-instagram.herokuapp.com/parse")
                .build();


        Parse.initialize(configuration);

    }
}
