package com.deepakshankar.securefit;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final ImageView appImage = (ImageView) findViewById(R.id.splashImage);
        final Animation roatate = AnimationUtils.loadAnimation(getBaseContext(), R.anim.splach_screen_animator);
        final Animation fadeOut = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        appImage.startAnimation(roatate);

        roatate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                appImage.startAnimation(fadeOut);
                finish();

                Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainIntent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
