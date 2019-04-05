package com.example.whats;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAccessorsAdapter tabsAccessorsAdapters;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private Context context;
    private DatabaseReference rootRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();
        FirebaseApp.initializeApp(context);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootRef= FirebaseDatabase.getInstance().getReference();
        toolbar=findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Whats");

        viewPager=findViewById(R.id.viewpager);
        tabsAccessorsAdapters=new TabsAccessorsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAccessorsAdapters);

        tabLayout=findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user==null)
        {
            sendUserLoginActivity();
        }
        else
            {
                verifyUserExistence();
            }
    }

    private void verifyUserExistence()
    {
        String currentUserId=mAuth.getCurrentUser().getUid();
        rootRef.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.child("name").exists()))
                {
                    Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        sendUserSettingsActivity();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         if (item.getItemId()==R.id.main_logout_option)
         {

             mAuth.signOut();
             sendUserLoginActivity();
         }
        if (item.getItemId()==R.id.main_settings_option)
        {
            sendUserSettingsActivity();
        }
        if (item.getItemId()==R.id.main_find_friend_option)
        {

        }
        return true;
    }
    private void sendUserLoginActivity() {
        Intent loginIntent=new Intent(MainActivity.this,Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }
    private void sendUserSettingsActivity() {
        Intent settingsIntent=new Intent(MainActivity.this,SettingsActivity.class);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingsIntent);
        finish();

    }


}


