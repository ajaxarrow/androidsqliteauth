package com.example.tabillaloginsignin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final TextView registerhere = findViewById(R.id.txt_register_here);

        final Button onLogin = findViewById(R.id.auth_btnLogin);
        final EditText txtInputUsername = findViewById(R.id.auth_txtUsername);
        final EditText txtInputPassword = findViewById(R.id.auth_txtPassword);
        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        onLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtInputUsername.getText().toString();
                String password = txtInputPassword.getText().toString();

                if (username.trim().isEmpty() || username.trim() == ""){
                    showError("Username is Invalid!");
                    txtInputUsername.setText("");
                }
                else if (password.trim().isEmpty() || password.trim() == ""){
                    showError("Password is Invalid!");
                    txtInputPassword.setText("");
                }
                else{
                    if(dbHelper.authenticateUser(username, password)){
                        Intent intent = new Intent(SignInActivity.this, WelcomeBackActivity.class);
                        intent.putExtra("username", username);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
                    }
                    else{
                        showError("User doesn't exist!");
                    }

                }
            }
        });

        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
            }
        });
    }

    public void showError(String errorMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        builder.setTitle("Error");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}