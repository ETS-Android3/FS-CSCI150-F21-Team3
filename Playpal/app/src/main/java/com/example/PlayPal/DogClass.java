package com.example.PlayPal;
// This is the dog class is used to hold information about dogs
public class DogClass {

    // this are the class attributes
    private String dogName;
    private String city;
    private String breed;
    private String bio;
    private int imgId;

    // used to get image
    public int getImgId() {

        return imgId;
    }
    // used to set image
    public void setImgId(int imgId) {

        this.imgId = imgId;
    }

    // used to get dog name
    public String getDogName() {

        return dogName;
    }
    // used to set the name of the dog
    public void setDogName(String dogName) {

        this.dogName = dogName;
    }
    // used to get the breed of thd dog
    public String getBreed() {

        return breed;
    }
    // used to set the breed of the dog
    public void setBreed(String breed) {
        this.breed = breed;
    }

    // used to get the city of the dog
    public String getCity() {

        return city;
    }
    // used to set the city of the dog
    public void setCity(String city) {

        this.city = city;
    }
    // used to get the bio of the dog
    public String getBio() {

        return bio;
    }
    // used to set the bio of the dog
    public void setBio(String bio) {

        this.bio = bio;
    }

    // constructor.
    public DogClass(String dogName, String breed, String city, String bio, int imgId) {
        this.dogName = dogName;
        this.breed = breed;
        this.city = city;
        this.bio = bio;
        this.imgId = imgId;
    }
}
