package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText Name, Contact, Email, Password;
    Button customer,delivery;
    FirebaseAuth Auth;
    FirebaseDatabase db1,db2;
    logindata Reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name= findViewById(R.id.fullName);
        Contact= findViewById(R.id.editText2);
        Email= findViewById(R.id.Email);
        Password= findViewById(R.id.pass);
        customer=findViewById(R.id.registering);
        delivery=findViewById(R.id.button2);

        Auth=FirebaseAuth.getInstance();
        db1=FirebaseDatabase.getInstance();

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name= Name.getText().toString();
                final String contact= Contact.getText().toString();
                Log.d("HELLLOOO", contact+ " "+name);
                final String email= Email.getText().toString();
                final String password= Password.getText().toString();

                Reg = new logindata(name,contact,email,password);
                if(!email.isEmpty() && !password.isEmpty()){
                    Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                DatabaseReference fr =  db1.getReference().child(Auth.getUid());
                                HashMap<String, Object> result = new HashMap<>();
                                result.put("name", name);
                                result.put("Email", email);
                                result.put("contact", contact);
                                result.put("password", password);
                                fr.setValue(result);
                            }
                        }
                    });
                }else{
                    Toast.makeText(Register.this,"please fill all the details",Toast.LENGTH_SHORT).show();
                }
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= Name.getText().toString();
                String contact= Contact.getText().toString();
                String email= Email.getText().toString();
                String password= Password.getText().toString();

                Reg = new logindata(name,contact,email,password);
                if(!email.isEmpty() && !password.isEmpty()){
                    Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                db2.getReference().child(Auth.getUid()).setValue(Reg);
                            }
                        }
                    });
                }else{
                    Toast.makeText(Register.this,"please fill all the details",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }}

