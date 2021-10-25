package com.team3.playpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Registration_Option extends AppCompatActivity {

    // initialize variables for login parameters and authentication
    private Button mLogin,mSignUp;
    private EditText mEmail,mPass;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_reg_option);

        // associate variables with XML fields and buttons
        mLogin = findViewById(R.id.loginBtn);
        mSignUp = findViewById(R.id.signupBtn);
        mEmail = findViewById(R.id.loginEmail);
        mPass = findViewById(R.id.loginPass);
        mAuth = FirebaseAuth.getInstance();


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_Registration_Option.this,RegistrationActivity.class);
                startActivity(i);
            }
        });

    }

    //
    private void validation(){
        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(Login_Registration_Option.this, "Don't leave fields empty.",
                    Toast.LENGTH_SHORT).show();
        } else {
            signIn(email, password);
        }
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG,"signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        Intent i = new Intent(Login_Registration_Option.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(Login_Registration_Option.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
    }

    private void reload() {
        Intent i = new Intent(Login_Registration_Option.this,MainActivity.class);
        startActivity(i);
        finish();
    }
    private void updateUI(FirebaseUser user) { }
}