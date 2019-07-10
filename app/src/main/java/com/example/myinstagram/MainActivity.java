package com.example.myinstagram;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;

    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //can make the next three lines a separate method to save time
            final Intent intent = new Intent (MainActivity.this, TimelineActivity.class);
            startActivity(intent);
            finish();
        } else {
            usernameInput = (EditText) findViewById(R.id.usernameInput);
            passwordInput = (EditText) findViewById(R.id.passwordInput);
            loginBtn = (Button) findViewById(R.id.loginBtn);

            signUpBtn = (Button) findViewById(R.id.SignUpBtn);

            //this is the background gradient for the login page
            ConstraintLayout constraintLayout = findViewById(R.id.layout);
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();


            loginBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();

                    login(username, password);
                }
            });

            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }


    } //end of onCreate

    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.d("LoginActivity", "Login successful");
                    final Intent intent = new Intent (MainActivity.this, TimelineActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                }
            }
        });
    }


}
