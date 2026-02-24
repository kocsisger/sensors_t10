package hu.unideb.inf.sensors2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor lightSensor;
    SensorEventListener lightSensorEventListener;

    TextView sensorsTextView;
    TextView lightSensorTextView;

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

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSensorEventListener = new LightSensorEventListener();

        sensorsTextView = findViewById(R.id.sensorsTextView);
        lightSensorTextView = findViewById(R.id.lightSensorTextView);
        //sensorsTextView.setMovementMethod(new ScrollingMovementMethod());
        sensorsTextView.setText(sensorList.toString());
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightSensorEventListener,lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightSensorEventListener);
    }

    private class LightSensorEventListener implements SensorEventListener {
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Log.d("SENSOR_TEST", "Lux: " + sensorEvent.values[0]);
            lightSensorTextView.setText("Lux: " + sensorEvent.values[0]);
        }
    }
}