package com.example.PlayPal;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.backuprecover.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    // declaring our swipe deck and dog list array
    private SwipeDeck cardStack;
    private ArrayList<DogClass> dogList;
    public ArrayList<DogClass> likedDogs;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView extendedBio;
    private TextView age;
    public String name;
    private TextView weight;
    private Button exitBio;
    private Button viewProfile;

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
        likedDogs= new ArrayList<>();
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        viewProfile = (Button) findViewById(R.id.viewProfile);

        // adding data to our array of dogs (hardcoding the data until we get the database connected).
        dogList.add(new DogClass("Victor", "Bulldog", "Fresno", "Mascot of Fresno State", R.drawable.victor));
        dogList.add(new DogClass("Laika", "Mongral", "Moscow", "One of the first animals in space", R.drawable.laika));
        dogList.add(new DogClass("Fala", "Scottish Terrier", "Washington D.C", "White house dog for FDR", R.drawable.fala));
        dogList.add(new DogClass("Balto", "Siberian Husky", "Cleveland", "Lead dog of sled dogs", R.drawable.balto));
        dogList.add(new DogClass("Firulais", "Chihuahua", "Fresno", "Your average chihuahua", R.drawable.firulais));

        // creating the adapter class and passing the dog list array.
        final DeckAdapter adapter = new DeckAdapter(dogList, this);

        // setting adapter to the card deck.
        cardStack.setAdapter(adapter);

        //event callback to our card deck.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // if card swipe left

                Toast.makeText(Home.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // if card swipe right

                DogClass likedDog = dogList.get(position);
               // likedDogs.add(likedDog);
                String name = likedDog.getDogName();
                Toast.makeText(Home.this, name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
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


        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bioDialog();
            }
        });
        Button btn = (Button) findViewById(R.id.disLikebtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(15);

            }
        });
        Button btn2 = (Button) findViewById(R.id.likebtn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(15);
            }
        });

    }

    public void bioDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View bioPopupView = getLayoutInflater().inflate(R.layout.bio_popup, null);
        exitBio = (Button) bioPopupView.findViewById(R.id.exitBio);
        dialogBuilder.setView(bioPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        exitBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    //Test comment
}