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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
