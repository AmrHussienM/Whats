package com.example.whats;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment {
    private View groupFragmentView;

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference gChatRef;
    private ArrayList<String> strings;
    private GroupChatAdapter adapter;
    public GroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        groupFragmentView= inflater.inflate(R.layout.fragment_groups, container, false);
        initialization();
        fillArrayListWithGroupNames();
         adapter=new GroupChatAdapter(strings,getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return groupFragmentView;
    }

    private void fillArrayListWithGroupNames() {

    gChatRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot d:dataSnapshot.getChildren()) {
                strings.add(d.getKey().toString());
                adapter.notifyDataSetChanged();

            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


    }


    private void initialization()
    {
      recyclerView= groupFragmentView.findViewById(R.id.group_chat_fragment_rec);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        gChatRef=firebaseDatabase.getReference().child("Groups");
        strings=new ArrayList<>();

    }



}
