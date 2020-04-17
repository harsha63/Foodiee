package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText Email, Password;
    Button button,submit;
    FirebaseAuth Auth;
    FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.Email2);
        Password = findViewById(R.id.pass2);
        submit = findViewById(R.id.registering2);
        button =findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
        Auth = FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= Email.getText().toString();
                String password= Password.getText().toString();

                final logindata R = new logindata(email,password);
                if(!email.isEmpty() && !password.isEmpty()){
                    Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                db.getReference().child(Auth.getUid()).setValue(R);
                            }
                        }
                    });
                }else{
                    Toast.makeText(Login.this,"please fill all the details",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void openRegister() {
        Intent intent= new Intent(this,Register.class);
        startActivity(intent);
    }
}