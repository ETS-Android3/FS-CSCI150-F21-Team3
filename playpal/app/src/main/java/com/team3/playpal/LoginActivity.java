package com.team3.playpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    // initialize variables for login parameters and authentication
    private Button mLogin;
    private EditText mEmail,mPass;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and send them to MainActivity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // associate variables with XML fields and buttons
        mLogin = findViewById(R.id.loginBtn);
        mEmail = findViewById(R.id.loginEmail);
        mPass = findViewById(R.id.loginPass);
        mAuth = FirebaseAuth.getInstance();


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });


    }

    private void validation(){
        /*
        private Boolean isEmail() {
            String email = inputEmail.getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (email.isEmpty()) {
                inputEmail.setError("Field cannot be empty");
                return false;
            } else if (!email.matches(emailPattern)) {
                inputEmail.setError("Invalid email address");
                return false;
            } else {
                inputEmail.setError(null);
                //inputEmail.setErrorEnabled(false);
                return true;
            }
        }
        // Checks if the user entered a valid password
        private Boolean isPass() {
            String pass = inputPass.getText().toString();
            String password = "^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=!])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$";

            if (pass.isEmpty()) {
                inputPass.setError("Field cannot be empty");
                return false;
            } else if (!pass.matches(password)) {
                inputPass.setError("Password is too weak");
                return false;
            } else {
                inputPass.setError(null);
                return true;
            }
        }
         */
        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this, "Don't leave fields empty.",
                    Toast.LENGTH_SHORT).show();
        } else {
            signIn(email, password);
        }
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

}