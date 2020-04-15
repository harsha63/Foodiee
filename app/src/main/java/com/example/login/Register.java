package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText mFullName, mEmail, mPassword;
    private Button mRegisterButton;
    private TextView mLoginButton;
    FirebaseAuth mFAuth;
    ProgressBar mProgressBar;
    String userID;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.pass);
        mRegisterButton = findViewById(R.id.registering);
        mLoginButton   = findViewById(R.id.textView254);

        mFAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mProgressBar = findViewById(R.id.progressBar2);


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Emails = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String name = mFullName.getText().toString().trim();

                if(TextUtils.isEmpty(Emails))
                {
                    mEmail.setError("Email field Empty");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password field is Empty");
                    return;
                }
                if(password.length()<=6)
                {
                    mPassword.setError("Password length should be greater than 6");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                mFAuth.createUserWithEmailAndPassword(Emails,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = mFAuth.getCurrentUser().getUid();
                            FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
                            String naame1 = us.getDisplayName();
                            for (UserInfo userInfo : us.getProviderData()) {
                                if (naame1 == null && userInfo.getDisplayName() != null) {
                                    naame1 = userInfo.getDisplayName();
                                }
                            }



                            Toast.makeText(Register.this, "The name : " + naame1, Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",mFullName.getText().toString());
                            user.put("email",Emails);
                            user.put("password",password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }

                        else
                        {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}

