package com.example.gminemini.chi_chisticks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RotateAnimation anim = new RotateAnimation(0f, 15f, 50f, 50f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(300);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);


        final ImageView chich = (ImageView) findViewById(R.id.chichi);
        chich.startAnimation(animation);
    }
}
