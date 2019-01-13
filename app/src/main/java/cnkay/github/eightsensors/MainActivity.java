package cnkay.github.eightsensors;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/*
 * Sensors Int Values
 * ------------------
 * TYPE_ALL = -1
 * TYPE_ACCELEROMETER = 1
 * TYPE_AMBIENT_TEMPERATURE = 13
 * TYPE_GRAVITY = 9
 * TYPE_GYROSCOPE = 4
 * TYPE_LIGHT = 5
 * TYPE_MAGNETIC_FIELD = 2
 * TYPE_PRESSURE = 6
 * TYPE_PROXIMITY = 8
 * TYPE_ORIENTATION = DEPRECATED AT API 16(JellyBean 4.1)!
 *
 * */
public class MainActivity extends Activity implements SensorEventListener {
    TextView txtAcc, txtGyro, txtLight, txtMag, txtPre, txtProx, txtTemp, txtGrav;
    TextView vwAcc, vwGyro, vwLight, vwMag, vwPre, vwProx, vwTemp, vwGrav;
    Integer[] textIDs = {R.id.textAccel, R.id.textGyro, R.id.textLight, R.id.textMagnet, R.id.textPressure, R.id.textProx, R.id.textTemp, R.id.textGrav};
    Integer[] viewIDs = {R.id.viewAccel, R.id.viewGyro, R.id.viewLight, R.id.viewMagnet, R.id.viewPressure, R.id.viewProx, R.id.viewTemp, R.id.viewGrav};
    TextView[] texts = {txtAcc, txtGyro, txtLight, txtMag, txtPre, txtProx, txtTemp, txtGrav};
    TextView[] views = {vwAcc, vwGyro, vwLight, vwMag, vwPre, vwProx, vwTemp, vwGrav};
    SensorManager sensorManager;
    Integer[] sensorArray = {1, 4, 5, 2, 6, 8, 13, 9};
    Map<Integer, Sensor> sensorMap = new HashMap<>();
    String newValue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createComponents();
    }

    public void createComponents() {
        for (int i = 0; i < textIDs.length; i++) {
            texts[i] = findViewById(textIDs[i]);
            views[i] = findViewById(viewIDs[i]);
        }
        getSensors();
    }

    public void getSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        for (int i = 0; i < sensorArray.length; i++) {
            if (sensorManager.getDefaultSensor(sensorArray[i]) != null) {
                sensorMap.put(sensorArray[i], sensorManager.getDefaultSensor(sensorArray[i]));
            } else {
                texts[i].setVisibility(View.GONE);//Like it doesn't exist!
                views[i].setVisibility(View.GONE);//Like it doesn't exist!
                sensorMap.remove(sensorArray[i]);
            }
        }
        for (Map.Entry<Integer, Sensor> entry : sensorMap.entrySet()) {
            sensorManager.registerListener(this, entry.getValue(), SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void sensorChangedUI(Integer type, float[] newValues) {
        for (int i = 0; i < sensorArray.length; i++) {
            if (sensorArray[i] == type) {
                if (newValues.length > 1)
                    newValue = "X : " + newValues[0] + " Y : " + newValues[1] + " Z : " + newValues[2];
                else
                    newValue = newValues[0] + "";
                texts[i].setText(newValue);
                newValue="";
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorChangedUI(event.sensor.getType(), event.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}