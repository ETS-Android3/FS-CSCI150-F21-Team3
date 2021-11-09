package com.example.playpal;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.dynamicanimation.animation.DynamicAnimation;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeckAdapter extends BaseAdapter {

    // Create an array of type User
    // also created the context
    private ArrayList<Dog> dogData;
    private Context context;

    // constructor for DogData and context
    public DeckAdapter(ArrayList<Dog> dogData, Context context) {
        this.dogData = dogData;
        this.context = context;
    }

    public String getDogData(int position) {
        return dogData.get(position).getDogName();
    }

    @Override
    //return the size of the dogData array
    public int getCount() {
        return dogData.size();
    }

    //@Override
    //return the size of the dogData array
   // public String getDogWeight(int position) {
        //return dogData.get(position).getWeight();
    //}

    @Override
    //return the element at a position
    public Object getItem(int position) {
        return dogData.get(position);
    }

    //get the id o
    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        View v2 = convertView;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_card_grill, parent, false);
            v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_profile, parent, false);
        }

        // Set the data to be displayed at the dog's card
        ((TextView) v.findViewById(R.id.idDogName)).setText(dogData.get(position).getDogName());
        //((TextView) v.findViewById(R.id.idDogName)).setText("test");
        ((TextView) v.findViewById(R.id.idBio)).setText(dogData.get(position).getBio());
        ((TextView) v2.findViewById(R.id.weight)).setText(dogData.get(position).getWeight());
       // ((TextView) v.findViewById(R.id.idCity)).setText(dogData.get(position).getCity());
        //Log.d("TAG", dogData.get(position).getImgUrl().toString());
        ImageView ivBasicImage = (ImageView) v.findViewById(R.id.idImage);
        Picasso.with(context).load(dogData.get(position).getImageUrl()).into(ivBasicImage);


        //Picasso.with(context).load(dogData.get(position).getImgUrl()).into(ivBasicImage);
        return v;
    }

}