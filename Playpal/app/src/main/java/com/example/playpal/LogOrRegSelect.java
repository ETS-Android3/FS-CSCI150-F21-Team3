package com.example.playpal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LogOrRegSelect extends AppCompatActivity {

    private Button mLogin,mSignUp;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and send them to MainActivity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Intent i = new Intent(LogOrRegSelect.this,Home.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_or_reg_select);

        mLogin = findViewById(R.id.loginChoice);
        mSignUp = findViewById(R.id.signupChoice);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogOrRegSelect.this,Home.class);
                startActivity(i);
            }
        });


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogOrRegSelect.this,Home.class);
                startActivity(i);
            }
        });

    }
}