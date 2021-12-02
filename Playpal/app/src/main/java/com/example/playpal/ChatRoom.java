package com.example.playpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChatRoom extends AppCompatActivity {

    private final List<ChatList> chatLists = new ArrayList<>();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private EditText newMsg;
    private ImageView sendBtn;
    private ImageView backBtn;
    private ImageView dogPic;
    private TextView dogName;

    private RecyclerView chatRecView;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        Bundle extras = getIntent().getExtras();

        final String userId = extras.getString("userId");
        final String chatId = extras.getString("chatId");
        final String selectedDogId = extras.getString("selectedDogId");
        final String getName = extras.getString("dogName");
        final String getPicUrl = extras.getString("imageUrl");


        //Toast.makeText(ChatRoom.this, selectedDogId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(ChatRoom.this, chatId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(ChatRoom.this, userId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(ChatRoom.this, getPicUrl, Toast.LENGTH_SHORT).show();

        newMsg = findViewById(R.id.newMsg);
        sendBtn = findViewById(R.id.sendMsg);
        backBtn = findViewById(R.id.backBtn);
        dogPic = findViewById(R.id.profilePic);
        dogName = findViewById(R.id.dogName);

        chatRecView = findViewById(R.id.chatRecView);

        Picasso.with(ChatRoom.this).load(getPicUrl).into(dogPic);
        dogName.setText(getName);


        chatRecView.setHasFixedSize(true);
        chatRecView.setLayoutManager(new LinearLayoutManager(ChatRoom.this));

        chatAdapter = new ChatAdapter(chatLists, ChatRoom.this);
        chatRecView.setAdapter(chatAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child("chats").child(chatId).child("history").getValue() != ""){

                    chatLists.clear();

                    for(DataSnapshot messagesSnapshot : snapshot.child("chats").child(chatId).child("history").getChildren()){


                        final String getSender = messagesSnapshot.child("sender").getValue(String.class);
                        final String getMsg = messagesSnapshot.child("msg").getValue(String.class);
                        final String getTimestamp = messagesSnapshot.child("timestamp").getValue(String.class);

                        Timestamp timestamp = new Timestamp(Long.parseLong(getTimestamp));
                        Date date = new Date(timestamp.getTime());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                        ChatList chatList = new ChatList(getSender,getMsg, simpleDateFormat.format(date), simpleTimeFormat.format(date));
                        chatLists.add(chatList);

                        chatAdapter.updateChatLists(chatLists);

                        chatRecView.scrollToPosition(chatLists.size()-1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatRoom.this, Messages.class);
                startActivity(i);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentMsg = newMsg.getText().toString();
                //Toast.makeText(ChatRoom.this, currentMsg, Toast.LENGTH_SHORT).show();
                //get current timestamps
                String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0,13);

                //create unique key for db that will contain msg, timestamp, and user's id in db
                String key = FirebaseDatabase.getInstance().getReference("users").child("chats").child(chatId).push().getKey();

                if (currentMsg.length() != 0) {
                    DatabaseReference msgHistory = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("history").child(key);

                    Map<String, Object> msgContent = new HashMap<>();

                    msgContent.put("msg", currentMsg);
                    msgContent.put("timestamp", currentTimeStamp);
                    msgContent.put("sender", userId);
                    msgHistory.updateChildren(msgContent);

                    newMsg.setText("");
                }
            }
        });
    }
}