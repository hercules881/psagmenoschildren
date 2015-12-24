package com.example.mixalis.psagmenos;

/**
 * Created by mixalis on 10/9/2015.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2600;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getIntent().getBooleanExtra("EXIT", false)) {
                                //tou stelnw to exit apo to seriaconsole ativitie gia na kleisei

            finish();
        }
        ImageView img_animation = (ImageView) findViewById(R.id.imgLogo);

        /*TranslateAnimation animation = new TranslateAnimation(200.0f, 0.0f,
                0.0f, 0.0f);
        animation.setDuration(2500);
        animation.setRepeatCount(5);
        animation.setRepeatMode(2);
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);
        //ImageView myImageView= (ImageView)findViewById(R.id.imgLogo);
        //Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        //myImageView.startAnimation(myFadeInAnimation); //Set animation to your ImageView

*/
        final Handler Handler = new Handler();
        Handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                //Intent i = new Intent(SplashScreen.this, SerialConsoleActivity.class);
                // startActivity(i);

                // close this activity


                Intent myIntent = new Intent(SplashScreen.this, MainActivity.class);  //alazei activity
                SplashScreen.this.startActivity(myIntent);
                finish();


            }
        }, SPLASH_TIME_OUT);
    }




}