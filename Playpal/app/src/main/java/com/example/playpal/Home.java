package com.example.playpal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements LocationListener {

    // Variables dependant on firebase usage
    private FirebaseAuth mAuth;
    private DatabaseReference coordinatesRef;
    private DatabaseReference userIDRef;
    private String userId;
    private String userLoc;

    // location manipulation variables
    protected double lati, longi;
    LocationManager locationManager;

    // declaring our swipe deck and dog list array
    private SwipeDeck cardStack;
    private ArrayList<Dog> dogList;
    private ArrayList<String> dogIds;
    private ArrayList<String> seenIds;
    private ArrayList<String> swipedYesIds;

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


        // getting the xml components into variables
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        viewProfile = (Button) findViewById(R.id.viewProfile);
        Button btn2 = (Button) findViewById(R.id.likebtn);
        Button btn = (Button) findViewById(R.id.disLikebtn);

        //Firebase get userID
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user = reference.child(userId);
        ViewProfile vP = new ViewProfile();

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                seenIds = new ArrayList<>();

                Query profileSeen = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                profileSeen.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotz) {
                        //snapshot.getChildrenCount();
                        //Log.i("TAG",String.valueOf(snapshotz.getChildrenCount()));
                        for (DataSnapshot ys : snapshotz.getChildren()) {
                            //Log.i("TAG",ys.getKey());
                            seenIds.add(ys.getKey());
                        }
                        //obtained user's location via database reference
                        userLoc = (String) snapshot.child("location").getValue();
                        if(userLoc != null){ //if the user doesnt have a location entry in database
                            //query to filter out database entries that do not match userLoc
                            Query filterByUserLoc = reference.orderByChild("location").equalTo(userLoc);
                            filterByUserLoc.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // initializing our array of dog list and swipe deck.
                                    cardStack.removeAllViews();
                                    dogList = new ArrayList<>();
                                    dogIds = new ArrayList<>();
                                    swipedYesIds = new ArrayList<>();
                                    //seenIds = new ArrayList<>();
                                    DeckAdapter adapter = new DeckAdapter(dogList, Home.this);
                                    // setting adapter to the card deck.
                                    // user found
                                    if  (dataSnapshot.exists()){
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            Dog dog = ds.getValue(Dog.class);

                                            if(!ds.getKey().equals(userId) && !seenIds.contains(ds.getKey())){
                                                dogList.add(dog);
                                                dogIds.add(ds.getKey());
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                        //ViewProfile vP = new ViewProfile();
                                        //Log.d("TAG", dogList.get(0).getDogName());
                                    }
                                    // user not found
                                    else{
                                        Toast.makeText(Home.this, "Test Reached" , Toast.LENGTH_SHORT).show();
                                    }
                                    if(!dogList.isEmpty()){
                                        viewProfile.setVisibility(View.VISIBLE);
                                        btn.setVisibility(View.VISIBLE);
                                        btn2.setVisibility(View.VISIBLE);
                                        cardStack.setVisibility(View.VISIBLE);
                                        vP.setW(dogList.get(0));
                                        // creating the adapter class and passing the dog list array.
                                        cardStack.setAdapter(adapter);
                                        //event callback to our card deck.
                                        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                                            @Override
                                            public void cardSwipedLeft(int position) {
                                                // if card swipe left
                                                if (dogList.size() > position + 1) {

                                                    DatabaseReference profileSeenRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                                                    Map<String, Object> seenUser = new HashMap<>();
                                                    seenUser.put(dogIds.get(position), true);
                                                    profileSeenRef.updateChildren(seenUser);

                                                    vP.setW(dogList.get(position += 1));
                                                } else{
                                                    DatabaseReference profileSeenRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                                                    Map<String, Object> seenUser = new HashMap<>();
                                                    seenUser.put(dogIds.get(position), true);
                                                    profileSeenRef.updateChildren(seenUser);
                                                }


                                                DatabaseReference profileDisliked = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("matches").child("nope");
                                                Map<String, Object> sayNay = new HashMap<>();
                                                sayNay.put(dogIds.get(position), true);
                                                profileDisliked.updateChildren(sayNay);

                                            }

                                            @Override
                                            public void cardSwipedRight(int position) {
                                                if (dogList.size() > position + 1) {
                                                    DatabaseReference profileSeenRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                                                    Map<String, Object> seenUser = new HashMap<>();
                                                    seenUser.put(dogIds.get(position), true);
                                                    profileSeenRef.updateChildren(seenUser);

                                                    vP.setW(dogList.get(position += 1));
                                                }else{
                                                    DatabaseReference profileSeenRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                                                    Map<String, Object> seenUser = new HashMap<>();
                                                    seenUser.put(dogIds.get(position), true);
                                                    profileSeenRef.updateChildren(seenUser);
                                                }

                                                DatabaseReference profileLiked = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("matches").child("yes");
                                                Map<String, Object> sayYay = new HashMap<>();
                                                sayYay.put(dogIds.get(position), true);
                                                profileLiked.updateChildren(sayYay);

                                                Query profileSeen = FirebaseDatabase.getInstance().getReference().child("users").child(dogIds.get(position)).child("matches").child("yes");
                                                profileSeen.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshotter) {
                                                        if(snapshotter.exists()){
                                                            for(DataSnapshot wtf : snapshotter.getChildren()){
                                                                swipedYesIds.add(wtf.getKey());
                                                                //Log.i("TAG",String.valueOf(wtf.getKey()));
                                                            }
                                                            if (swipedYesIds.contains(userId)){
                                                                Toast.makeText(Home.this, "You've been matched!", Toast.LENGTH_SHORT).show();
                                                            }
                                                            //Log.i("TAG",String.valueOf(snapshotter.getChildrenCount()));
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }

                                            @Override
                                            public void cardsDepleted() {

                                                viewProfile.setVisibility(View.INVISIBLE);
                                                btn.setVisibility(View.INVISIBLE);
                                                btn2.setVisibility(View.INVISIBLE);
                                                // if no cards in the deck
                                                //Toast.makeText(Home.this, "No more dogs in your city", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void cardActionDown() {
                                                // if card is swipped down.
                                                // Log.i("TAG", "CARDS MOVED DOWN");
                                            }

                                            @Override
                                            public void cardActionUp() {
                                                // if card is moved up.
                                                //Log.i("TAG", "CARDS MOVED UP");
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
                                    } else{
                                        //Log.i("TAG", "MOTHA");
                                        //Log.i("TAG",String.valueOf(dogList.size()));
                                        //cardStack.setVisibility(View.GONE);
                                        dogList = new ArrayList<>();
                                        adapter = new DeckAdapter(dogList, Home.this);
                                        cardStack.setAdapter(adapter);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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

            coordinatesRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("coordinates");
            userIDRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            Map<String, Object> userCoord= new HashMap<>();
            userCoord.put("longitude", longi);
            userCoord.put("latitude", lati);
            coordinatesRef.updateChildren(userCoord);

            Map<String, Object> userLoc = new HashMap<>();
            userLoc.put("location", locee);
            userIDRef.updateChildren(userLoc);

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
/*
Query profileSeen = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                                profileSeen.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshotz) {
                                        //snapshot.getChildrenCount();
                                        //Log.i("TAG",String.valueOf(snapshotz.getChildrenCount()));
                                        for (DataSnapshot ys : snapshotz.getChildren()) {
                                            //Log.i("TAG",ys.getKey());
                                            seenIds.add(ys.getKey());
                                        }
                                        //Log.i("TAG", String.valueOf(seenIds.size()));

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
 */