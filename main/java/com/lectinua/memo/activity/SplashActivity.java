package com.lectinua.memo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.lectinua.memo.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        anim = AnimationUtils.loadAnimation(this,R.anim.splash);

        LinearLayout view = findViewById(R.id.view_splash);
        view.startAnimation(anim);

        Handler handler = new Handler();
        handler.postDelayed(new Splash(), 1500);
    }

    public class Splash implements Runnable{
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, PermissionActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
