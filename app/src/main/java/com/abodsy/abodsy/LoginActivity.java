package com.abodsy.abodsy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.abodsy.abodsy.Main_App.MainActivity;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {


    private static int SPLASH_SCREEN_TIME_OUT=2000;

    /* FIREBASE LOGIN RELATED VARIABLES */
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    /* USER INFO */
    public static String userName;
    public static Uri userPhotoUrl;
    public static String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //This method is used so that your splash activity
        //can cover the entire screen.

        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);

        /* AUTHENTICATION CUSTOM LAYOUT MAKER*/
        final AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.login_screen)
                .setPhoneButtonId(R.id.waste)
                .setGoogleButtonId(R.id.button_gSignIn)
                .build();

        /* Initialize Firebase components */
        mFirebaseAuth = FirebaseAuth.getInstance();

        /* Initializing  Auth Listener
           RUNS EVERY TIME WHEN ACTIVITY OR APP OPENS */
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = firebaseAuth.getCurrentUser();

                /* USER IS LOGGED IN */
                if (user != null) {

                    // USER DATA
                    userName = user.getDisplayName();
                    userPhotoUrl = user.getPhotoUrl();
                    userId = user.getEmail();

                    /* START MAIN APP */
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    /* IF NO USER IS SIGNED IN THEN Create and launch sign-in intent */
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    // ...
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.PhoneBuilder().build()))
                                    .setAuthMethodPickerLayout(customLayout)
                                    .build(), RC_SIGN_IN);
                }
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // USER DATA
                userName = user.getDisplayName();
                userPhotoUrl = user.getPhotoUrl();
                userId = user.getEmail();

                /* START MAIN APP */
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* Attach Listener on activity running */
    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFirebaseAuth.addAuthStateListener(mAuthStateListener);
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }

    /* Detach Listener on activity not running */
    @Override
    protected void onPause() {
        super.onPause();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
            }
        }, SPLASH_SCREEN_TIME_OUT);
        if (mAuthStateListener != null) {
        }
    }

}
