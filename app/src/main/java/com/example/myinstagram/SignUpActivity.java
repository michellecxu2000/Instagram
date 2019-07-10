package com.example.myinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        signUpBtn = (Button) findViewById(R.id.SignUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                final String email = emailInput.getText().toString();

                signUp(username, password, email);
            }
        });
    }

    private void signUp(String username, String password, String email){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d("SignUpActivity", "Sign up successful");
                    final Intent intent = new Intent (SignUpActivity.this, TimelineActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.e("SignUpActivity", "Sign up failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
