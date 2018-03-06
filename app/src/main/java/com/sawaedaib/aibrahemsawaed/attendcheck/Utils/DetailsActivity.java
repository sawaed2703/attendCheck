package com.sawaedaib.aibrahemsawaed.attendcheck.Utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sawaedaib.aibrahemsawaed.attendcheck.R;

public class DetailsActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Button btnAddWork, btnAddHome;
    TextView tvHome, tvWork;
    FirebaseDatabase database;
    DatabaseReference myRef;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    int PLACE_PICKER_REQUEST = 1;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAddHome = findViewById(R.id.btnAddHome);
        btnAddWork = findViewById(R.id.btnAddWork);
        tvHome = findViewById(R.id.tvHome);
        tvWork = findViewById(R.id.tvWork);

        database= FirebaseDatabase.getInstance();
        myRef= database.getReference("home");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tvHome.setText(value);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnAddHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGeoDataClient = Places.getGeoDataClient(DetailsActivity.this, null);


                // Construct a PlaceDetectionClient.
                mPlaceDetectionClient = Places.getPlaceDetectionClient(DetailsActivity.this, null);

                // TODO: Start using the Places API.

                mGoogleApiClient = new GoogleApiClient
                        .Builder(DetailsActivity.this)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage(DetailsActivity.this, DetailsActivity.this)
                        .build();

                try {
                    startActivityForResult(builder.build(DetailsActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
