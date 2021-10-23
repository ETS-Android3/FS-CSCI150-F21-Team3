package com.example.PlayPal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.backuprecover.R;

public class SignUpPage3 extends AppCompatActivity {

    private EditText inputCity, inputState, inputEmail, inputPass, inputConfirmPass;
    private EditText text;
    private Button singUpButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up_page3);
        inputCity = findViewById(R.id.inCity);
        inputState = findViewById(R.id.inState);
        inputEmail = findViewById(R.id.inEmail);
        inputPass = findViewById(R.id.inPass);
        inputConfirmPass = findViewById(R.id.inConfirmPass);
        singUpButton3 = findViewById(R.id.signUpbtn3);

        /* When the singUpButton is clicked the user is taken to another activity
        the home activity */
        singUpButton3.setOnClickListener(new View.OnClickListener() {
            private String userString, noEmptyString;
            @Override
            public void onClick(View view) {
                if(!checkDataEntered()){
                    Intent i = new Intent(SignUpPage3.this,Home.class);
                    startActivity(i);
                }

            }
            /*This function checks if the the data entered by the user is valid*/
            private boolean checkDataEntered(){
                return false;
                //return !isCity() | !isState() | !isEmail() | !isPass() | !isConfirm();
            }
            // Checks if the First name entered by the user is valid
            private boolean isCity() {
                userString = inputCity.getText().toString();
                noEmptyString = "\\A\\w{4,20}\\z";

                if (userString.isEmpty()) {
                    inputCity.setError("Field cannot be empty");
                    return false;
                } else if (userString.length() >= 15) {
                    inputCity.setError("Username too long");
                    return false;
                } else if (!userString.matches(noEmptyString)) {
                    inputCity.setError("White Spaces are not allowed");
                    return false;
                } else {
                    inputCity.setError(null);
                    return true;
                }
            }
            //Check if the last name entered by the user is valid
            boolean isState() {
                userString = inputState.getText().toString();
                noEmptyString = "\\A\\w{4,20}\\z";

                if (userString.isEmpty()) {
                    inputState.setError("Field cannot be empty");
                    return false;
                } else if (userString.length() >= 15) {
                    inputState.setError("Username too long");
                    return false;
                } else if (!userString.matches(noEmptyString)) {
                    inputState.setError("White Spaces are not allowed");
                    return false;
                } else {
                    inputState.setError(null);
                    return true;
                }
            }
            // Checks if the user entered a valid email
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
            // Check if the confirmation password is valid
            private Boolean isConfirm() {
                String pass = inputConfirmPass.getText().toString();
                String inPass = inputPass.getText().toString();

                if (pass.isEmpty()){
                    inputConfirmPass.setError("Field cannot be empty");
                    return false;
                }
                else if (!pass.equals(inPass)) {
                    inputConfirmPass.setError("Passwords do not match");
                    return false;
                }
                inputPass.setError(null);
                return true;
            }

        });

    }

}



