package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /*private List<Accelerometer> listAcc = new ArrayList<Accelerometer>();*/
    private List<Accelerometer> listAcc = new LinkedList<Accelerometer>();
    private List<Gyroscope> listGyro = new LinkedList<Gyroscope>();
    private List<Compass> listCompass = new LinkedList<Compass>();
    private final int NUMSAMPLE = 100;
    private static final String TAG1 = "MyActivity";
    private final double HIGH_PASS_WEIGHT = 0.98;
    private final double LOW_PASS_WEIGHT = 0.02;
    private final double DELTA_TIME = 0.01;

    private TextView x, y, z;
    private Button button;
    private SensorManager sensorManager;
    private Sensor sensorAcc, sensorGyro, sensorCompass;
    private double CF_angleX, CF_angleY, CF_angleZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = (TextView) findViewById(R.id.textview);
        y = (TextView) findViewById(R.id.textView2);
        z = (TextView) findViewById(R.id.textView3);
//        button = (Button) findViewById(R.id.button);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //access  to the Sensor service to get a specific sensor and register at onResume
        sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorCompass = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

//    @Override
//    public void onClick(View view) {
////        switch (view.getId()){
////            case R.id.button:
////                sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_FASTEST);
////                sensorManager.registerListener(this, sensorGyro, SensorManager.SENSOR_DELAY_FASTEST);
////                sensorManager.registerListener(this, sensorCompass, SensorManager.SENSOR_DELAY_FASTEST);
//                Log.d(TAG1,"Clicked button");
//                break;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, sensorGyro, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, sensorCompass, SensorManager.SENSOR_DELAY_FASTEST);
        Log.d(TAG1,"Start registering sensors");
    }

    private double calcNorm(double x, double y, double z) {
        return Math.sqrt(x*x + y*y + z*z);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Float accx, accy, accz;
        Float gyrox, gyroy, gyroz;
        Float compassx, compassy, compassz;


        //check when you have 100 samples
        if (listAcc.size() >= NUMSAMPLE && listCompass.size() >= NUMSAMPLE && listGyro.size() >= NUMSAMPLE){
            //    analysis code
            float[] avgAcc, avgGyro, avgCom;
            avgAcc = CalcAvgList.calcAvgAcc(listAcc);
            avgGyro = CalcAvgList.calcAvgGyro(listGyro);
            avgCom = CalcAvgList.calcAvgCom(listCompass);

            //Compute Roll(x)
            double roll = Math.toDegrees(Math.atan2(avgAcc[1],avgAcc[2]));
            //Compute Pitch(y)
            double pitch = Math.toDegrees(Math.atan2(-avgAcc[0],Math.sqrt(avgAcc[1] * avgAcc[1] + avgAcc[2] * avgAcc[2])));
            //Compute Yaw(z)

            double magx = avgCom[0] * Math.cos(pitch) + avgCom[2] * Math.sin(pitch);
            double magy = avgCom[0] * Math.sin(roll) * Math.sin(pitch) + avgCom[1] * Math.cos(roll) - avgCom[2] * Math.sin(roll) * Math.cos(pitch);
            double yaw = Math.toDegrees(Math.atan2(-magy,magx));

            //Using Gyroscope for a Complementary Filter
            CF_angleX = HIGH_PASS_WEIGHT * (CF_angleX + avgGyro[0]* DELTA_TIME) + LOW_PASS_WEIGHT * roll;
            CF_angleY = HIGH_PASS_WEIGHT * (CF_angleY + avgGyro[1]* DELTA_TIME) + LOW_PASS_WEIGHT * pitch;
            CF_angleZ = HIGH_PASS_WEIGHT * (CF_angleZ + avgGyro[2]* DELTA_TIME) + LOW_PASS_WEIGHT * yaw;

            x.setText("Roll: " + String.valueOf(CF_angleX));
            y.setText("Pitch: " + String.valueOf(CF_angleY));
            z.setText("Yaw: " + String.valueOf(CF_angleZ));

            Log.d(TAG1,"Changing all textviews");

            listAcc.remove(0);
            listGyro.remove(0);
            listCompass.remove(0);
        }
        else {

            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accx = sensorEvent.values[0];
                accy = sensorEvent.values[1];
                accz = sensorEvent.values[2];

                listAcc.add(new Accelerometer(accx, accy, accz));

            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                gyrox = sensorEvent.values[0];
                gyroy = sensorEvent.values[1];
                gyroz = sensorEvent.values[2];

                listGyro.add(new Gyroscope(gyrox,gyroy,gyroz));
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                compassx = sensorEvent.values[0];
                compassy = sensorEvent.values[1];
                compassz = sensorEvent.values[2];

                listCompass.add(new Compass(compassx,compassy,compassz));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensorAcc);
        sensorManager.unregisterListener(this, sensorGyro);
        sensorManager.unregisterListener(this, sensorCompass);
        Log.d(TAG1,"Stop registering sensors");
    }
}