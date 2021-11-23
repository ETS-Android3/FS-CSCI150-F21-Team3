package com.example.playpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.playpal.databinding.ActivityMessagesBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class Messages extends AppCompatActivity {

    ActivityMessagesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /////
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_messages);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.messages);  //Set Home selected on bottom navigation
        ///----->> YOUR CODE GOES HERE

        //Navigation selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.userProfile:
                        startActivity(new Intent(getApplicationContext() ,UserProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.findPlaypal:
                        startActivity(new Intent(getApplicationContext() ,Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext() ,Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.messages:
                        return true;
                }
                return false;
            }
        });


    }

}
