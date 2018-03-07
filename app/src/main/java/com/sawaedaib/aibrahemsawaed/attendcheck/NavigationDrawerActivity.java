package com.sawaedaib.aibrahemsawaed.attendcheck;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sawaedaib.aibrahemsawaed.attendcheck.Utils.DetailsActivity;
import com.sawaedaib.aibrahemsawaed.attendcheck.Utils.PlacesActivity;
import com.sawaedaib.aibrahemsawaed.attendcheck.Utils.ScheduleActivity;

import java.util.Date;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvCourseName, tvDateNow, tvStartCourse, tvEndCourse, tvCourseDate;
    FirebaseDatabase database;
    DatabaseReference getCourseName,getMyCourseDate ,getMyCourseEndTime,getCourseStartTime;
    Button btnCheck;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //find :
        tvCourseName = findViewById(R.id.tvName);
        tvCourseDate = findViewById(R.id.tvCourseDate);
        tvDateNow = findViewById(R.id.tvDateNow);
        tvStartCourse = findViewById(R.id.tvStartCourse);
        tvEndCourse = findViewById(R.id.tvEndCourse);
        btnCheck = findViewById(R.id.btnCheck);

        @SuppressLint({"NewApi", "LocalSuppress"}) Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd / MMMM / yyyy");
        String formattedDate = df.format(c);

        tvDateNow.setText(formattedDate);

        checkDate();


        //Date d = new Date();
        //CharSequence s  = DateFormat.format("d/m/y ", d.getTime());

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationDrawerActivity.this,CheckLocationActivity.class));
            }
        });



        database = FirebaseDatabase.getInstance();
        getCourseName = database.getReference("Name");
        getMyCourseDate = database.getReference("courseDate");
        getMyCourseEndTime = database.getReference("endTime");
        getCourseStartTime = database.getReference("startTime");




        courseInfo();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkDate() {
        //TODO : check current date and course date

        String month = null;

        int monthNum = 0;
        switch (month){

            case "January" : monthNum =1;
            break;
            case "February":monthNum =2;
            break;
            case "March " : monthNum =3;
                break;
            case "April" : monthNum =4;
                break;
            case "May" : monthNum =5;
                break;
            case "June" : monthNum =6;
                break;
            case "July " : monthNum =7;
                break;
            case "August" : monthNum =8;
                break;

            case "September" : monthNum =9;
                break;
            case "October" : monthNum =10;
                break;
            case "November " : monthNum =11;
                break;
            case "December" : monthNum =12;
                break;

        }
    }


    private void courseInfo() {

        getMyCourseDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                tvCourseDate.setText(s);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getMyCourseEndTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                tvEndCourse.setText(s);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getCourseStartTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                tvStartCourse.setText(s);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getCourseName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                tvCourseName.setText(s);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NavigationDrawerActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
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
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
        int id = item.getItemId();

        if (id == R.id.nav_maps) {
            // Handle the camera action
            startActivity(new Intent(this,ScheduleActivity.class));


        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this,MapsActivity.class));


        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this,PlacesActivity.class));


        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
