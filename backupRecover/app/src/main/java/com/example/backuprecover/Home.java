package com.example.backuprecover;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    // declaring our swipe deck and dog list array
    private SwipeDeck cardStack;
    private ArrayList<DogClass> dogList;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView extendedBio;
    private TextView age;
    private TextView weight;
    private Button exitBio;
    private Button viewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initializing our array of dog list and swipe deck.
        dogList = new ArrayList<>();
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
                Toast.makeText(Home.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
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
                //show pop up
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
}