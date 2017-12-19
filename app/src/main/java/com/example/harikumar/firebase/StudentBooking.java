package com.example.harikumar.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentBooking extends AppCompatActivity {
Button button;
TextView textView;
//String timerData;
DatabaseReference databaseReference;
FirebaseAuth firebaseAuth;
//FirebaseUser firebaseUser;
Integer c=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_booking);
        button = (Button) findViewById(R.id.button5);
        textView = (TextView) findViewById(R.id.textView5);
       // timerData = (String) getIntent().getStringExtra("EXTRA_TIMERDATA");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child("evebus").child("status");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                if (name.equals("true")) {
                    button.setEnabled(true);
                    textView.setText("The booking is currently open");
                    bookopen();


                }
                if (name.equals("false")) {
                    changestatus();
                    button.setEnabled(false);
                    textView.setText("The booking is currently closed");


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child(timerData).child("evebus").child("count");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Integer count = dataSnapshot.getValue().hashCode();
                        Toast.makeText(StudentBooking.this, count.toString(), Toast.LENGTH_SHORT).show();
                        //setvalue(count);
                        // textView.setText("Booking has been made");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    */
      public void  bookopen() {
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("booking");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String book = dataSnapshot.getValue().toString();
                  /*  if (book.equals("false")) {
                        button.setEnabled(true);

                    } */
                    if (book.equals("true")) {
                        button.setEnabled(false);
                        textView.setText("Booking has been made");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child("evebus").child("count");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Integer count = dataSnapshot.getValue().hashCode();
                            Toast.makeText(StudentBooking.this, count.toString(), Toast.LENGTH_SHORT).show();
                            setter(count);
                            textView.setText("Booking has been made");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            });
        }

    private void changestatus() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("booking");
        databaseReference.setValue("false");

    }

    public void setter(Integer value) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("booking");
        databaseReference.setValue("true");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("transport").child("evebus").child("count");
        databaseReference.setValue(value+1);
        return;

    }

            }