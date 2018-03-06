package com.sawaedaib.aibrahemsawaed.attendcheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.sawaedaib.aibrahemsawaed.attendcheck.Utils.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    Button btnStart, btnLocation;
    TextView tvWelcome;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btnStart = findViewById(R.id.btnStart);
        btnLocation = findViewById(R.id.btnLocation);
        tvWelcome = findViewById(R.id.tvWelcome);
        mAuth = FirebaseAuth.getInstance();

        try {
            getUser();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this,NavigationDrawerActivity.class));
            }
        });

    }

    private void getUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "user nulllllllll", Toast.LENGTH_SHORT).show();
        } else {
            tvWelcome.setText(currentUser.getDisplayName());

            //TODO : save userName

            // User logged in
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
