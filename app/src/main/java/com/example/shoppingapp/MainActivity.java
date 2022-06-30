package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
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
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText name, email, pass, confpass;
    RelativeLayout signupButton;
    ProgressBar pb;
    TextView already;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.editTextTextPersonName);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        pass = (EditText) findViewById(R.id.editTextTextPassword);
        confpass = (EditText) findViewById(R.id.editTextTextPassword3);
        signupButton = (RelativeLayout) findViewById(R.id.signUpButton);
        pb = (ProgressBar)findViewById(R.id.pgbar);
        already = (TextView)findViewById(R.id.already);


        // already text clickListner

already.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
});


// button setonclicklistner
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();
                String Pass = pass.getText().toString().trim();
                String Name = name.getText().toString().trim();
                String Confpass = confpass.getText().toString().trim();

                if (Name.isEmpty()){
                    name.setError("Your name is required");
                    name.requestFocus();
                    return;
                }
                if (Email.isEmpty()){
                    email.setError("Your Email is required");
                    email.requestFocus();
                    return;
                }
                if (Pass.isEmpty()){
                    pass.setError("Your Pass is required");
                    pass.requestFocus();
                    return;
                }
                if (Confpass.isEmpty()){
                    confpass.setError("Confirm Your Password ");
                    confpass.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    email.setError("Please Provide Valid email");
                    email.requestFocus();
                    return;
                }
                if (Pass.length()<6){
                    pass.setError("Min Password length should be 6 char");
                    pass.requestFocus();
                    return;
                }
                if(!Confpass.equals(Pass)){
                    confpass.setError("Your Password should be same");
                    confpass.requestFocus();
                    return;
                }

                pb.setVisibility(view.VISIBLE);
                auth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(Name, Email);

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MainActivity.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                        pb.setVisibility(view.VISIBLE);
                                        // redirect to login page
                                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        pb.setVisibility(view.GONE);



                                    }else{
                                        Toast.makeText(MainActivity.this,"Failed to register try again!",Toast.LENGTH_LONG).show();
                                        pb.setVisibility(view.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this,"Failed to register try again!",Toast.LENGTH_LONG).show();
                            pb.setVisibility(view.GONE);
                        }
                    }
                });
            }
        });





    }
}