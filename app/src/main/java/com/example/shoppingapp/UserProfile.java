package com.example.shoppingapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

public class UserProfile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav;
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView sidelistner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        nav = (NavigationView) findViewById(R.id.navigationbar);
//        sidelistner = (ImageView)findViewById(R.id.menu_drawer);

//        getSupportActionBar().hide();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);

    drawerLayout.addDrawerListener(toggle);

    toggle.syncState();





    }
}