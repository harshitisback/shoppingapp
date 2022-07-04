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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Dashboard extends AppCompatActivity {

   DrawerLayout drawerLayout;
   NavigationView navigationView;
   Toolbar toolbar;
   TextView showname, showemail;

   FirebaseAuth mAuth;
   FirebaseUser user;
//   FirebaseDatabase database;
   DatabaseReference reference;

   String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.tb);

        showname = (TextView)findViewById(R.id.showname);
        showemail = (TextView)findViewById(R.id.showemail);

        View headerview = navigationView.getHeaderView(0);




//        Fetching the data of email and name to show

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null){
                    String fullname = userprofile.fullname;
                    String email = userprofile.email;

                    showname.setText(fullname);
                    showemail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Dashboard.this,"Something wrong happend",Toast.LENGTH_LONG).show();
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
                    startActivity(new Intent(Dashboard.this,UserInfoActivity.class));

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

//    public  void updateNavHeader() {
//        navigationView = (NavigationView) findViewById(R.id.navigationview);
//        View headerview = navigationView.getHeaderView(0);
//        showname = (TextView)findViewById(R.id.showname);
//        showemail = (TextView)findViewById(R.id.showemail);
//
//        showemail.setText(user.getEmail());
//        showname.setText(user.getDisplayName());
//
//        // now we will use Glide to load user image
//
//    }

//    loading first home as display

}