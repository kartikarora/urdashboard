package me.kartikarora.urdashboard.activities;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import me.kartikarora.urdashboard.R;

public class QRScanActivity extends AppCompatActivity {

    private QRCodeReaderView qrView;
    private boolean torch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        qrView = findViewById(R.id.qr_view);
        qrView.setQRDecodingEnabled(true);
        qrView.setAutofocusInterval(300L);
        qrView.setBackCamera();

        /*ImageButton flashlightToggleButton = findViewById(R.id.toggle_flashlight_button);
        flashlightToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                torch = !torch;
                qrView.setTorchEnabled(torch);
            }
        });*/

        qrView.setOnQRCodeReadListener(new QRCodeReaderView.OnQRCodeReadListener() {
            @Override
            public void onQRCodeRead(String text, PointF[] points) {
                startActivity(new Intent(QRScanActivity.this, OnboardingActivity.class)
                        .putExtra(getString(R.string.pref_udacity_token), text));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        qrView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrView.stopCamera();
    }
}
