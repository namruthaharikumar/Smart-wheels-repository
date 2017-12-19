package com.example.harikumar.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Evebusbooking extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton,rb1,rb2;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
  //  String timerData;
    EditText editText;
    TextView textView,display;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evebusbooking);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
      //  editText=(EditText)findViewById(R.id.editText9);
        rb1=(RadioButton)findViewById(R.id.radioButton2);
        rb2=(RadioButton)findViewById(R.id.radioButton3);
        textView=(TextView)findViewById(R.id.textView);
        display=(TextView)findViewById(R.id.tw4);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
     //   timerData = (String) getIntent().getStringExtra("EXTRA_TIMERDATA");

        //setContentView(R.layout.activity_main2);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child("evebus").child("status");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                if (name.equals("true")) {
                    rb1.setChecked(true);
                    getinfo();
                    textView.setText("The booking is currently open");


                }
                if (name.equals("false")) {
                    rb2.setChecked(true);
                    textView.setText("The booking is currently closed");
                    reset();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
             //   timerData = (String) getIntent().getStringExtra("EXTRA_TIMERDATA");
                radioButton = (RadioButton) findViewById(checkedId);

                if (radioButton.getText().toString().equals("Open Booking")) {
                    //startActivity(new Intent(Main2Activity.this,MainActivity.class));
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child("evebus").child("status");
                    databaseReference.setValue(true);

                    Toast.makeText(Evebusbooking.this, "Open Booking", Toast.LENGTH_LONG).show();
                   // startActivity(new Intent(Main2Activity.this, Firebasetransport.class));
                }

                if (radioButton.getText().toString().equals("Close Booking")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child("evebus").child("status");
                    databaseReference.setValue(false);
                    //startActivity(new Intent(Main2Activity.this,MainActivity.class));
                    Toast.makeText(Evebusbooking.this, "Close Booking", Toast.LENGTH_LONG).show();
                   // startActivity(new Intent(Main2Activity.this, LogIn.class));
                }

            }
        });

    }

    private void reset() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child("evebus").child("count");
        databaseReference.setValue(0);
    }

    public void getinfo() {
        display=(TextView)findViewById(R.id.tw4);
     //   editText=(EditText)findViewById(R.id.editText9);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child("evebus").child("count");
databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Integer count=dataSnapshot.getValue().hashCode();
       Toast.makeText(Evebusbooking.this,count.toString(),Toast.LENGTH_SHORT).show();
        display.setText("Total seats booked for evening trip is "+count);
       // editText.setText("Total seats booked for evening trip is "+count);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
    }
}

