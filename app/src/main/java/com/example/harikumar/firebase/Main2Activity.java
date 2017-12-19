package com.example.harikumar.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        setContentView(R.layout.activity_main2);
        radioGroup = (RadioGroup) findViewById(R.id.Radiogp);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    radioButton = (RadioButton) findViewById(checkedId);

                    if (radioButton.getText().toString().equals("Transport")) {
                        finish();
                        //startActivity(new Intent(Main2Activity.this,MainActivity.class));
                        Toast.makeText(Main2Activity.this, "transport", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Main2Activity.this, Firebasetransport.class));
                    }

                    if (radioButton.getText().toString().equals("Students")) {
                        finish();
                        //startActivity(new Intent(Main2Activity.this,MainActivity.class));
                        Toast.makeText(Main2Activity.this, "students", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Main2Activity.this, LogIn.class));
                    }

                }
            });

        }
    }

