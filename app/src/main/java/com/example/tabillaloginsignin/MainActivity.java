package com.example.tabillaloginsignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView loginhere = findViewById(R.id.txt_login_here);
        final Button onRegister = findViewById(R.id.auth_btnRegister);
        final EditText txtInputUsername = findViewById(R.id.auth_txtUsername);
        final EditText txtInputEmail = findViewById(R.id.auth_txtEmail);
        final EditText txtInputPassword = findViewById(R.id.auth_txtPassword);
        final DatabaseHelper dbHelper = new DatabaseHelper(this);




        onRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtInputUsername.getText().toString();
                String email = txtInputEmail.getText().toString();
                String password = txtInputPassword.getText().toString();

                if (username.trim().isEmpty() || username.trim() == "" || username.trim().length() < 4){
                    showError("Username is Invalid! It should be atleast 4 characters long.");
                    txtInputUsername.setText("");
                }
                else if (email.trim().isEmpty() || email.trim() == "" || !isValidEmail(email)){
                    showError("Email is Invalid!");
                    txtInputEmail.setText("");
                }
                else if (password.trim().isEmpty() || password.trim() == "" || password.trim().length() < 8){
                    showError("Password is Invalid! It should be atleast 8 characters long.");
                    txtInputPassword.setText("");
                }
                else if(dbHelper.doesEmailExists(email)){
                    showError("Email Already Exists!");
                }
                else if(dbHelper.doesUsernameExists(username)){
                    showError("Username Already Exists!");
                }
                else{
                    dbHelper.addUser(username, password, email);
                    Intent intent = new Intent(MainActivity.this, WelcomeScreen.class);
                    intent.putExtra("username", username);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
                }
            }
        });

        loginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);

            }
        });

    }

    public void showError(String errorMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

    public boolean isValidEmail(String email) {
        final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(EMAIL_REGEX);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

}