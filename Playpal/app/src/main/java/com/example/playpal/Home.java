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

//For animation
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.animation.BounceInterpolator;

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
    private static Boolean myInvisible;
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
        /* Because buttons have an animation function onClick another buttons are assigned
           to them to make them visible or invisible
        * */
        Button viewProfile2 = (Button) findViewById(R.id.viewProfile);
        Button btn2 = (Button) findViewById(R.id.likebtn);
        Button btn = (Button) findViewById(R.id.disLikebtn);

        /* Make the buttons invisible to avoid making them visible if the stack of dogs cards
        is empty when the user goes to another activity and comes back to the home activity */
        viewProfile2.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);

        //Firebase get userID
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user = reference.child(userId);
        ViewProfile vP = new ViewProfile();
        seenIds = new ArrayList<>();

        user.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Query profileSeen = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                profileSeen.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotz) {

                        for (DataSnapshot ys : snapshotz.getChildren()) {
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
                                        // make the buttons visible
                                        viewProfile2.setVisibility(View.VISIBLE);
                                        btn.setVisibility(View.VISIBLE);
                                        btn2.setVisibility(View.VISIBLE);

                                        cardStack.setVisibility(View.VISIBLE);
                                        vP.setW(dogList.get(0));
                                        // creating the adapter class and passing the dog list array.
                                        cardStack.setAdapter(adapter);
                                        //event callback to our card deck.
                                    } else{
                                        // make button invisible is the list is empty
                                        viewProfile2.setVisibility(View.INVISIBLE);
                                        btn.setVisibility(View.INVISIBLE);
                                        btn2.setVisibility(View.INVISIBLE);
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
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                clickDisLikeAnimation(null);
                // if card swipe left
                //btn.setBackground(getResources().getDrawable(R.drawable.fill_dislike));
                if (dogList.size() > position + 1) {

                    DatabaseReference profileSeenRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                    Map<String, Object> seenUser = new HashMap<>();
                    seenUser.put(dogIds.get(position), true);
                    profileSeenRef.updateChildren(seenUser);

                    vP.setW(dogList.get(position));
                } else{
                    DatabaseReference profileSeenRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                    Map<String, Object> seenUser = new HashMap<>();
                    seenUser.put(dogIds.get(position), true);
                    profileSeenRef.updateChildren(seenUser);
                }
                DatabaseReference profileDisliked = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("connections").child("nope");
                Map<String, Object> sayNay = new HashMap<>();
                sayNay.put(dogIds.get(position), true);
                profileDisliked.updateChildren(sayNay);

                clickDisLikeAnimation2(null);
            }

            @Override
            public void cardSwipedRight(int position) {
                //clickLikeAnimation(null);
                //btn2.setBackground(getResources().getDrawable(R.drawable.like_w_outline));
                //Log.i("TAG",dogIds.get(position));
                //if card swiped right
                if (dogList.size() > position + 1) {
                    DatabaseReference profileSeenRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                    Map<String, Object> seenUser = new HashMap<>();
                    seenUser.put(dogIds.get(position), true);
                    profileSeenRef.updateChildren(seenUser);

                    vP.setW(dogList.get(position));
                }else{
                    DatabaseReference profileSeenRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("seen");
                    Map<String, Object> seenUser = new HashMap<>();
                    seenUser.put(dogIds.get(position), true);
                    profileSeenRef.updateChildren(seenUser);
                }

                DatabaseReference profileLiked = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("connections").child("yes");
                Map<String, Object> sayYay = new HashMap<>();
                sayYay.put(dogIds.get(position), true);
                profileLiked.updateChildren(sayYay);

                isConnectionMatch(dogIds.get(position));
/*
                DatabaseReference profileSeen = FirebaseDatabase.getInstance().getReference().child("users").child(dogIds.get(position)).child("connections").child("yes");
                profileSeen.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotter) {
                        if(snapshotter.exists()){
                            for(DataSnapshot wtf : snapshotter.getChildren()){
                                swipedYesIds.add(wtf.getKey());
                            }
                            if (swipedYesIds.contains(userId)){
                                Toast.makeText(Home.this, "You've been matched!", Toast.LENGTH_SHORT).show();

                                //String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();

                                DatabaseReference skitskat = FirebaseDatabase.getInstance().getReference().child("users").child(dogIds.get(position));
                                //Log.i("TAG",dogIds.get(position));
                                //skitskat.child("connections").child("matches").child(userId).setValue(true);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
*/
                clickLikeAnimation2(null);
            }

            private void isConnectionMatch(String s) {
                //Toast.makeText(Home.this, s, Toast.LENGTH_SHORT).show();
                DatabaseReference swipedOnConnections = FirebaseDatabase.getInstance().getReference().child("users").child(s).child("connections").child("yes").child(userId);
                swipedOnConnections.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot swipedSnap) {
                        if(swipedSnap.exists()){
                            Toast.makeText(Home.this, s, Toast.LENGTH_SHORT).show();

                            String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                            DatabaseReference skitskat = FirebaseDatabase.getInstance().getReference().child("users").child(s);
                            DatabaseReference skitskut = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                            DatabaseReference addChat = FirebaseDatabase.getInstance().getReference();
                            //Log.i("TAG",dogIds.get(position));
                            skitskat.child("connections").child("matches").child(userId).child("ChatId").setValue(key);
                            skitskut.child("connections").child("matches").child(s).child("ChatId").setValue(key);
                            addChat.child("chats").child(key).setValue("");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void cardsDepleted() {
                //myInvisible = false;
                viewProfile2.setVisibility(View.INVISIBLE);
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


    //The Following creates an animation for the viewProfile. A little bounce
    public void clickAnimation(View view){
        Button button = (Button)findViewById(R.id.viewProfile);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1,20);
        animation.setInterpolator(interpolator);
        button.startAnimation(animation);

        Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                //Once the animation is over open the ViewProfile activity
                startActivity(intent);
            }
        });
    }
    //The Following creates an animation for the like button. A little bounce
    public void clickLikeAnimation(View view) {
        Button likes = (Button) findViewById(R.id.likebtn);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        animation.setInterpolator(interpolator);
        likes.startAnimation(animation);
        likes.setBackground(getResources().getDrawable(R.drawable.fill_like));

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                //Once the animation is over
                likes.setBackground(getResources().getDrawable(R.drawable.like_outline));
                cardStack.swipeTopCardRight(10);

            }
        });

    }
    public void clickLikeAnimation2(View view) {
        Button likes = (Button) findViewById(R.id.likebtn);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        animation.setInterpolator(interpolator);
        likes.startAnimation(animation);
        likes.setBackground(getResources().getDrawable(R.drawable.fill_like));

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                likes.setBackground(getResources().getDrawable(R.drawable.like_outline));
                //Once the animation is over
                //cardStack.swipeTopCardRight(10);

            }
        });

    }
    //The Following creates an animation for the dislike button. A little bounce
    public void clickDisLikeAnimation(View view) {
        Button dislikes = (Button) findViewById(R.id.disLikebtn);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        animation.setInterpolator(interpolator);
        dislikes.startAnimation(animation);
        dislikes.setBackground(getResources().getDrawable(R.drawable.fill_dislike));
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            //Once the animation is over
            public void onAnimationEnd(Animation animation) {
                dislikes.setBackground(getResources().getDrawable(R.drawable.dislike_outline));
                cardStack.swipeTopCardLeft(10);
            }

        });

    }

    public void clickDisLikeAnimation2(View view) {
        Button dislikes = (Button) findViewById(R.id.disLikebtn);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        animation.setInterpolator(interpolator);
        dislikes.startAnimation(animation);
        dislikes.setBackground(getResources().getDrawable(R.drawable.fill_dislike));
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            //Once the animation is over
            public void onAnimationEnd(Animation animation) {
                dislikes.setBackground(getResources().getDrawable(R.drawable.dislike_outline));
                //cardStack.swipeTopCardLeft(10);
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
    public void onProviderEnabled(@NonNull String provider) {}

    @Override
    public void onProviderDisabled(@NonNull String provider) {}

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