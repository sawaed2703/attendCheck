package com.sawaedaib.aibrahemsawaed.attendcheck.Utils;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.sawaedaib.aibrahemsawaed.attendcheck.R;
import com.sawaedaib.aibrahemsawaed.attendcheck.SplashActivity;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
