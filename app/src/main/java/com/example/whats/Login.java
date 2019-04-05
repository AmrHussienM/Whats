package com.example.whats;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
   // private FirebaseUser user;
    private Button loginButton,phoneLoginButton;
    private EditText userEmail,userPassword;
    private TextView needNewAccountLink,forgetPasswordLink;
    private FirebaseAuth mAuth;
    private Context context;
    private ProgressDialog loadingBar;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=getApplicationContext();
        FirebaseApp.initializeApp(context);
        mAuth=FirebaseAuth.getInstance();
      //  user=mAuth.getCurrentUser();
        rootRef= FirebaseDatabase.getInstance().getReference();
        initalizationField();
        needNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToRegisterActivity();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();
            }
        });

    }

    private void AllowUserToLogin() {
        String email=userEmail.getText().toString();
        String password=userPassword.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "please enter an email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "please enter an password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Signing In..");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String currentUserId=mAuth.getCurrentUser().getUid();
                                rootRef.child("Users").child(currentUserId).setValue("");

                                sendUserToMainActivity();
                                Toast.makeText(context, "Logged in success", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else {
                                String message=task.getException().toString();
                                Toast.makeText(context, "Error: "+message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }


    }

    private void initalizationField() {
        loginButton=findViewById(R.id.login_button);
        phoneLoginButton=findViewById(R.id.phone_login_button);
        userEmail=findViewById(R.id.login_email);
        userPassword=findViewById(R.id.login_password);
        needNewAccountLink=findViewById(R.id.register_link);
        forgetPasswordLink=findViewById(R.id.forgot_password_link);
        loadingBar=new ProgressDialog(this);
    }

    //@Override
    //protected void onStart() {
        //super.onStart();
        //if (user!=null)
        //{
        //    sendUserToMainActivity();
      //  }
    //}

    private void sendUserToMainActivity() {
        Intent mainIntent=new Intent(Login.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void sendUserToRegisterActivity() {
        Intent registerIntent=new Intent(Login.this,Register.class);
        startActivity(registerIntent);

    }
}
