package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private EditText inputName, inputBreed, inputAge, inputWeight,inputBio; //,inputEmail, inputPass, inputConfirmPass;
    private EditText text;
    private Button singUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        inputName = findViewById(R.id.inName);
        inputBreed = findViewById(R.id.inBreed);
        inputAge = findViewById(R.id.inAge);
        inputWeight = findViewById(R.id.inWeight);
        inputAge = findViewById(R.id.inAge);
        inputBio = findViewById(R.id.inBio);
        singUpButton = findViewById(R.id.signUpbtn2);

        //When the singUpButton is clicked the user is taken to another activity the home activity
        singUpButton.setOnClickListener(new View.OnClickListener() {
            private String userString, noEmptyString;
            private int userInt;
            @Override
            public void onClick(View view) {
                if(!checkDataEntered()){
                    Intent i = new Intent(SignUp.this,SignUpPage3.class);
                    startActivity(i);
                }
            }
            //This function checks if the the data entered by the user is valid
            private boolean checkDataEntered(){
                return false;
                //return !isDogName() | !isBreed() | !isWeight() | !isAge() | !isBio(); // | !isConfirm();
            }
            // Checks if the dog name entered by the user is valid
            private boolean isDogName() {
                userString = inputName.getText().toString();
                noEmptyString = "\\A\\w{2,20}\\z";

                if (userString.isEmpty()) {
                    inputName.setError("Field cannot be empty");
                    return false;
                } else if (userString.length() >= 15) {
                    inputName.setError("Name too long");
                    return false;
                } else if (!userString.matches(noEmptyString)) {
                    inputName.setError("White Spaces are not allowed");
                    return false;
                } else {
                    inputName.setError(null);
                    return true;
                }
            }
            private boolean isBio() {
                userString = inputBio.getText().toString();

                if (userString.isEmpty()) {
                    inputBio.setError("Field cannot be empty");
                    return false;
                 } else {
                    inputBio.setError(null);
                    return true;
                }
            }
            //check if the the weight is valid
            private boolean isWeight(){
                userString = inputWeight.getText().toString();
                //noEmptyString = "\\A\\w{4,20}\\z";

                if (userString.isEmpty()) {
                    inputWeight.setError("Field cannot be empty");
                    return false;
                } else if (userString.length() > 2) {
                    inputWeight.setError("Enter valid weight");
                    return false;
                } else {
                    inputWeight.setError(null);
                    return true;
                }
            }
            //check if the the age is valid
            private boolean isAge(){
                userString = inputAge.getText().toString();
                //noEmptyString = "\\A\\w{4,20}\\z";

                if (userString.isEmpty()) {
                    inputAge.setError("Field cannot be empty");
                    return false;
                } else if (userString.length() > 2) {
                    inputAge.setError("Enter Valid Age");
                    return false;

                } else {
                    inputAge.setError(null);
                    return true;
                }
            }

            //Check if the bio entered by the user is valid
            boolean isBreed() {
                userString = inputBreed.getText().toString();
                noEmptyString = "\\A\\w{4,20}\\z";

                if (userString.isEmpty()) {
                    inputBreed.setError("Field cannot be empty");
                    return false;
                } else if (userString.length() >= 15) {
                    inputBreed.setError("Breed name is to long");
                    return false;
                } else if (!userString.matches(noEmptyString)) {
                    inputBreed.setError("White Spaces are not allowed");
                    return false;
                } else {
                    inputBreed.setError(null);
                    return true;
                }
            }

        });


    }

}





