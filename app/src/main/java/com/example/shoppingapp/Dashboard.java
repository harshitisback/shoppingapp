package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.Ui.Profileui;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;


public class Dashboard extends AppCompatActivity {

   DrawerLayout drawerLayout;
   NavigationView navigationView;
   Toolbar toolbar;


    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.tb);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();



        View headerview = navigationView.getHeaderView(0);
        TextView name = headerview.findViewById(R.id.nav_header_name);
        TextView email = headerview.findViewById(R.id.nav_header_email);
        CircleImageView navImage = headerview.findViewById(R.id.nav_header_img);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userModel = snapshot.getValue(User.class);
                        name.setText(userModel.getFullname());
                        email.setText(userModel.getEmail());

                        Glide.with(Dashboard.this).load(userModel.getProfileImg()).into(navImage);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






//        updateNavHeader();


        loadfragment(new HomeFragment());

//        setting here our tool bar


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opendrawer,R.string.closedrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.opthome){
                    loadfragment(new HomeFragment());

                } else  if(id == R.id.optinfo){
//                    startActivity(new Intent(Dashboard.this,UserInfoActivity.class));
                    loadfragment(new Profileui());


                }else  if(id == R.id.optcart){

                }else if(id == R.id.optlogout){
                    startActivity(new Intent(Dashboard.this,LoginActivity.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }


    }

    private void loadfragment(Fragment fragment) {

        FragmentManager fm =  getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.containers,fragment);
        ft.commit();
    }


}