package com.example.mixalis.psagmenos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

import Database.ExternalDbOpenHelper;
import Misc.ChangeLanguageListener;
import Misc.Preferences;

/**
 * Created by mixalis on 13/11/2015.
 */
public class RythmiseisActivity extends Activity {
    Switch soundSwitch;
    public final static String SOUNDSETTINGS = "soundsettings";
    public final static String ISSOUNDENABLED = "issoundenabled";
    public static ChangeLanguageListener changeLanguageListener;
    Switch languageSwitch;
    public static final String LANGUAGE_KEY = "lang";
    TextView languageText;
    static TextView soundText;
int mousiki=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String currentLanguage = preferences.getString(LANGUAGE_KEY,Locale.getDefault().getLanguage().startsWith("el")?"el": "en");

        Resources res = RythmiseisActivity.this.getResources();
        // Change locale settings in the app.
        Locale locale = new Locale(currentLanguage);
        setContentView(R.layout.rythmiseis);
        soundSwitch = (Switch)findViewById(R.id.soundSwitch);
        soundText = (TextView)findViewById(R.id.sound_on_off);
        boolean isSoundEnabled = (boolean) Preferences.get(this,SOUNDSETTINGS,ISSOUNDENABLED, true);
        soundSwitch.setChecked(isSoundEnabled);
        soundSwitch.setTextOff("");
        soundSwitch.setTextOn("");

        languageSwitch = (Switch) findViewById(R.id.languageSwitch);
        languageSwitch.setChecked(currentLanguage.equals("el"));
        languageSwitch.setTextOn("");
        languageSwitch.setTextOff("");
        languageText = (TextView) findViewById(R.id.language);
        languageText.setText(currentLanguage.equals("el")?"Ελληνικά":"English");
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSwitchOn) {
                if(isSwitchOn){
                    MainActivity.mediaPlayer.start();
                }
                else{
                    MainActivity.mediaPlayer.pause();
                }

                Preferences.set(RythmiseisActivity.this,SOUNDSETTINGS,ISSOUNDENABLED, isSwitchOn);
            }
        });


        languageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Context context = RythmiseisActivity.this;
                SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();


                Resources res = context.getResources();
                // Change locale settings in the app.
                Locale locale = new Locale(!b?"en":"el");
                languageText.setText(b?"Ελληνικά":"English");
                Locale.setDefault(locale);
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.locale = locale;
                res.updateConfiguration(conf, dm);
                res.getDisplayMetrics();


                editor.putString(LANGUAGE_KEY, !b ? "en" : "el");
                editor.apply();
                changeLanguageListener.onLanguageChange(b);
                soundText.setText(R.string.sound);

            }
        });

    }
    public static void setOnChangeLanguageListener(ChangeLanguageListener changeLanguageListenerr){
        changeLanguageListener = changeLanguageListenerr;
    }

    @Override
    protected void onResume() {

        boolean isSoundEnabled;
        isSoundEnabled = (boolean) Preferences.get(this, RythmiseisActivity.SOUNDSETTINGS, RythmiseisActivity.ISSOUNDENABLED, true);
        if(isSoundEnabled && !MainActivity.mediaPlayer.isPlaying())
            MainActivity.mediaPlayer.start();




        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (mousiki!=1 && !this.isFinishing()) {
            MainActivity.mediaPlayer.pause();
            // MainActivity.mediaPlayer.stop();

        }
        mousiki=0;
    }
}
