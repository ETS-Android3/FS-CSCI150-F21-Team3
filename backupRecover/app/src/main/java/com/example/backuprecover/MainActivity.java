package com.example.backuprecover;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class MainActivity extends AppCompatActivity {

    private Button logInBtn,signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        logInBtn = (Button) findViewById(R.id.logIn);
        signUpBtn = (Button) findViewById(R.id.signUp);

        // By clicking this button the user goes to the home activity
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Home.class);
                startActivity(i);
            }
        });
        // By clicking the is button the user goes to the sing up activity
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });


    }
}