package com.example.mixalis.psagmenos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Locale;

import Misc.Preferences;

/**
 * Created by mixalis on 26/1/2016.
 */
public class AnimalsActivity extends Activity {
    ImageView elefantas;
    ImageView gourouni;
    ImageView pouli;
    ImageView delfini;
    ImageView alogo;
    ImageView agelada;
    ImageView skilos;
    ImageView kota;
    ImageView liontari;
    ImageView gata;

    MediaPlayer questionMediaPlayer;
    String currentLanguage;
    int randomNumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.gameanimals);

        SharedPreferences preferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        currentLanguage = preferences.getString(MainActivity.LANGUAGE_KEY, Locale.getDefault().getLanguage().startsWith("el")?"el": "en");





        elefantas = (ImageView) findViewById(R.id.elefantas);
        elefantas.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();
                randomNumer=2;
                String filenameSound = currentLanguage.equals("el") ? "elefantas":"elefantasagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });

            }
        });



        gourouni = (ImageView) findViewById(R.id.gourouni);
        gourouni.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();
                randomNumer=3;
                String filenameSound = currentLanguage.equals("el") ? "gourouni":"gourouniagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });
            }
        });



        pouli = (ImageView) findViewById(R.id.bird);
        pouli.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();
                randomNumer=4;
                String filenameSound = currentLanguage.equals("el") ? "pouli":"pouliagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());

                 questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });
            }
        });



        delfini = (ImageView) findViewById(R.id.delfini);
        delfini.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();
                randomNumer=5;
                String filenameSound = currentLanguage.equals("el") ? "delfini":"delfiniagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });
            }
        });


        alogo = (ImageView) findViewById(R.id.alogo);
        alogo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();
                randomNumer=6;
                String filenameSound = currentLanguage.equals("el") ? "alogo":"alogoagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });
            }
        });





        agelada = (ImageView) findViewById(R.id.agelada);
        agelada.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();
                randomNumer=7;
                String filenameSound = currentLanguage.equals("el") ? "agelada":"ageladaagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });

            }
        });



        skilos = (ImageView) findViewById(R.id.skilos);
        skilos.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();
                randomNumer=8;
                String filenameSound = currentLanguage.equals("el") ? "skilos":"skilosagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });

            }
        });




        kota = (ImageView) findViewById(R.id.kota);
        kota.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();
randomNumer=1;
                String filenameSound = currentLanguage.equals("el") ? "kokoras":"kokorasagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });

            }
        });




        liontari = (ImageView) findViewById(R.id.liontari);
        liontari.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();

                randomNumer=9;
                String filenameSound = currentLanguage.equals("el") ? "liontari":"liontariagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });
            }
        });



        gata = (ImageView) findViewById(R.id.gata);
        gata.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (questionMediaPlayer != null)
                    questionMediaPlayer.release();

                randomNumer=10;
                String filenameSound = currentLanguage.equals("el") ? "gata":"gataagglika";
                int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(AnimalsActivity.this, idSound);
                questionMediaPlayer.start();
                questionMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        String filenameSound = String.format("animals%d", randomNumer);
                        int idSound = AnimalsActivity.this.getResources().getIdentifier(filenameSound, "raw", AnimalsActivity.this.getPackageName());
                        questionMediaPlayer =  MediaPlayer.create(AnimalsActivity.this, idSound);
                        questionMediaPlayer.start();
                    }
                });
            }
        });




    }

    @Override
    protected void onDestroy() {
        if (questionMediaPlayer != null)
            questionMediaPlayer.release();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!this.isFinishing()) {
            MainActivity.mediaPlayer.pause();
            // MainActivity.mediaPlayer.stop();

        }
    }

    @Override
    protected void onResume() {
        boolean isSoundEnabled;
        isSoundEnabled = (boolean) Preferences.get(this, RythmiseisActivity.SOUNDSETTINGS, RythmiseisActivity.ISSOUNDENABLED, true);
        if(isSoundEnabled && !MainActivity.mediaPlayer.isPlaying())
            MainActivity.mediaPlayer.start();

        super.onResume();
        super.onResume();
    }
}
