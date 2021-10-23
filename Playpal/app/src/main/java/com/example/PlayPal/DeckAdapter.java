package com.example.PlayPal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.backuprecover.R;

import java.util.ArrayList;

public class DeckAdapter extends BaseAdapter {

    // Create an array of type DogClass
    // also created the context
    private ArrayList<DogClass> dogData;
    private Context context;

    // constructor for DogData and context
    public DeckAdapter(ArrayList<DogClass> dogData, Context context) {
        this.dogData = dogData;
        this.context = context;
    }

    @Override
    //return the size of the dogData array
    public int getCount() {
        return dogData.size();
    }

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
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_card_grill, parent, false);
        }

        // Set the data to be displayed at the dog's card
        ((TextView) v.findViewById(R.id.idDogName)).setText(dogData.get(position).getDogName());
        ((TextView) v.findViewById(R.id.idDogName)).setText(dogData.get(position).getDogName());
        ((TextView) v.findViewById(R.id.idBio)).setText(dogData.get(position).getBio());
        //((TextView) v.findViewById(R.id.idBreed)).setText(dogData.get(position).getBreed());
        ((TextView) v.findViewById(R.id.idCity)).setText(dogData.get(position).getCity());
        ((ImageView) v.findViewById(R.id.idImage)).setImageResource(dogData.get(position).getImgId());
        return v;
    }

}