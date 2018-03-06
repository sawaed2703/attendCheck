package com.sawaedaib.aibrahemsawaed.attendcheck.Utils;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class PlacesActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    ImageView imageView;
    Button btnAddNewLocation, btnSchedule,btnBack;
    private TextView mPlaceDetailsText;
    TextView tvPlace;
    private TextView mPlaceAttribution;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private GoogleApiClient mGoogleApiClient;
    int PLACE_PICKER_REQUEST = 1;
    String place= null;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    FirebaseDatabase database;
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAddNewLocation = findViewById(R.id.btnAddNewLocation);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnBack = findViewById(R.id.btnBack);
        tvPlace = findViewById(R.id.tvPlace);
        imageView = findViewById(R.id.placeView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        database= FirebaseDatabase.getInstance();
        myRef= database.getReference("work");
        //getRequestQueue Saved place Form DataBase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tvPlace.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                tvPlace.setText(message);

            }
        });


        // Construct a GeoDataClient.
        btnAddNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGeoDataClient = Places.getGeoDataClient(PlacesActivity.this, null);


                // Construct a PlaceDetectionClient.
                mPlaceDetectionClient = Places.getPlaceDetectionClient(PlacesActivity.this, null);

                // TODO: Start using the Places API.

                mGoogleApiClient = new GoogleApiClient
                        .Builder(PlacesActivity.this)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage(PlacesActivity.this, PlacesActivity.this)
                        .build();

                try {
                    startActivityForResult(builder.build(PlacesActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back2Main();
            }
        });


    }

    private void back2Main() {
        startActivity(new Intent(PlacesActivity.this,NavigationDrawerActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                tvPlace.setText(place.getName());
                if (place.getName()!=null){

                }

                getImage(place.getName().toString());
                saveData(place.getName().toString());
            }
        }
    }

    private void getImage(String name) {
        Intent intent;

        intent = new Intent(Intent.ACTION_WEB_SEARCH);
        String term = (String) name;
        intent.putExtra(SearchManager.QUERY, term);
        //  startActivity(intent);


        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://www.google.co.il/search?q="+name+"&source=lnms&tbm=isch&sa=X&ved=0ahUKEwj2-__y0tvYAhXQ2aQKHUTDBBAQ_AUICigB&biw=1301&bih=678#imgrc=GL6RVo_km2-cYM:"));
        //startActivity(intent);
    }
// here q='My_search_text'

    private void saveData(String s){
        myRef.setValue(s);
        tvPlace.setText(s);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
