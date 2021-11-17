package com.example.playpal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.xml.sax.helpers.LocatorImpl;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Home extends AppCompatActivity implements LocationListener {

    // Variables dependant on firebase usage
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private DatabaseReference db2;
    private String userId;
    private String userLoc;

    // location manipulation variables
    protected double lati, longi;
    LocationManager locationManager;

    // declaring our swipe deck and dog list array
    private SwipeDeck cardStack;
    private ArrayList<Dog> dogList;
    private Button viewProfile;
    //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Location permission request
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(Home.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Home.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);}
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);

        // initializing our array of dog list and swipe deck.
        dogList = new ArrayList<>();
        DeckAdapter adapter = new DeckAdapter(dogList, this);
        // setting adapter to the card deck.

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        viewProfile = (Button) findViewById(R.id.viewProfile);
        Button btn2 = (Button) findViewById(R.id.likebtn);
        Button btn = (Button) findViewById(R.id.disLikebtn);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user = reference.child(userId);
        ViewProfile vP = new ViewProfile();

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //obtained user's location via database reference
                userLoc = (String) snapshot.child("location").getValue();
                if(userLoc != null){ //if the user doesnt have a location entry in database
                    //query to filter out database entries that do not match userLoc
                    Query filterByUserLoc = reference.orderByChild("location").equalTo(userLoc);
                    filterByUserLoc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // user found
                            if  (dataSnapshot.exists()){
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Dog dog = ds.getValue(Dog.class);
                                    //Toast.makeText(Home.this, userId , Toast.LENGTH_SHORT).show();
                                    //Log.d("TAG",ds.getKey());
                                    //dogList.add(dog);
                                    if(!ds.getKey().equals(userId)){
                                        dogList.add(dog);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                //ViewProfile vP = new ViewProfile();
                                //Log.d("TAG", dogList.get(0).getDogName());
                                if(!dogList.isEmpty()){
                                    vP.setW(dogList.get(0));
                                    // creating the adapter class and passing the dog list array.
                                    cardStack.setAdapter(adapter);
                                    //event callback to our card deck.
                                    cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {

                                        @Override
                                        public void cardSwipedLeft(int position) {
                                            // if card swipe left
                                            if (dogList.size() > position + 1)
                                                vP.setW(dogList.get(position += 1));
                                            //vP.setW(dogList.get(position+=1));
                                            //Toast.makeText(Home.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void cardSwipedRight(int position) {
                                            if (dogList.size() > position + 1)
                                                vP.setW(dogList.get(position += 1));
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
                            // user not found
                            else{
                                Toast.makeText(Home.this, "Test Reached" , Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.findPlaypal);  //Set Home selected on bottom navigation

        //Navigation selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.userProfile:
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext(), Messages.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.findPlaypal:
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        lati = location.getLatitude();
        longi = location.getLongitude();

        Geocoder geocoder = new Geocoder(this);
        List<Address> adresses = null;
        try {
            adresses = geocoder.getFromLocation(lati,longi,1);
            String city = adresses.get(0).getLocality();
            String state = adresses.get(0).getAdminArea();
            String locee = state + "_" + city;

            db = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("coordinates");
            db2 = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            Map<String, Object> userCoord= new HashMap<>();
            userCoord.put("longitude", longi);
            userCoord.put("latitude", lati);
            db.updateChildren(userCoord);

            Map<String, Object> userLoc = new HashMap<>();
            userLoc.put("location", locee);
            db2.updateChildren(userLoc);

        } catch (IOException e) {
            e.printStackTrace();
        }

        locationManager.removeUpdates(this);

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}