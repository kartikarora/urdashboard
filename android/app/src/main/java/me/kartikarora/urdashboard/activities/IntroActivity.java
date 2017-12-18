package me.kartikarora.urdashboard.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.utils.HelperUtils;


/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.activities
 * Project : UR Dashboard
 * Date : 2/6/17
 */

public class IntroActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1001;
    private static final int RC_QR_SCAN = 1002;
    private static final int RC_CAMERA = 1003;
    private static final String TAG = IntroActivity.class.getName();
    private View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        rootView = findViewById(android.R.id.content);

        HelperUtils.getInstance().changeStatusBarColor(IntroActivity.this, R.color.primary);

        AppCompatButton qrScanButton = findViewById(R.id.scan_qr_button);
        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR();
            }
        });
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
}
