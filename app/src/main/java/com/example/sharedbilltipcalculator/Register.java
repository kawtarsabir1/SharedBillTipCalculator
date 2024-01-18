package com.example.sharedbilltipcalculator;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Register extends AppCompatActivity {

    EditText username;
    EditText pssw;
    Button connect;
    FirebaseAuth mAuth;
    TextView gotologin;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        gotologin=findViewById(R.id.gotologin);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        });


        username=findViewById(R.id.newuser);
        pssw=findViewById(R.id.newpw);
        connect=findViewById(R.id.signeup);
        mAuth=FirebaseAuth.getInstance();
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,password;
                name=username.getText().toString();
                password=pssw.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(Register.this, "Enter UserName", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(name, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "account created ",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(),Login.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}