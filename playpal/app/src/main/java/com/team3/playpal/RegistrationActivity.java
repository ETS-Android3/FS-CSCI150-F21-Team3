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

public class RegistrationActivity extends AppCompatActivity {

    private Button rCompleteReg;
    private EditText rEmail,rPass,rConfirmPass;
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
        setContentView(R.layout.activity_registration);

        rEmail = findViewById(R.id.regEmail);
        rPass = findViewById(R.id.regPass);
        rConfirmPass = findViewById(R.id.confirmRegPass);
        rCompleteReg = findViewById(R.id.completeReg);
        mAuth = FirebaseAuth.getInstance();


        rCompleteReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }


    private void validation(){
        String email = rEmail.getText().toString();
        String password = rPass.getText().toString();
        String confirmPass = rConfirmPass.getText().toString();
        if(email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()){
            Toast.makeText(RegistrationActivity.this, "Don't leave fields empty.",
                    Toast.LENGTH_SHORT).show();
        } else {
            createAccount(email, password);
        }
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }



    private void reload() {
        Intent i = new Intent(RegistrationActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

}