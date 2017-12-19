package com.example.harikumar.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebasetransport extends AppCompatActivity {
    private DatabaseReference mdb;
    Button button;
    EditText editText,password;
    String data,authid,busval,element;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebasetransport);
        button = (Button) findViewById(R.id.button2);
        password=(EditText)findViewById(R.id.editText3);
        editText = (EditText) findViewById(R.id.editText);

     //   try {

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  String comp=editText.getText().toString();
                   final String key=password.getText().toString();
                    busval=editText.getText().toString();
                    mdb = FirebaseDatabase.getInstance().getReference().child("transport").child(busval).child("userid");
                  //  element=mdb.getKey();
                    mdb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data = dataSnapshot.getValue().toString();
                            // editText.setText(data);
                           // Toast.makeText(Firebasetransport.this,data,Toast.LENGTH_LONG).show();
                            if(key.equals(data)) {
                                Toast.makeText(Firebasetransport.this, "logged in successfully", Toast.LENGTH_LONG).show();
                                finish();
                                //startActivity(Intent intent=new Intent(Firebasetransport.this,Checkid.class));
                                Intent intent=new Intent(Firebasetransport.this,Transportnav.class);
                                intent.putExtra("EXTRA_TIMERDATA",busval);
                                startActivity(intent);
                                //mdb.setValue("success");
                            }                    else
                            {  Toast.makeText(Firebasetransport.this,"incorrect userid or password",Toast.LENGTH_LONG).show();
                                //mdb.setValue("failure");

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //authid= mdb.setValue(key).toString();
                    }
            });
       // } catch (Exception e) {
        }
    }


