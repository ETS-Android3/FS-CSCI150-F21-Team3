package com.example.playpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ViewProfile extends AppCompatActivity {
    public void clickBackAnimation(View view) {
        Button back = (Button) findViewById(R.id.disLikebtn);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        animation.setInterpolator(interpolator);
        back.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
    }
    private Button exitProfile;
    //private ImageButton prevImg, nextImg;
    //private ImageSwitcher imageSwitcher;
    private ImageView imageView;
    private TextView viewName, viewWeight, viewBio, viewAge, viewSex, viewBreed;
    private static Dog dogInfo;

    //int imageList[] = {R.drawable.emy1, R.drawable.emy2, R.drawable.emy3, R.drawable.emy4};
    //int length = imageList.length;
    int i = 0;


    public void setW(Dog dogInfo) {
        this.dogInfo = dogInfo;
        Log.i("TAG",dogInfo.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        exitProfile = (Button) findViewById(R.id.exitProfile);
        //prevImg = findViewById(R.id.prevImg);
        //nextImg = findViewById(R.id.nextImg);
        imageView = findViewById(R.id.image_switcher);
        //imageSwitcher = findViewById(R.id.image_switcher);


        //TextView textView = (TextView)findViewById(R.id.name);
        viewName = (TextView)findViewById(R.id.name);
        viewAge = (TextView)findViewById(R.id.age);
        viewBreed = (TextView)findViewById(R.id.breed);
        viewBio = (TextView)findViewById(R.id.extendedBio);
        viewSex = (TextView)findViewById(R.id.sex);
        viewWeight = (TextView)findViewById(R.id.weight);

        viewName.setText(dogInfo.getName());
        viewAge.setText("Age: " + dogInfo.getAge());
        viewBio.setText("Bio: " + dogInfo.getBio());
        viewBreed.setText("Breed: " + dogInfo.getBreed());
        viewSex.setText("Sex: " + dogInfo.getSex());
        viewWeight.setText("Weight: " + dogInfo.getWeight());

        //Display image
        Glide.with(this).load(dogInfo.getImageUrl()).into(imageView);

        //Exit profile view button
        exitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
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
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        /* CODE FOR MULTIPLE IMAGES
        //Image Switcher
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //LAYOUT PARAMS
                return imageView;
            }
        });
        imageSwitcher.setImageResource(imageList[0]);

        //Previous image button
        prevImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSwitcher.setInAnimation(ViewProfile.this,
                        R.anim.from_left);
                imageSwitcher.setOutAnimation(ViewProfile.this,
                        R.anim.to_right);
                --i;
                if (i < 0) {
                    i = imageList.length-1;
                }
                imageSwitcher.setImageResource(imageList[i]);
            }
        });

        //Next image button
        nextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSwitcher.setInAnimation(ViewProfile.this,
                        R.anim.from_right);
                imageSwitcher.setOutAnimation(ViewProfile.this,
                        R.anim.to_left);
                ++i;
                if (i == imageList.length) {
                    i = 0;
                }
                imageSwitcher.setImageResource(imageList[i]);
            }
        }); */
    }
}