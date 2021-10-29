package com.example.PlayPal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.example.backuprecover.R;

public class ViewProfile extends AppCompatActivity {

    private Button exitProfile;
    private ImageButton prevImg, nextImg;
    private ImageSwitcher imageSwitcher;

    int imageList[] = {R.drawable.emy1, R.drawable.emy2, R.drawable.emy3, R.drawable.emy4};
    //int length = imageList.length;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        exitProfile = (Button) findViewById(R.id.exitProfile);
        prevImg = findViewById(R.id.prevImg);
        nextImg = findViewById(R.id.nextImg);
        imageSwitcher = findViewById(R.id.image_switcher);

        //Exit profile view button
        exitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

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
        });
    }
}