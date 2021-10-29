package com.team3.playpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class InitProfileActivity extends AppCompatActivity {

    private EditText mOwner, mDogName, mBreed, mAge, mWeight, mSex, mBio;
    private Button initProfile;
    private ImageView img;
    public Uri resultUri;

    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private StorageReference storage;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_profile);

        img = findViewById(R.id.imgBox);
        mOwner = findViewById(R.id.ownerName);
        mDogName = findViewById(R.id.dogName);
        mBreed = findViewById(R.id.dogBreed);
        mAge = findViewById(R.id.dogAge);
        mWeight = findViewById(R.id.dogWeight);
        mSex = findViewById(R.id.dogSex);
        mBio = findViewById(R.id.dogBio);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        initProfile = findViewById(R.id.initProfileBtn);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        initProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase();
                Intent i = new Intent(InitProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            resultUri = data.getData();
            img.setImageURI(resultUri);
        }
    }

    private void updateDatabase(){
        String owner = mOwner.getText().toString();
        String dName = mDogName.getText().toString();
        String breed = mBreed.getText().toString();
        String age = mAge.getText().toString();
        String weight = mWeight.getText().toString();
        String sex = mSex.getText().toString();
        String bio = mBio.getText().toString();

        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("owner", owner);
        userInfo.put("name", dName);
        userInfo.put("breed", breed);
        userInfo.put("age", age);
        userInfo.put("weight", weight);
        userInfo.put("sex", sex);
        userInfo.put("bio", bio);

        db.updateChildren(userInfo);

        if(resultUri != null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;


            try{
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            }catch(IOException e){
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Map<String, Object> addURL = new HashMap<>();
                            addURL.put("imageUrl",imageURL);
                            db.updateChildren(addURL);
                            /*
                            Toast.makeText(InitProfileActivity.this, imageURL,
                                    Toast.LENGTH_SHORT).show();
                            */
                        }
                    });

                }
            });



/*
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageRef.child("users/"+userId+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Toast.makeText(InitProfileActivity.this, "Test reached.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Toast.makeText(InitProfileActivity.this, "Test failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
*/
        }else{
            finish();
        }


    }


}

