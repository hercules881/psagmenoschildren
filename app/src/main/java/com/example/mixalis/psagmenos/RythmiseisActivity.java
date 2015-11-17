package com.example.mixalis.psagmenos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import Misc.Preferences;

/**
 * Created by mixalis on 13/11/2015.
 */
public class RythmiseisActivity extends Activity {
    Switch soundSwitch;
    public final static String SOUNDSETTINGS = "soundsettings";
    public final static String ISSOUNDENABLED = "issoundenabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rythmiseis);
        soundSwitch = (Switch)findViewById(R.id.soundSwitch);
        boolean isSoundEnabled = (boolean) Preferences.get(this,SOUNDSETTINGS,ISSOUNDENABLED, true);
        soundSwitch.setChecked(isSoundEnabled);

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


    }
}
