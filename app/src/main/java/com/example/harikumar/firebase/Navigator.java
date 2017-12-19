package com.example.harikumar.firebase;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Navigator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
FirebaseAuth firebaseAuth;
DatabaseReference mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        firebaseAuth=FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
       // String timerData =  getIntent().getStringExtra("EXTRA_TIMERDATA");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        firebaseAuth=FirebaseAuth.getInstance();
        int id = item.getItemId();
        String timerData =  getIntent().getStringExtra("EXTRA_TIMERDATA");

     /*   locationrequest = LocationRequest.create();
        locationrequest.setInterval(10000);
        locationclient.requestLocationUpdates(locationrequest,new com.google.android.gms.location.LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, "Last Known Location :" + location.getLatitude() + "," + location.getLongitude());
            }
        });
*/
        if (id == R.id.nav_trackbus) {
            Toast.makeText(Navigator.this,"navigate track ", Toast.LENGTH_LONG).show();
            finish();
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            mdb= FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("bus");
            mdb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   String bus = dataSnapshot.getValue().toString();
                    Intent intent=new Intent(Navigator.this,MapsActivity.class);
                    intent.putExtra("EXTRA_TIMERDATA",bus);
                    startActivity(intent);
                    //final dlon= Double.parseDouble(lon);
                   // Toast.makeText(MapsActivity.this, bus, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

           // startActivity(new Intent(Navigator.this,MapsActivity.class));
            // Handle the camera action
        }  else if (id == R.id.nav_bookevebus) {
            finish();
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            mdb= FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("bus");
            mdb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String bus = dataSnapshot.getValue().toString();
                    Intent intent=new Intent(Navigator.this,StudentBooking.class);
                    intent.putExtra("EXTRA_TIMERDATA",bus);
                    startActivity(intent);
                    //final dlon= Double.parseDouble(lon);
                    // Toast.makeText(MapsActivity.this, bus, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Toast.makeText(Navigator.this,"book eve bus ", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_editprofile) {
            Toast.makeText(Navigator.this,"edit profile ", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(Navigator.this,Profile.class));
        }  else if (id == R.id.nav_signout) {
Toast.makeText(Navigator.this,"sign out", Toast.LENGTH_LONG).show();
firebaseAuth.signOut();
finish();
startActivity(new Intent(Navigator.this,LogIn.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
