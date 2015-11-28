package com.example.mixalis.psagmenos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import Database.ExternalDbOpenHelper;
import Misc.Preferences;

import static android.media.MediaPlayer.create;


/**
 * Created by mixalis on 13/11/2015.
 */
public class EnarxiActivity extends Activity {
    ListView listView;
    String[] katigories;
int mousiki=0;
    private ImageButton math;
    private ImageButton alphavita;
    private ImageButton glwssa;
    private ImageButton xrwmata;
    private ImageButton zwgrafiki;

    public EnarxiActivity() {
    }

    @ Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enarxi);
        initCats();


    // if  ( MainActivity.mediaPlayer.isPlaying())


        /*CustomAdapter adapter = new CustomAdapter(this, katigories);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);*/


       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent myIntent = new Intent(EnarxiActivity.this, GameActivity.class);
                // EnarxiActivity.this.startActivity(myIntent);
                if (position == 4) {
                    Intent myIntent = new Intent(EnarxiActivity.this, PaintActivity.class);
                    EnarxiActivity.this.startActivity(myIntent);
                }
                if (position != 4) {
                    Intent i = new Intent(getApplicationContext(), GameActivity.class);
                    // i.putExtra("epelexes","Γεωγραφία");

                    i.putExtra("firstName", katigories[position]);
                    i.putExtra("isalphabete", katigories[position].equals(EnarxiActivity.this.getString(R.string.alphabete)));
                    i.putExtra("iscolor", katigories[position].equals(EnarxiActivity.this.getString(R.string.colors)));
                    startActivity(i);
                }
            }
        });*/


        ////////////////////////////////////////////////////////////
        math = (ImageButton) findViewById(R.id.math);
        math.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

mousiki=1;
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                i.putExtra("firstName", katigories[0]);
                i.putExtra("isalphabete", katigories[0].equals(EnarxiActivity.this.getString(R.string.alphabete)));
                i.putExtra("iscolor", katigories[0].equals(EnarxiActivity.this.getString(R.string.colors)));
                startActivity(i);
            }
        });



        glwssa = (ImageButton) findViewById(R.id.glwssa);
        glwssa.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                mousiki=1;

                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                i.putExtra("firstName", katigories[1]);
                i.putExtra("isalphabete", katigories[1].equals(EnarxiActivity.this.getString(R.string.alphabete)));
                i.putExtra("iscolor", katigories[1].equals(EnarxiActivity.this.getString(R.string.colors)));
                startActivity(i);
            }
        });




        xrwmata = (ImageButton) findViewById(R.id.xrwmata);
        xrwmata.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mousiki=1;


                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                i.putExtra("firstName", katigories[2]);
                i.putExtra("isalphabete", katigories[2].equals(EnarxiActivity.this.getString(R.string.alphabete)));
                i.putExtra("iscolor", katigories[2].equals(EnarxiActivity.this.getString(R.string.colors)));
                startActivity(i);
            }
        });




        alphavita = (ImageButton) findViewById(R.id.alphavita);
        alphavita.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mousiki=1;


                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                i.putExtra("firstName", katigories[3]);
                i.putExtra("isalphabete", katigories[3].equals(EnarxiActivity.this.getString(R.string.alphabete)));
                i.putExtra("iscolor", katigories[3].equals(EnarxiActivity.this.getString(R.string.colors)));
                startActivity(i);
            }
        });




        zwgrafiki = (ImageButton) findViewById(R.id.zwgrafiki);
        zwgrafiki.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mousiki=1;


                Intent myIntent = new Intent(EnarxiActivity.this, PaintActivity.class);
                EnarxiActivity.this.startActivity(myIntent);
            }
        });




}





    private void initCats() {
              katigories = new String[]{
                EnarxiActivity.this.getString(R.string.maths),
                EnarxiActivity.this.getString(R.string.languageCategory),
                EnarxiActivity.this.getString(R.string.colors),
                EnarxiActivity.this.getString(R.string.alphabete),
                      EnarxiActivity.this.getString(R.string.paint)

        };
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
        if (mousiki!=1) {
            MainActivity.mediaPlayer.pause();
            // mediaPlayer.release();
        }
        mousiki=0;
    }


}
