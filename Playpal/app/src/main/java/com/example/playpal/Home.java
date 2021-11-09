package com.example.playpal;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    // declaring our swipe deck and dog list array
    private SwipeDeck cardStack;
    private ArrayList<Dog> dogList;
    public ArrayList<Dog> likedDogs;
    public String name;
    private Button viewProfile;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.findPlaypal);  //Set Home selected on bottom navigation

        //Navigation selected listener

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext() ,Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.findPlaypal:
                        return true;
                }
                return false;
            }
        });


        // initializing our array of dog list and swipe deck.
        dogList = new ArrayList<>();
        DeckAdapter adapter = new DeckAdapter(dogList, this);
        // setting adapter to the card deck.

        //likedDogs= new ArrayList<>();
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        viewProfile = (Button) findViewById(R.id.viewProfile);
        Button btn2 = (Button) findViewById(R.id.likebtn);
        Button btn = (Button) findViewById(R.id.disLikebtn);
/*
        // adding data to our array of dogs (hardcoding the data until we get the database connected).
        dogList.add(new DogClass("Victor", "Bulldog", "Fresno", "Mascot of Fresno State", R.drawable.victor));
        dogList.add(new DogClass("Laika", "Mongral", "Moscow", "One of the first animals in space", R.drawable.laika));
        dogList.add(new DogClass("Fala", "Scottish Terrier", "Washington D.C", "White house dog for FDR", R.drawable.fala));
        dogList.add(new DogClass("Balto", "Siberian Husky", "Cleveland", "Lead dog of sled dogs", R.drawable.balto));
        dogList.add(new DogClass("Firulais", "Chihuahua", "Fresno", "Your average chihuahua", R.drawable.firulais));
*/
       // String image = "https://firebasestorage.googleapis.com/v0/b/playpal-5b72e.appspot.com/o/profileImages%2FAG5dkgaFIIQSI1zxY7ETtKdo3Ih2?alt=media&token=249510be-de2a-42e3-b967-b0361279a82d";
       // DogClass dog = new  DogClass("4", "Mascot of Fresno State", "Bulldog", image , "Victor", "Fresno", "Male", "6");
       // dog.setImgUrl(image);
       // dogList.add(dog);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        //DatabaseReference UsersdRef = rootRef.child("Users");
        ViewProfile vP = new ViewProfile();
        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Dog dog = ds.getValue(Dog.class);
                    dog.setDogName(ds.child("name").getValue(String.class));
                    dogList.add(dog);
                    /*
                    totalSize = (int) dataSnapshot.getChildrenCount();
                    String img = ds.child("imageUrl").getValue(String.class);
                    String age = ds.child("age").getValue(String.class);
                    String bio = ds.child("bio").getValue(String.class);
                    String breed = ds.child("breed").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    String owner = ds.child("owner").getValue(String.class);
                    String sex = ds.child("sex").getValue(String.class);
                    String weight = ds.child("weight").getValue(String.class);

                    Log.d("TAG", age);
                    Log.d("TAG", bio);
                    Log.d("TAG", breed);
                    Log.d("TAG", name);
                    Log.d("TAG", owner);
                    Log.d("TAG", sex);
                    Log.d("TAG", weight);
                    Log.d("TAG", img);

                     */

                }
                adapter.notifyDataSetChanged();
                //ViewProfile vP = new ViewProfile();
                //Log.d("TAG", dogList.get(0).getDogName());
                vP.setW(dogList.get(0));
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //System.out.println(myRef.getChildren() );
        // creating the adapter class and passing the dog list array.

        cardStack.setAdapter(adapter);

        //event callback to our card deck.

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {

            @Override
            public void cardSwipedLeft(int position) {

                // if card swipe left
                if(dogList.size() > position + 1)
                    vP.setW(dogList.get(position+=1));
                dogList.remove(position -= 1);

                    //vP.setW(dogList.get(position+=1));
                Toast.makeText(Home.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // if card swipe right
                if(dogList.size() > position + 1) {
                    vP.setW(dogList.get(position += 1));
                }
                dogList.remove(position -= 1);


                //User likedDog = dogList.get(position);
                // likedDogs.add(likedDog);
               // String name = likedDog.getDogName();
                //Toast.makeText(Home.this, name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                
                viewProfile.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);


                // if no cards in the deck
                Toast.makeText(Home.this, "No more dogs in your city", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {
                // if card is swipped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // if card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }
        });

        // Open activity to view User profiles
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(intent);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(15);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(15);
            }
        });

    }
}