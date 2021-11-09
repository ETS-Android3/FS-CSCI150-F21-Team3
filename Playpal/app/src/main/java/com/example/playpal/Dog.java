package com.example.playpal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.MalformedURLException;
import java.net.URL;

// This is the dog class is used to hold information about dogs
public class Dog {

    // this are the class attributes
    private String name;
    private String age;
    private String bio;
    private String breed;
    private String imageUrl;
    private String owner;
    private String sex;
    private String weight;


    public void setAge(String age){
        this.age = age;
    }
    public String getAge(){
        return age;
    }
    // used to get the bio of the dog
    public String getBio() {

        return bio;
    }
    // used to set the bio of the dog
    public void setBio(String bio) {

        this.bio = bio;
    }
    // used to get the breed of thd dog
    public String getBreed() {
        return breed;
    }
    // used to set the breed of the dog
    public void setBreed(String breed) {
        this.breed = breed;
    }
    // used to get image
    public String getImageUrl() {
        return imageUrl;
    }
    // used to set image
    public void setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
    }

    // used to get dog name
    public String getDogName() {
        return name;
    }
    // used to set the name of the dog
    public void setDogName(String name) {

        this.name = name;
    }
    // used to get dog's owner
    public String getOwner() {
        return owner;
    }
    // used to set the name of the dog
    public void setOwner(String owner) {
        this.owner = owner;
    }

    // used to get the sex of the dog
    public String getSex() {
        return sex;
    }
    // used to set the city of the dog
    public void setSex(String sex) {

        this.sex = sex;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getWeight(){
        return weight;
    }

    // constructor.

    public Dog(String owner, String imageUrl, String sex, String bio,  String name, String weight, String age, String breed ) {
        this.age = age;
        this.bio = bio;
        this.breed = breed;
        this.imageUrl = imageUrl;
        this.name = name;
        this.owner = owner;
        this.sex = sex;
        this.weight = weight;
    }
    public Dog(){}
     
}
