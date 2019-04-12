package com.example.whats;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupChatActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private EditText editText;
        private Button button;
        private String groupName;
        private FirebaseAuth firebaseAuth;
        private FirebaseDatabase firebaseDatabase;
        private DatabaseReference messagesRef;
        private ArrayList<messageModel> listOfMEssages;
        private GroupChatActivityAdapter adapter;
        private DatabaseReference userRef;
        private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        initializeFields();
        fillArray();
        getUserName();
        adapter=new GroupChatActivityAdapter(listOfMEssages,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message =editText.getText().toString();
                if(message.isEmpty()){
                    editText.setError("you cant send an empty message");
                }
                else{

                    uploadMessage(message);


                }
            }
        });
    }

    private void uploadMessage(String message) {

    messageModel messageModel=new messageModel();
    messageModel.setName(userName);
    messageModel.setMessage(message);
    messagesRef.push().setValue(messageModel).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful())
            {

                editText.setText("");

            }
            else{

                Toast.makeText(GroupChatActivity.this, task.getException()+"", Toast.LENGTH_SHORT).show();
            }
        }
    });
    }

    private void getUserName() {

    userRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            userName=dataSnapshot.getValue().toString();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


    }

    private void fillArray() {
    messagesRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            listOfMEssages.clear();
            for (DataSnapshot d:dataSnapshot.getChildren()) {
                messageModel messageModel = new messageModel();
                messageModel.setMessage(d.child("message").getValue().toString());
                messageModel.setName(d.child("name").getValue().toString());
                listOfMEssages.add(messageModel);
                adapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }

    private void initializeFields() {

    recyclerView=findViewById(R.id.group_chat_activity_recycler_view);
    editText=findViewById(R.id.group_message_et);
    button=findViewById(R.id.send_button_gr_chat_activity);
    Intent i = getIntent();
    groupName=i.getStringExtra("groupName");
    firebaseDatabase=FirebaseDatabase.getInstance();
    messagesRef = firebaseDatabase.getReference().child("Groups").child(groupName);
    listOfMEssages=new ArrayList<>();
    userRef=firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("name");

    }
}
