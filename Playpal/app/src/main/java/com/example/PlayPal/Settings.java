package com.example.PlayPal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.backuprecover.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);  //Set Settings selected on bottom navigation

        //Navigation selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.userProfile:
                        startActivity(new Intent(getApplicationContext() ,UserProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext() ,Messages.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.findPlaypal:
                        startActivity(new Intent(getApplicationContext() ,Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });

        ListView listView = findViewById(R.id.settingsList);
        List<String> list = new ArrayList<>();
        list.add("Account");
        list.add("Notifications");
        list.add("About Us");
        list.add("Log Out");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(Settings.this, Account.class));
                        return;
                    case 1:
                        //Notifications
                        return;
                    case 2:
                        startActivity(new Intent(Settings.this, AboutUs.class));
                        return;
                    case 3:
                        //Log Out
                        return;
                }
            }
        });
    }
}