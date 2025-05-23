package com.wul4.paythunder.gestorInventario.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.wul4.paythunder.gestorInventario.R;

import java.util.List;

public class ScannerActivity extends AppCompatActivity {
    public static final String EXTRA_SCAN = "EXTRA_SCAN";
    private static final int REQUEST_CAMERA = 200;

    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Pedir permiso si hace falta
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ Manifest.permission.CAMERA }, REQUEST_CAMERA);
        } else {
            initScanner();
        }
    }

    private void initScanner() {
        setContentView(R.layout.activity_scanner);
        barcodeView = findViewById(R.id.barcode_scanner);
        beepManager = new BeepManager(this);

        // Arrancamos el escaneo continuo pero lo detenemos tras el primer resultado
        barcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result.getText() != null) {
                    beepManager.playBeepSoundAndVibrate();
                    Intent data = new Intent();
                    data.putExtra(EXTRA_SCAN, result.getText());
                    setResult(RESULT_OK, data);
                    finish();
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
                // no usamos
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (barcodeView != null) {
            barcodeView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (barcodeView != null) {
            barcodeView.pause();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initScanner();
            } else {
                Toast.makeText(this,
                        "Necesito permiso de cámara para escanear",
                        Toast.LENGTH_LONG).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }
}
