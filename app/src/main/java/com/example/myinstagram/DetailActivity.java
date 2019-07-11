package com.example.myinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView ivPost;
    TextView tvUser;
    TextView tvCaption;
    TextView tvTimePosted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivPost = (ImageView) findViewById(R.id.ivPost);
        tvUser = (TextView) findViewById(R.id.tvUser);
        tvCaption = (TextView) findViewById(R.id.tvCaption);
        tvTimePosted = (TextView) findViewById(R.id.tvTimePosted);

        Post post= (Post) getIntent().getSerializableExtra("post");
        tvUser.setText(post.getUser().getUsername().toString());
        tvCaption.setText(post.getCaption());
        tvTimePosted.setText(post.getCreatedAt().toString());
        String url = post.getMedia().getUrl();
        Glide.with(DetailActivity.this).load(url).into(ivPost);
    }


}
