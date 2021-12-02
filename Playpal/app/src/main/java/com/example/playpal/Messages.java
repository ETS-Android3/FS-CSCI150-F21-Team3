package com.example.playpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.playpal.databinding.ActivityMessagesBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Messages extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String userId;

    private ArrayList<Dog> dogList;
    private ArrayList<String> matchIds;

    ActivityMessagesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("connections").child("matches");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dogList = new ArrayList<>();
                matchIds = new ArrayList<>();

                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        matchIds.add(ds.getKey());
                        DatabaseReference getMatches = FirebaseDatabase.getInstance().getReference("users").child(ds.getKey());
                        getMatches.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot matchshot) {

                                Dog dog = matchshot.getValue(Dog.class);
                                dogList.add(dog);

                                ListAdapter listAdapter = new ListAdapter(Messages.this,dogList);
                                binding.listview.setAdapter(listAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }

                    binding.listview.setClickable(true);
                    binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView parent, View view, int position, long id) {
                            getInfo(position);
                        }
                    });
                }
            }

            private void getInfo(int position) {
                DatabaseReference getChatId = FirebaseDatabase.getInstance().getReference("users").child(userId).child("connections").child("matches")
                        .child(String.valueOf(matchIds.get(position)));

                getChatId.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String chatId = String.valueOf(snapshot.child("chatId").getValue());

                        Intent i= new Intent(getApplicationContext(), ChatRoom.class);
                        i.putExtra("selectedDogId",String.valueOf(matchIds.get(position)));
                        i.putExtra("chatId",chatId);
                        i.putExtra("userId",userId);
                        i.putExtra("imageUrl",dogList.get(position).getImageUrl());
                        i.putExtra("dogName",dogList.get(position).getName());
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.messages);  //Set Home selected on bottom navigation

        //Navigation selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.userProfile){
                    Intent i = new Intent(Messages.this, UserProfile.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();

                }
                else if (item.getItemId() == R.id.messages){
                    return true;
                }
                else if (item.getItemId() == R.id.settings){
                    Intent i = new Intent(Messages.this, Settings.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();

                }
                else if (item.getItemId() == R.id.findPlaypal){
                    Intent i = new Intent(Messages.this, Home.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();
                }
                return false;
            }
        });
    }
}

