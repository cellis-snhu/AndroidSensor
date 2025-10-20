package com.example.sensoractivity;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // setup sensor manager and accelerometer sensor
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize sensorManager and get accelerometer
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register listener onResume to start capturing data when activity resumes
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister listener onPause to save power
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Read accelerometer values for each axis
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // store accelerometer data in string
            String accelerometerData = String.format("X: %.2f\nY: %.2f\nZ: %.2f", x, y, z);

            // Update the TextView to display accelerometer data
            TextView accelerometerTextView = findViewById(R.id.accelerometerDataText);
            accelerometerTextView.setText(accelerometerData);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}