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

        int[] imageId = {R.drawable.firulais,R.drawable.balto,R.drawable.victor,R.drawable.laika,R.drawable.fala,R.drawable.fala,R.drawable.fala};
        String[] name = {"Firulais", "Balto", "Victor", "Laika", "Fala","Fala","Fala"};
        String[] lastMessage = {"Hello","How are you?","Play Date?","How's frank doing?","Cya later","How's frank doing?","Cya later", "Cya later", "Cya later"};
        String[] lastmsgTime = {"8:45 pm", "7:34 pm","9:32 am","5:76 pm", "4:34 pm", "4:34 pm", "4:34 pm"};
        String[] phoneNo = {"7656610000","9999043232","7834354323","9876543211","5434432343","5434432343","5434432343"};
        String[] city = {"Fresno","Merced","Hanford","Visalia","Stockton","Stockton","Stockton"};

        ArrayList<User> userArrayList = new ArrayList<>();

        for(int i = 0;i< imageId.length;i++){

            User user = new User(name[i],lastMessage[i],lastmsgTime[i],phoneNo[i],city[i],imageId[i]);
            userArrayList.add(user);

        }


        ListAdapter listAdapter = new ListAdapter(Messages.this,userArrayList);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(Messages.this, ChatRoom.class);
                i.putExtra("name",name[position]);
                i.putExtra("phone",phoneNo[position]);
                i.putExtra("city",city[position]);
                i.putExtra("imageid",imageId[position]);
                startActivity(i);

            }
        });

    }

}
