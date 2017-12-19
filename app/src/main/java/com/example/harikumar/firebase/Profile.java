package com.example.harikumar.firebase;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
private Button save;
private EditText name,rollno;
public RadioGroup rg1;
private RadioButton radioButton;
//String namestr,rollnostr,bus;
FirebaseAuth firebaseAuth;
DatabaseReference databaseReference;
Spinner spinner1;
String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        save=(Button)findViewById(R.id.button4);
        name=(EditText)findViewById(R.id.editText7);
        rollno=(EditText)findViewById(R.id.editText8);
        spinner1=(Spinner)findViewById(R.id.spinner);
        String[] items = new String[]{"bus 1", "bus 2", "bus 3"};
        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(Profile.this, android.R.layout.simple_spinner_item, items);
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(areasAdapter);
        spinner1.setOnItemSelectedListener(this);

        firebaseAuth=FirebaseAuth.getInstance();



    }
    private void UserProfile()
    {
        final String namestr=name.getText().toString();
        final String rollnostr=rollno.getText().toString();
       // String bus=radioButton.getText().toString();

        if(TextUtils.isEmpty(namestr)) {
            //  Toast.makeText(Profile.this,"Enter the name",Toast.LENGTH_LONG).show();
            name.setError("Name field empty");
            return;
        }
        if(TextUtils.isEmpty(rollnostr))
        {
            rollno.setError("Enter Valid phone number");
        }



       firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("booking");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String book = dataSnapshot.getValue().toString();

                UserInformation userInformation= new UserInformation(namestr,rollnostr,select,book);
                setter(userInformation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //UserInformation userInformation= new UserInformation(namestr,rollnostr,select,"false");
            }
    @Override
    public void onClick(View v) {

        if(v == save)
        {

            UserProfile();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                //startActivity(new Intent(MainActivity.this,Studentnavigate.class));
                // Whatever you want to happen when the first item gets selected

                select="bus1";

//Toast.makeText(MainActivity.this,"Selection is 1",Toast.LENGTH_LONG).show();
                break;
            case 1:
                // Whateveou want to happen when the second item gets selected
                select="bus2";
                // Toast.makeText(MainActivity.this,"Selection is 2",Toast.LENGTH_LONG).show();

                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                select="bus3";
                //Toast.makeText(MainActivity.this,"Selection is 3",Toast.LENGTH_LONG).show();

                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setter(UserInformation userInformation) {
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(Profile.this,user.getUid(),Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Profile.this,Navigator.class);
        intent.putExtra("EXTRA_TIMERDATA",select);
        startActivity(intent);
        Toast.makeText(Profile.this,select,Toast.LENGTH_SHORT).show();

    }
}
