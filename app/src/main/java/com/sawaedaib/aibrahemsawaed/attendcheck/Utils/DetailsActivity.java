package com.sawaedaib.aibrahemsawaed.attendcheck.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sawaedaib.aibrahemsawaed.attendcheck.NavigationDrawerActivity;
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
    String ref = null;

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


        HomeAdress();
        workAdress();

       btnAddWork.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ref = "work";
               database= FirebaseDatabase.getInstance();
               myRef= database.getReference("work");
               addLocation(ref);

           }
       });

       btnAddHome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ref = "home";
               database= FirebaseDatabase.getInstance();
               myRef= database.getReference("home");
               addLocation(ref);


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
       /* btnAddHome.setOnClickListener(new View.OnClickListener() {
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
*/
    }

    private void HomeAdress() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("home");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                tvHome.setText(s);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailsActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void workAdress(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("work");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                tvWork.setText(s);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailsActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addLocation(String ref) {
        mGeoDataClient = Places.getGeoDataClient(DetailsActivity.this, null);


        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(DetailsActivity.this, null);

        // TODO: Start using the Places API.

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tvHome.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                tvHome.setText(message);
                Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                if (place.getName()!=null){

                    saveData(place.getName().toString());

                }


            }
        }
    }

    private void saveData(String s) {
        myRef.setValue(s);

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();

    }

    public void back2main(View view) {
        startActivity(new Intent(this, NavigationDrawerActivity.class));
    }
}
