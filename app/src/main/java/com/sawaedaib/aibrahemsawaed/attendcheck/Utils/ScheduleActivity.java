package com.sawaedaib.aibrahemsawaed.attendcheck.Utils;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sawaedaib.aibrahemsawaed.attendcheck.R;

public class ScheduleActivity extends AppCompatActivity {
    String ref = null;
    FirebaseDatabase database;
    DatabaseReference couseName, startTime, endTime, courseDate;
    EditText etCousreName, etStart,etEnd;
    TextView tvCousreName, tvStart, tvEnd, tvDate;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etCousreName = findViewById(R.id.etCousreName);
        etStart = findViewById(R.id.etStart);
        etEnd = findViewById(R.id.etEnd);
        tvCousreName = findViewById(R.id.tvCousreName);
        tvStart = findViewById(R.id.tvStart);
        tvEnd = findViewById(R.id.tvEnd);
        tvDate = findViewById(R.id.tvDate);
        btnSave = findViewById(R.id.btnSave);

        ref = "courseName";
        database= FirebaseDatabase.getInstance();
        couseName= database.getReference("Name");
        startTime= database.getReference("startTime");
        endTime= database.getReference("endTime");
        courseDate= database.getReference("courseDate");

        saveData();
        showData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void showData() {
        courseDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String v = dataSnapshot.getValue(String.class);
                tvDate.setText(v);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        endTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String v = dataSnapshot.getValue(String.class);
                tvEnd.setText(v);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        startTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String v = dataSnapshot.getValue(String.class);
                tvStart.setText(v);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        couseName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String v = dataSnapshot.getValue(String.class);
                tvCousreName.setText(v);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveData() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cousreNameText = etCousreName.getText().toString();
                String startTimeC = etStart.getText().toString();
                String endTimeC = etEnd.getText().toString();
                String Date = "06/03/2018";
                couseName.setValue(cousreNameText);
                startTime.setValue(startTimeC);
                endTime.setValue(endTimeC);
                courseDate.setValue(Date);

            }
        });

    }

}
