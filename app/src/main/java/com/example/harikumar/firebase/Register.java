package com.example.harikumar.firebase;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button regbtn;
    private EditText editEmail, editpassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mauthlisteneter;
    private DatabaseReference databaseReference;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        regbtn = (Button) findViewById(R.id.button3);
        editEmail = (EditText) findViewById(R.id.editText2);
        editpassword = (EditText) findViewById(R.id.editText4);
        textView = (TextView) findViewById(R.id.textView3);
       /* mauthlisteneter = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null)
                {

                }

            }

            regbtn.setOnClickListener(this);

        };*/
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() != null) {
Toast.makeText(Register.this,"Email verified",Toast.LENGTH_SHORT).show();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            // storeId = user.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("name");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String name = dataSnapshot.getValue().toString();
                    if (name.equals("")) {
                        finish();
                        startActivity(new Intent(Register.this, Profile.class));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    private void btnUserLogin_Click() {
       // progressDialog.show(Register.this, "Please wait", "Processing...", true).show();
        String email = editEmail.getText().toString().trim();
        String pass = editpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Register.this, "Enter valid email id", Toast.LENGTH_LONG).show();

        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(Register.this, "Enter password", Toast.LENGTH_LONG).show();
        }
        progressDialog.setMessage("Registering user....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.hide();
                if (task.isSuccessful()) {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user != null) {

                        String name = "";
                        String rollno = "";
                        String bus = "";
                        String booking="false";

                        UserInformation userInformation = new UserInformation(name,rollno,bus,booking);

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

                        databaseReference.child(user.getUid()).setValue(userInformation);

                        user.sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(Register.this, "Click The Verification Link Sent To The Given Email Id And Then Click Verify",
                                                    Toast.LENGTH_LONG).show();
                                            //Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_LONG).show();
                                        } else {
                                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                            Toast.makeText(Register.this, "Verification failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }

                    } else{
                    FirebaseAuthException e=(FirebaseAuthException)task.getException();
                    Toast.makeText(Register.this,"Aldready Registered or error"+ e.getMessage(),Toast.LENGTH_LONG).show();


                }
            }
        });
    }
                    @Override
                    public void onClick(View view){
                        if (view == regbtn)
                            btnUserLogin_Click();
                        if(view==textView)
                            startActivity(new Intent(Register.this,LogIn.class));
                    }
                }
