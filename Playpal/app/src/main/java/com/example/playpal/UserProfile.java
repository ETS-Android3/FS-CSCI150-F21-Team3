package com.example.playpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String userId;
    private EditText editPetName;
    private EditText editPetAge;
    private EditText editPetBreed;
    private EditText editPetWeight;
    private EditText editPetSex;
    private EditText editPetBio;
    private TextView viewPetName;
    private TextView viewPetAge;
    private TextView viewPetBreed;
    private TextView viewPetWeight;
    private TextView viewPetSex;
    private TextView viewPetBio;
    private Button editProfile;
    private Button doneProfile;
    private ImageView userPic;




    //    private Button exitUserProfile;

    public Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        viewPetName = findViewById(R.id.textPetName);
        viewPetAge = findViewById(R.id.textPetAge);
        viewPetBreed = findViewById(R.id.textPetBreed);
        viewPetWeight = findViewById(R.id.textPetWeight);
        viewPetSex = findViewById(R.id.textPetSex);
        viewPetBio = findViewById(R.id.textPetBio);
        userPic = findViewById(R.id.imageBox);



//Firebase get userID
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user = reference.child(userId);


        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Dog userDog = snapshot.getValue(Dog.class);
                viewPetName.setText(userDog.getName());
                viewPetAge.setText(" Age: "+userDog.getAge());
                viewPetBreed.setText(" Breed: "+userDog.getBreed());
                viewPetWeight.setText(" Weight: "+userDog.getWeight());
                viewPetSex.setText(" Sex: "+userDog.getSex());
                viewPetBio.setText(" Bio: "+userDog.getBio());
                Picasso.with(UserProfile.this).load(userDog.getImageUrl()).into(userPic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        editPetName = (EditText) findViewById(R.id.petName);
        //       viewPetName = (TextView) findViewById(R.id.textPetName);
        editPetAge = (EditText) findViewById(R.id.petAge);
//        viewPetAge = (TextView) findViewById(R.id.textPetAge);
        editPetBreed = (EditText) findViewById(R.id.petBreed);
//        viewPetBreed = (TextView) findViewById(R.id.textPetBreed);
        editPetWeight = (EditText) findViewById(R.id.petWeight);
//        viewPetWeight = (TextView) findViewById(R.id.textPetWeight);
        editPetSex = (EditText) findViewById(R.id.petSex);
//        viewPetSex = (TextView) findViewById(R.id.textPetSex);
        editPetBio = (EditText) findViewById(R.id.petBio);
//        viewPetBio = (TextView) findViewById(R.id.textPetBio);

        editProfile = (Button) findViewById(R.id.editProfile);
        doneProfile = (Button) findViewById(R.id.doneProfile);



        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String age;
                String breed;
                String weight;
                String sex;
                String bio;

                editPetName.setVisibility(View.VISIBLE);
                viewPetName.setVisibility(View.INVISIBLE);

                editPetAge.setVisibility(View.VISIBLE);
                viewPetAge.setVisibility(View.INVISIBLE);

                editPetBreed.setVisibility(View.VISIBLE);
                viewPetBreed.setVisibility(View.INVISIBLE);

                editPetWeight.setVisibility(View.VISIBLE);
                viewPetWeight.setVisibility(View.INVISIBLE);

                editPetSex.setVisibility(View.VISIBLE);
                viewPetSex.setVisibility(View.INVISIBLE);

                editPetBio.setVisibility(View.VISIBLE);
                viewPetBio.setVisibility(View.INVISIBLE);

                age = String.valueOf(viewPetAge.getText());
                age = age.replace(" Age: ", "");

                breed = String.valueOf(viewPetBreed.getText());
                breed = breed.replace(" Breed: ", "");

                weight = String.valueOf(viewPetWeight.getText());
                weight = weight.replace(" Weight: ", "");

                sex = String.valueOf(viewPetSex.getText());
                sex = sex.replace(" Sex: ", "");

                bio = String.valueOf(viewPetBio.getText());
                bio = bio.replace(" Bio: ", "");

                editPetName.setText(viewPetName.getText());
                editPetAge.setText(age);
                editPetBreed.setText(breed);
                editPetWeight.setText(weight);
                editPetSex.setText(sex);
                editPetBio.setText(bio);

                userPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    }
                });
            }


        });



        doneProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPetName.setVisibility(View.INVISIBLE);
                viewPetName.setVisibility(View.VISIBLE);

                editPetAge.setVisibility(View.INVISIBLE);
                viewPetAge.setVisibility(View.VISIBLE);

                editPetBreed.setVisibility(View.INVISIBLE);
                viewPetBreed.setVisibility(View.VISIBLE);

                editPetWeight.setVisibility(View.INVISIBLE);
                viewPetWeight.setVisibility(View.VISIBLE);

                editPetSex.setVisibility(View.INVISIBLE);
                viewPetSex.setVisibility(View.VISIBLE);

                editPetBio.setVisibility(View.INVISIBLE);
                viewPetBio.setVisibility(View.VISIBLE);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.userProfile);  //Set Home selected on bottom navigation

        //((TextView) findViewById(R.id.weight)).setText("test");



        //Navigation selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.findPlaypal:
                        startActivity(new Intent(getApplicationContext() ,Home.class));
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
                    case R.id.userProfile:
                        return true;
                }
                return false;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            resultUri = data.getData();
            userPic.setImageURI(resultUri);
        }

    }
}

