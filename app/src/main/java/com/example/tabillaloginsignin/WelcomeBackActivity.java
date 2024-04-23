package com.example.tabillaloginsignin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class WelcomeBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_back);
        final Button logoutButton = findViewById(R.id.logout);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");


        final TextView welcomeText = findViewById(R.id.text_welcome);
        welcomeText.setText("Welcome Back " + username +"!");

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeBackActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
            }
        });
    }
}