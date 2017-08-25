package me.kartikarora.udacityreviewer.activities;

import android.content.Intent;
import android.graphics.PointF;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.util.List;

import me.kartikarora.potato.Potato;
import me.kartikarora.udacityreviewer.R;

public class QRScanActivity extends AppCompatActivity {

    private QRCodeReaderView qrView;
    private boolean torch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        qrView = findViewById(R.id.qr_view);
        qrView.setQRDecodingEnabled(true);
        qrView.setAutofocusInterval(1000L);
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
                Potato.potate(QRScanActivity.this).Preferences()
                        .putSharedPreference(getString(R.string.pref_udacity_token), text);
                startActivity(new Intent(QRScanActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
