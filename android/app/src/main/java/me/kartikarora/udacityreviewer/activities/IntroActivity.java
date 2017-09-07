package me.kartikarora.udacityreviewer.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.models.FirebaseKeys;
import me.kartikarora.udacityreviewer.utils.HelperUtils;


/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.activities
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class IntroActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1001;
    private static final int RC_QR_SCAN = 1002;
    private static final int RC_CAMERA = 1003;
    private static final String TAG = IntroActivity.class.getName();
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        rootView = findViewById(android.R.id.content);

        HelperUtils.getInstance().changeStatusBarColor(IntroActivity.this, R.color.primary);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();

        AppCompatButton signInButton = findViewById(R.id.signin_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        AppCompatButton qrScanButton = findViewById(R.id.scan_qr_button);
        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void scanQR() {
        if (ActivityCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(new Intent(IntroActivity.this, QRScanActivity.class), RC_QR_SCAN);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(IntroActivity.this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(IntroActivity.this)
                        .setMessage("Camera permission is required to scan and decode the QR code")
                        .setPositiveButton(android.R.string.ok, null)
                        .create().show();
            }
            ActivityCompat.requestPermissions(IntroActivity.this, new String[]{Manifest.permission.CAMERA}, RC_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_CAMERA && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(IntroActivity.this, QRScanActivity.class));
            } else {
                Snackbar.make(rootView, "Cannot scan QR, need permission to use camera", Toast.LENGTH_SHORT)
                        .setAction("Grant", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scanQR();
                            }
                        }).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final ProgressDialog dialog = new ProgressDialog(IntroActivity.this);
                            dialog.setMessage(getString(R.string.please_wait));
                            dialog.show();
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference();
                                    DatabaseReference key = ref.child("keys/" + user.getUid());
                                    key.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            FirebaseKeys keys = dataSnapshot.getValue(FirebaseKeys.class);
                                            String apikey = keys.getApikey();
                                            dialog.hide();
                                            dialog.dismiss();
                                            startActivity(new Intent(IntroActivity.this, OnboardingActivity.class)
                                                    .putExtra(getString(R.string.pref_udacity_token), apikey));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    FirebaseAuth.getInstance().signOut();
                                }
                            }, 6000);

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(IntroActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
