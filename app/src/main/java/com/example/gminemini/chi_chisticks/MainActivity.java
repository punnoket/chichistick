package com.example.gminemini.chi_chisticks;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private SensorInfo sensorInfo;
    private Handler hdr;
    private ImageView chich;
    private Animation animation;
    private static final int shake_threshold = 12;
    private Random random;
    private int ran;
    private boolean shown_dialog = false;
    private int count = 0;

    class SensorInfo {
        float accX, accY, accZ;
        float ambTemp;
        float graX, graY, graZ;
        float gyrX, gyrY, gyrZ;
        float light;
        float laccX, laccY, laccZ;
        float magX, magY, magZ;
        float orX, orY, orZ;
        float pressure;
        float proximity;
        float humid;
        float rotX, rotY, rotZ;
        float temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorInfo = new SensorInfo();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        RotateAnimation anim = new RotateAnimation(0f, 15f, 50f, 50f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(1);
        anim.setDuration(300);

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);

        chich = (ImageView) findViewById(R.id.chichi);

        hdr = new Handler();
        pollTask = new Runnable() {
            public void run() {
                startSemsi();
                hdr.postDelayed(pollTask, 100);
            }
        };

        hdr.postDelayed(pollTask, 100);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            sensorInfo.accX = event.values[0];
            sensorInfo.accY = event.values[1];
            sensorInfo.accZ = event.values[2];

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private Runnable pollTask;

    private void startSemsi() {
        if ((Math.abs(sensorInfo.accX) > shake_threshold) ||
                (Math.abs(sensorInfo.accY) > shake_threshold) ||
                (Math.abs(sensorInfo.accZ) > shake_threshold)) {
            chich.startAnimation(animation);
            count++;
            if (count % 3 == 0) {
                chich.clearAnimation();
                chich.animate().cancel();
                randomSemsi();
            }
        }
    }

    private void randomSemsi() {
        random = new Random();
        ran = random.nextInt(Predict.predicts.length);
        String predict = Predict.predicts[ran];
        if (!shown_dialog) {
            shown_dialog = true;
            final AlertDialog.Builder viewDialog = new AlertDialog.Builder(this);
            viewDialog.setIcon(android.R.drawable.btn_star_big_on);
            viewDialog.setTitle("คำทำนาย");
            viewDialog.setMessage(predict);
            viewDialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            shown_dialog = false;
                            chich.setAnimation(animation);
                        }
                    });
            viewDialog.show();
        }//end if

    }
}