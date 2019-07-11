package com.example.myinstagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName("Post")
public class Post extends ParseObject implements Serializable {

    public ParseFile getMedia() {
        return getParseFile("Image");
    }

    public void setMedia(ParseFile parseFile) {
        put("Image", parseFile);
    }

    public String getCaption(){
        return getString("description");
    }

    public void setCaption(String caption){
        put("description", caption);

    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public void setUser(ParseUser user){
        put("user", user);
    }

//    public Date getCreatedAt(){
//        return getDate("createdAt");
//    }
}
