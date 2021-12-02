package com.example.playpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView logOutText;
    private Button logOutBtn;
    private Button cancelBtn;

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
                if (item.getItemId() == R.id.userProfile){
                    Intent i = new Intent(Settings.this, UserProfile.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();
                }
                else if (item.getItemId() == R.id.messages){
                    Intent i = new Intent(Settings.this, Messages.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();
                }
                else if (item.getItemId() == R.id.settings){
                    return true;
                }
                else if (item.getItemId() == R.id.findPlaypal){
                    Intent i = new Intent(Settings.this, Home.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();
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
                    case 0: //Account
                        startActivity(new Intent(Settings.this, Account.class));
                        return;
                    case 1: //Notifications
                        //CODE HERE
                        return;
                    case 2: //About Us
                        startActivity(new Intent(Settings.this, AboutUs.class));
                        return;
                    case 3: //Log Out
                        logOutDialog();
                        return;
                }
            }
        });
    }
    public void logOutDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View bioPopupView = getLayoutInflater().inflate(R.layout.log_out_popup, null);
        cancelBtn = (Button) bioPopupView.findViewById(R.id.cancelBtn);
        logOutBtn = (Button) bioPopupView.findViewById(R.id.logOutBtn);
        dialogBuilder.setView(bioPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Settings.this, LogOrRegSelect.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }
}