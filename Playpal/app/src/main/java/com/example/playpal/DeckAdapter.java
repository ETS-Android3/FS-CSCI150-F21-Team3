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

    private ArrayList<Dog> dogRoster;
    private Context context;

    // on below line we have created constructor for our variables.
    public DeckAdapter(ArrayList<Dog> courseData, Context context) {
        this.dogRoster = courseData;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return dogRoster.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return dogRoster.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // in get view method we are inflating our layout on below line.
        View v = convertView;
        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_card_grill, parent, false);
        }
        // on below line we are initializing our variables and setting data to our variables.
        ((TextView) v.findViewById(R.id.idDogName)).setText(dogRoster.get(position).getName());
        ((TextView) v.findViewById(R.id.idBio)).setText(dogRoster.get(position).getBio());
        ((TextView) v.findViewById(R.id.idDogLocation)).setText(dogRoster.get(position).prettyPrintCity());

        ImageView ivBasicImage = (ImageView) v.findViewById(R.id.idImage);
        Picasso.with(context).load(dogRoster.get(position).getImageUrl()).into(ivBasicImage);
        return v;
    }
}