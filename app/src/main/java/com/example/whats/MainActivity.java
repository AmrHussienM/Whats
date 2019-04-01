package com.example.whats;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAccessorsAdapter tabsAccessorsAdapters;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(context);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
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
    }

    private void sendUserLoginActivity() {
        Intent loginIntent=new Intent(MainActivity.this,Login.class);
        startActivity(loginIntent);

    }
}


