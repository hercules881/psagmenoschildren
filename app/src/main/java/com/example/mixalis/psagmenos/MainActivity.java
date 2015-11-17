package com.example.mixalis.psagmenos;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.sql.SQLException;

import Database.ExternalDbOpenHelper;
import Misc.Preferences;

import static android.media.MediaPlayer.*;
import static com.example.mixalis.psagmenos.R.*;

public class MainActivity extends Activity {
    private TextView enarxi;
    public static MediaPlayer mediaPlayer;
    ProgressBar progressBarq;




    @Override
    public void onBackPressed()
    {
        // super.onBackPressed();
        mediaPlayer.stop();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        mediaPlayer = create(this, raw.back1);
        boolean isSoundEnabled = (boolean) Preferences.get(this, RythmiseisActivity.SOUNDSETTINGS, RythmiseisActivity.ISSOUNDENABLED, true);
        if(isSoundEnabled)
        mediaPlayer.start();

        ExternalDbOpenHelper dbHelper = new ExternalDbOpenHelper(this);
        try {
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        enarxi = (TextView) findViewById(id.enarxi);
        enarxi.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                Intent myIntent = new Intent(MainActivity.this, EnarxiActivity.class);
                MainActivity.this.startActivity(myIntent);





            }

        });

        findViewById(R.id.title2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, RythmiseisActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;


    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
