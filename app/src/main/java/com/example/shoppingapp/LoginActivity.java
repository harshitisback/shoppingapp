package com.example.shoppingapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    EditText emaillogin, passlogin;
    TextView goback;
    //View buttonlogin;
    RelativeLayout loginbutton;
    FirebaseAuth auth;
    ProgressBar pb;
    TextView reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_actiivity);
        auth = FirebaseAuth.getInstance();
        goback = (TextView) findViewById(R.id.new_user_re);
        emaillogin = (EditText) findViewById(R.id.LoginEmail);
        passlogin = (EditText) findViewById(R.id.LoginPass);
        //buttonlogin = (View)findViewById(R.id.LoginButton);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        loginbutton = (RelativeLayout) findViewById(R.id.LoginButton);
        reset = (TextView) findViewById(R.id.forgot);


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPass.class));
            }
        });


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void userlogin() {
        String email = emaillogin.getText().toString().trim();
        String Pass = passlogin.getText().toString().trim();
        if (email.isEmpty()) {
            emaillogin.setError("Email is required");
            emaillogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emaillogin.setError("Please enter a valid emial");
            emaillogin.requestFocus();
            return;
        }
        if (Pass.isEmpty()) {
            passlogin.setError("password is required");
            passlogin.requestFocus();
            return;
        }
        if (Pass.length() < 6) {
            passlogin.setError("Min password length should be 6 characters!");
            passlogin.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // redirect to user profile
                    startActivity(new Intent(LoginActivity.this, UserProfile.class));
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);


                } else {
                    Toast.makeText(LoginActivity.this, "Failed to login Please check your credentials", Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);
                }
            }
        });

    }
}