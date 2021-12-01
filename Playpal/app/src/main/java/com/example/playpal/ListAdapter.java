package com.example.playpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<com.example.playpal.Dog> {


    public ListAdapter(Context context, ArrayList<com.example.playpal.Dog> dogArrayList){

        super(context,R.layout.chatter,dogArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        com.example.playpal.Dog dog = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chatter,parent,false);

        }

        //TO BE IMPLEMENTED
        //get LAst mssg fnction
        //get time of last sent mssg

        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView userName = convertView.findViewById(R.id.personName);
        //TextView lastMsg = convertView.findViewById(R.id.lastMessage);
        // TextView time = convertView.findViewById(R.id.msgtime);

        //imageView.setImageResource(dog.getImageUrl());
        Picasso.with(getContext()).load(dog.getImageUrl()).into(imageView);
        userName.setText(dog.getName());
        //lastMsg.setText(user.lastMessage);
        //time.setText(user.lastMsgTime);


        return convertView;
    }
}



