package com.example.playpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.userProfile);  //Set Home selected on bottom navigation

        //((TextView) findViewById(R.id.weight)).setText("test");

        //Navigation selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.findPlaypal:
                        startActivity(new Intent(getApplicationContext() ,Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext() ,Messages.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext() ,Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.userProfile:
                        return true;
                }
                return false;
            }
        });

    }
}