package com.sawaedaib.aibrahemsawaed.attendcheck.Utils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.internal.zzdym;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.sawaedaib.aibrahemsawaed.attendcheck.NavigationDrawerActivity;
import com.sawaedaib.aibrahemsawaed.attendcheck.R;
import com.sawaedaib.aibrahemsawaed.attendcheck.SplashActivity;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    Button btnSignOut, btnSignIn;
    FirebaseUser user;
     FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignOut = findViewById( R.id.sign_out);
        btnSignIn = findViewById( R.id.sign_in);

        mAuth = FirebaseAuth.getInstance(FirebaseApp.initializeApp(this));

        user =  new FirebaseUser() {
            @NonNull
            @Override
            public String getUid() {
                return null;
            }

            @NonNull
            @Override
            public String getProviderId() {
                return null;
            }

            @Override
            public boolean isAnonymous() {
                return false;
            }

            @Nullable
            @Override
            public List<String> getProviders() {
                return null;
            }

            @NonNull
            @Override
            public List<? extends UserInfo> getProviderData() {
                return null;
            }

            @NonNull
            @Override
            public FirebaseUser zzaq(@NonNull List<? extends UserInfo> list) {
                return null;
            }

            @Override
            public FirebaseUser zzcf(boolean b) {
                return null;
            }

            @NonNull
            @Override
            public FirebaseApp zzbre() {
                return null;
            }

            @Nullable
            @Override
            public String getDisplayName() {
                return null;
            }

            @Nullable
            @Override
            public Uri getPhotoUrl() {
                return null;
            }

            @Nullable
            @Override
            public String getEmail() {
                return null;
            }

            @Nullable
            @Override
            public String getPhoneNumber() {
                return null;
            }

            @NonNull
            @Override
            public zzdym zzbrf() {
                return null;
            }

            @Override
            public void zza(@NonNull zzdym zzdym) {

            }

            @NonNull
            @Override
            public String zzbrg() {
                return null;
            }

            @NonNull
            @Override
            public String zzbrh() {
                return null;
            }

            @Nullable
            @Override
            public FirebaseUserMetadata getMetadata() {
                return null;
            }

            @Override
            public boolean isEmailVerified() {
                return false;
            }
        };

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.sign_out) {
                    AuthUI.getInstance()
                            .signOut(LoginActivity.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    // user is now signed out
                                    startActivity(new Intent(LoginActivity.this, NavigationDrawerActivity.class));
                                    finish();
                                }
                            });
                }

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.PhoneBuilder().build(),
                                        new AuthUI.IdpConfig.GoogleBuilder().build()
                                ))
                                .build(),
                        RC_SIGN_IN);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
            startActivity(new Intent(this, SplashActivity.class)
                    .putExtra("my_token", idpResponse.getIdpToken()));
        }
    }

     //   if (requestCode == RC_SIGN_IN) {
     //       IdpResponse response = IdpResponse.fromResultIntent(data);
//
     //       // Successfully signed in
     //       if (resultCode == RESULT_OK) {
     //           startActivity(LoginActivity.createIntent(this, response));
     //           finish();
     //           // Sign in failed
     //           if (response == null) {
     //               // User pressed back button
     //               Toast.makeText(this, "sign_in_cancelled", Toast.LENGTH_LONG).show();
     //               return;
     //           }
//
     //           if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
     //               Toast.makeText(this, "no_internet_connection", Toast.LENGTH_LONG).show();
     //               return;
     //           }
//
     //           Toast.makeText(this, "unknown_error", Toast.LENGTH_LONG).show();
     //           Log.e("", "Sign-in error: ", response.getError());
     //       }
     //   }



}
