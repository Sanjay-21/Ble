package com.exam.ble;

import static com.exam.ble.Constants.REQUEST_ENABLE_BT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.exam.ble.central.CentralActivity;
import com.exam.ble.peripheral.PeripheralActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnCentral, btnPeripheral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE
        };

//        if (!hasPermissions(this, PERMISSIONS)) {
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }
        Intent ble_enable_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
           // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
           ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            // to handle the case where the user grants the permission. See the documentation
           // for ActivityCompat#requestPermissions for more details.
           return;
        }
        startActivityForResult(ble_enable_intent, REQUEST_ENABLE_BT);
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initView() {
        btnCentral = (Button) findViewById(R.id.btnCentral);
        btnPeripheral = (Button) findViewById(R.id.btnPeripheral);

        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CentralActivity.class));
            }
        });

        btnPeripheral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // public void onButtonClick(View view) {  newly added
                    EditText editText = (EditText) findViewById(R.id.editText);
                    String textToPass = editText.getText().toString();

                    // start the SecondActivity
                    Intent intent = new Intent(MainActivity.this, PeripheralActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, textToPass);
                    startActivity(intent);
                //}newly added
                //startActivity(new Intent(MainActivity.this, PeripheralActivity.class));
            }
        });
    }

}