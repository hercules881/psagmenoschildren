package com.example.mixalis.psagmenos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Database.Answer;
import Database.ExternalDbOpenHelper;
import Database.Question;
import Misc.Preferences;


/**
 * Created by mixalis on 5/12/2015.
 */
public class ShapesActivity extends Activity {
    ImageButton apantisi1;
    ImageButton apantisi2;
    ImageButton apantisi3;
    ImageButton apantisi4;
    ImageView imageshape;

    TextView erwtisi;
    private Handler handler = new Handler();
    ArrayList<Integer> lastQuestionNumber = new ArrayList<>();
    int randomNumer;
    Thread thread;
    ArrayList<Question> questions;

    String currentLanguage;
    boolean lockLoop = true;
    Integer[] drawbleAnswersId = new Integer[]{R.drawable.shapes0, R.drawable.shapes1, R.drawable.shapes2, R.drawable.shapes3, R.drawable.shapes4, R.drawable.shapes5, R.drawable.shapes6, R.drawable.shapes7, R.drawable.shapes8};
    HashMap<String, Integer> mapColorsToBackground;
    String[] drawableimageid = new String[]{"trigwno", "romvos", "orthogwnio", "tetragwno", "kilindros", "kiklos", "kardia", "ouloudi", "oval"};
    String ena;
    String dio;
    String treia;
    String tessera;
    MediaPlayer questionMediaPlayer;
    MediaPlayer colorMediaPlayer;
    int questionsCounter = 0;
    Timer timer;
int mousiki=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.gameshapes);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // All emulators
        //    .addTestDevice("B91B02905799AD43D0D499D045263F18").build();  // An example device ID
        //vaze auto se release AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        final String epelexes = intent.getExtras().getString("epelexes");
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(epelexes);

        apantisi1 = (ImageButton) findViewById(R.id.apantisi1);
        apantisi2 = (ImageButton) findViewById(R.id.apantisi2);
        apantisi3 = (ImageButton) findViewById(R.id.apantisi3);
        apantisi4 = (ImageButton) findViewById(R.id.apantisi4);
        erwtisi = (TextView) findViewById(R.id.erwtisi);
        imageshape = (ImageView) findViewById(R.id.imageshapesCategory);

        SharedPreferences preferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        currentLanguage = preferences.getString(MainActivity.LANGUAGE_KEY, Locale.getDefault().getLanguage().startsWith("el")?"el": "en");

        createHashMap();

        new Thread(new Runnable() {
            @Override
            public void run() {
                questions = (ArrayList<Question>) ExternalDbOpenHelper.sharedInstance().getQuestionForCategory(epelexes);   //erwtiseis
                randomNumer = getRandomNumer(questions.size() - 1);
                //kratame ton arithmo tis proigoumenis erwtisis gia na min ksanapesei!


                String filename = String.format("shapes%d", randomNumer);
                final int id = ShapesActivity.this.getResources().getIdentifier(filename, "drawable", ShapesActivity.this.getPackageName());
                ShapesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageshape.setImageResource(id);
                    }
                });
                 String filenameSound = String.format(currentLanguage.equals("el") ? "shapes%d" : "shapesenglish%d", randomNumer);
                 int idSound = ShapesActivity.this.getResources().getIdentifier(filenameSound, "raw", ShapesActivity.this.getPackageName());
                questionMediaPlayer = MediaPlayer.create(ShapesActivity.this,idSound);
                 questionMediaPlayer.start();


                lastQuestionNumber.add(randomNumer);
                questions.get(randomNumer);
                lockLoop = false;
                ShapesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // progressDialog.dismiss();
                        erwtisi.setText(questions.get(randomNumer).getText());
                        ArrayList<Answer> answers = (ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(questions.get(randomNumer));//apantiseis
                        answers.get(0);

                        ena = answers.get(0).getText();
                        dio = answers.get(1).getText();
                        treia = answers.get(2).getText();
                        tessera = answers.get(3).getText();

                        if (ena.equals("Τρίγωνο") || ena.equals("Triangle"))
                            apantisi1.setImageResource(R.drawable.shapes0);
                        if (ena.equals("Ρόμβος") || ena.equals("Rhombus"))
                            apantisi1.setImageResource(R.drawable.shapes1);
                        if (ena.equals("Ορθογώνιο") || ena.equals("Rectangular"))
                            apantisi1.setImageResource(R.drawable.shapes2);
                        if (ena.equals("Τετράγωνο") || ena.equals("Square"))
                            apantisi1.setImageResource(R.drawable.shapes3);
                        if (ena.equals("Κύλινδρος") || ena.equals("Cylinder"))
                            apantisi1.setImageResource(R.drawable.shapes4);
                        if (ena.equals("Κύκλος") || ena.equals("Circle"))
                            apantisi1.setImageResource(R.drawable.shapes5);
                        if (ena.equals("Καρδιά") || ena.equals("Heart"))
                            apantisi1.setImageResource(R.drawable.shapes6);
                        if (ena.equals("Λουλούδι") || ena.equals("Flower"))
                            apantisi1.setImageResource(R.drawable.shapes7);
                        if (ena.equals("Οβάλ") || ena.equals("Oval"))
                            apantisi1.setImageResource(R.drawable.shapes8);

                        if (dio.equals("Τρίγωνο") || dio.equals("Triangle"))
                            apantisi2.setImageResource(R.drawable.shapes0);
                        if (dio.equals("Ρόμβος") || dio.equals("Rhombus"))
                            apantisi2.setImageResource(R.drawable.shapes1);
                        if (dio.equals("Ορθογώνιο") || dio.equals("Rectangular"))
                            apantisi2.setImageResource(R.drawable.shapes2);
                        if (dio.equals("Τετράγωνο") || dio.equals("Square"))
                            apantisi2.setImageResource(R.drawable.shapes3);
                        if (dio.equals("Κύλινδρος") || dio.equals("Cylinder"))
                            apantisi2.setImageResource(R.drawable.shapes4);
                        if (dio.equals("Κύκλος") || dio.equals("Circle"))
                            apantisi2.setImageResource(R.drawable.shapes5);
                        if (dio.equals("Καρδιά") || dio.equals("Heart"))
                            apantisi2.setImageResource(R.drawable.shapes6);
                        if (dio.equals("Λουλούδι") || dio.equals("Flower"))
                            apantisi2.setImageResource(R.drawable.shapes7);
                        if (dio.equals("Οβάλ") || dio.equals("Oval"))
                            apantisi2.setImageResource(R.drawable.shapes8);

                        if (treia.equals("Τρίγωνο") || treia.equals("Triangle"))
                            apantisi3.setImageResource(R.drawable.shapes0);
                        if (treia.equals("Ρόμβος") || treia.equals("Rhombus"))
                            apantisi3.setImageResource(R.drawable.shapes1);
                        if (treia.equals("Ορθογώνιο") || treia.equals("Rectangular"))
                            apantisi3.setImageResource(R.drawable.shapes2);
                        if (treia.equals("Τετράγωνο") || treia.equals("Square"))
                            apantisi3.setImageResource(R.drawable.shapes3);
                        if (treia.equals("Κύλινδρος") || treia.equals("Cylinder"))
                            apantisi3.setImageResource(R.drawable.shapes4);
                        if (treia.equals("Κύκλος") || treia.equals("Circle"))
                            apantisi3.setImageResource(R.drawable.shapes5);
                        if (treia.equals("Καρδιά") || treia.equals("Heart"))
                            apantisi3.setImageResource(R.drawable.shapes6);
                        if (treia.equals("Λουλούδι") || treia.equals("Flower"))
                            apantisi3.setImageResource(R.drawable.shapes7);
                        if (treia.equals("Οβάλ") || treia.equals("Oval"))
                            apantisi3.setImageResource(R.drawable.shapes8);

                        if (tessera.equals("Τρίγωνο") || tessera.equals("Triangle"))
                            apantisi4.setImageResource(R.drawable.shapes0);
                        if (tessera.equals("Ρόμβος") || tessera.equals("Rhombus"))
                            apantisi4.setImageResource(R.drawable.shapes1);
                        if (tessera.equals("Ορθογώνιο") || tessera.equals("Rectangular"))
                            apantisi4.setImageResource(R.drawable.shapes2);
                        if (tessera.equals("Τετράγωνο") || tessera.equals("Square"))
                            apantisi4.setImageResource(R.drawable.shapes3);
                        if (tessera.equals("Κύλινδρος") || tessera.equals("Cylinder"))
                            apantisi4.setImageResource(R.drawable.shapes4);
                        if (tessera.equals("Κύκλος") || tessera.equals("Circle"))
                            apantisi4.setImageResource(R.drawable.shapes5);
                        if (tessera.equals("Καρδιά") || tessera.equals("Heart"))
                            apantisi4.setImageResource(R.drawable.shapes6);
                        if (tessera.equals("Λουλούδι") || tessera.equals("Flower"))
                            apantisi4.setImageResource(R.drawable.shapes7);
                        if (tessera.equals("Οβάλ") || tessera.equals("Oval"))
                            apantisi4.setImageResource(R.drawable.shapes8);
                        // apantisi1.setBackgroundResource(mapColorsToBackground.get(apantisi1.getText().toString().trim()));
                        ///   apantisi2.setBackgroundResource(mapColorsToBackground.get(apantisi2.getText().toString().trim()));
                        //  apantisi3.setBackgroundResource(mapColorsToBackground.get(apantisi3.getText().toString().trim()));
                        //  apantisi4.setBackgroundResource(mapColorsToBackground.get(apantisi4.getText().toString().trim()));


                    }
                });

            }
        }).start();


        apantisi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;

                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer));
                ArrayList<Answer> answers = (ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer : answers) {

                    if (answer.getIsValidAnswer() == 1 && (ena.equals(answer.getText()))) {
                        String filename = currentLanguage.equals("el") ? "success" : "correct";
                        int id = ShapesActivity.this.getResources().getIdentifier(filename, "raw", ShapesActivity.this.getPackageName());
                        colorMediaPlayer = MediaPlayer.create(ShapesActivity.this, id);
                        colorMediaPlayer.start();
                        isCorrectAnswer = true;

                        break;
                    }


                }

                if (!isCorrectAnswer) {
                    String filename = currentLanguage.equals("el") ? "failure" : "wrong";
                    int id = ShapesActivity.this.getResources().getIdentifier(filename, "raw", ShapesActivity.this.getPackageName());
                    colorMediaPlayer = MediaPlayer.create(ShapesActivity.this, id);
                    colorMediaPlayer.start();
                }


                if (questionMediaPlayer != null) {
                    questionMediaPlayer.release();
                }
                goToNextQuestion();

            }
        });






        apantisi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;

                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer));
                ArrayList<Answer> answers = (ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer : answers) {

                    if (answer.getIsValidAnswer() == 1 && (dio.equals(answer.getText()))) {
                        String filename = currentLanguage.equals("el") ? "success" : "correct";
                        int id = ShapesActivity.this.getResources().getIdentifier(filename, "raw", ShapesActivity.this.getPackageName());
                        colorMediaPlayer = MediaPlayer.create(ShapesActivity.this, id);
                        colorMediaPlayer.start();
                        isCorrectAnswer = true;

                        break;
                    }


                }

                if (!isCorrectAnswer) {
                    String filename = currentLanguage.equals("el") ? "failure" : "wrong";
                    int id = ShapesActivity.this.getResources().getIdentifier(filename, "raw", ShapesActivity.this.getPackageName());
                    colorMediaPlayer = MediaPlayer.create(ShapesActivity.this, id);
                    colorMediaPlayer.start();
                }


                if (questionMediaPlayer != null) {
                    questionMediaPlayer.release();
                }
                goToNextQuestion();

            }
        });



        apantisi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;

                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer));
                ArrayList<Answer> answers = (ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer : answers) {

                    if (answer.getIsValidAnswer() == 1 && (treia.equals(answer.getText()))) {
                        String filename = currentLanguage.equals("el") ? "success" : "correct";
                        int id = ShapesActivity.this.getResources().getIdentifier(filename, "raw", ShapesActivity.this.getPackageName());
                        colorMediaPlayer = MediaPlayer.create(ShapesActivity.this, id);
                        colorMediaPlayer.start();
                        isCorrectAnswer = true;

                        break;
                    }


                }

                if (!isCorrectAnswer) {
                    String filename = currentLanguage.equals("el") ? "failure" : "wrong";
                    int id = ShapesActivity.this.getResources().getIdentifier(filename, "raw", ShapesActivity.this.getPackageName());
                    colorMediaPlayer = MediaPlayer.create(ShapesActivity.this, id);
                    colorMediaPlayer.start();
                }


                if (questionMediaPlayer != null) {
                    questionMediaPlayer.release();
                }
                goToNextQuestion();

            }
        });



        apantisi4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;

                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer));
                ArrayList<Answer> answers = (ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer : answers) {

                    if (answer.getIsValidAnswer() == 1 && (tessera.equals(answer.getText()))) {
                        String filename = currentLanguage.equals("el") ? "success" : "correct";
                        int id = ShapesActivity.this.getResources().getIdentifier(filename, "raw", ShapesActivity.this.getPackageName());
                        colorMediaPlayer = MediaPlayer.create(ShapesActivity.this, id);
                        colorMediaPlayer.start();
                        isCorrectAnswer = true;

                        break;
                    }


                }

                if (!isCorrectAnswer) {
                    String filename = currentLanguage.equals("el") ? "failure" : "wrong";
                    int id = ShapesActivity.this.getResources().getIdentifier(filename, "raw", ShapesActivity.this.getPackageName());
                    colorMediaPlayer = MediaPlayer.create(ShapesActivity.this, id);
                    colorMediaPlayer.start();
                }


                if (questionMediaPlayer != null) {
                    questionMediaPlayer.release();
                }
                goToNextQuestion();

            }
        });




    }

    private int getRandomNumer(int size) {
        Random r = new Random();
        return randomNumer = r.nextInt(size);
    }

    private void createHashMap() {
        String[] colorAnswersId = new String[]{this.getString(R.string.black), this.getString(R.string.blue), this.getString(R.string.green), this.getString(R.string.red), this.getString(R.string.brown), this.getString(R.string.yellow), this.getString(R.string.white), this.getString(R.string.grey), this.getString(R.string.orange)};

        mapColorsToBackground = new HashMap<>();
        for (int i = 0; i < colorAnswersId.length; i++) {
            mapColorsToBackground.put(colorAnswersId[i].trim(), drawbleAnswersId[i]);
        }
    }


    private void goToNextQuestion() {
        questionsCounter++;
        if(questionsCounter == questions.size()) {
            //  progressStatus=0;

            showAlertDialog3();
            return;
        }
        apantisi1.setEnabled(false);
        apantisi2.setEnabled(false);
        apantisi3.setEnabled(false);
        apantisi4.setEnabled(false);
        timer = new Timer();
                                    /* Duration of wait */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ShapesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        randomNumer = getRandomWithExclusion(new Random(), 0, questions.size() - 1, lastQuestionNumber);
                        if (colorMediaPlayer != null)
                            colorMediaPlayer.release();
                        //kratame ton arithmo tis proigoumenis erwtisis gia na min ksanapesei!


                        String filename = String.format("shapes%d", randomNumer);
                        final int id = ShapesActivity.this.getResources().getIdentifier(filename, "drawable", ShapesActivity.this.getPackageName());
                        ShapesActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageshape.setImageResource(id);
                            }
                        });
                        String filenameSound = String.format(currentLanguage.equals("el") ? "shapes%d" : "shapesenglish%d", randomNumer);
                        int idSound = ShapesActivity.this.getResources().getIdentifier(filenameSound, "raw", ShapesActivity.this.getPackageName());
                        questionMediaPlayer = MediaPlayer.create(ShapesActivity.this, idSound);
                        questionMediaPlayer.start();

                        lastQuestionNumber.add(randomNumer);
                        erwtisi.setText(questions.get(randomNumer).getText());
                        ArrayList<Answer> answers = (ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(questions.get(randomNumer));//apantiseis
                        answers.get(0);

                        apantisi1.setBackgroundResource(R.drawable.text_corner);
                        apantisi2.setBackgroundResource(R.drawable.text_corner);
                        apantisi3.setBackgroundResource(R.drawable.text_corner);
                        apantisi4.setBackgroundResource(R.drawable.text_corner);


                        ena = answers.get(0).getText();
                        dio = answers.get(1).getText();
                        treia = answers.get(2).getText();
                        tessera = answers.get(3).getText();

                        if (ena.equals("Τρίγωνο") || ena.equals("Triangle")) apantisi1.setImageResource(R.drawable.shapes0);
                        if (ena.equals("Ρόμβος") || ena.equals("Rhombus")) apantisi1.setImageResource(R.drawable.shapes1);
                        if (ena.equals("Ορθογώνιο") || ena.equals("Rectangular")) apantisi1.setImageResource(R.drawable.shapes2);
                        if (ena.equals("Τετράγωνο") || ena.equals("Square")) apantisi1.setImageResource(R.drawable.shapes3);
                        if (ena.equals("Κύλινδρος") || ena.equals("Cylinder")) apantisi1.setImageResource(R.drawable.shapes4);
                        if (ena.equals("Κύκλος") || ena.equals("Circle")) apantisi1.setImageResource(R.drawable.shapes5);
                        if (ena.equals("Καρδιά") || ena.equals("Heart")) apantisi1.setImageResource(R.drawable.shapes6);
                        if (ena.equals("Λουλούδι") || ena.equals("Flower")) apantisi1.setImageResource(R.drawable.shapes7);
                        if (ena.equals("Οβάλ") || ena.equals("Oval")) apantisi1.setImageResource(R.drawable.shapes8);

                        if (dio.equals("Τρίγωνο") || dio.equals("Triangle")) apantisi2.setImageResource(R.drawable.shapes0);
                        if (dio.equals("Ρόμβος") || dio.equals("Rhombus")) apantisi2.setImageResource(R.drawable.shapes1);
                        if (dio.equals("Ορθογώνιο") || dio.equals("Rectangular")) apantisi2.setImageResource(R.drawable.shapes2);
                        if (dio.equals("Τετράγωνο") || dio.equals("Square")) apantisi2.setImageResource(R.drawable.shapes3);
                        if (dio.equals("Κύλινδρος") || dio.equals("Cylinder")) apantisi2.setImageResource(R.drawable.shapes4);
                        if (dio.equals("Κύκλος") || dio.equals("Circle")) apantisi2.setImageResource(R.drawable.shapes5);
                        if (dio.equals("Καρδιά") || dio.equals("Heart")) apantisi2.setImageResource(R.drawable.shapes6);
                        if (dio.equals("Λουλούδι") || dio.equals("Flower")) apantisi2.setImageResource(R.drawable.shapes7);
                        if (dio.equals("Οβάλ") || dio.equals("Oval")) apantisi2.setImageResource(R.drawable.shapes8);

                        if (treia.equals("Τρίγωνο") || treia.equals("Triangle")) apantisi3.setImageResource(R.drawable.shapes0);
                        if (treia.equals("Ρόμβος") || treia.equals("Rhombus")) apantisi3.setImageResource(R.drawable.shapes1);
                        if (treia.equals("Ορθογώνιο") || treia.equals("Rectangular")) apantisi3.setImageResource(R.drawable.shapes2);
                        if (treia.equals("Τετράγωνο") || treia.equals("Square")) apantisi3.setImageResource(R.drawable.shapes3);
                        if (treia.equals("Κύλινδρος") || treia.equals("Cylinder")) apantisi3.setImageResource(R.drawable.shapes4);
                        if (treia.equals("Κύκλος") || treia.equals("Circle")) apantisi3.setImageResource(R.drawable.shapes5);
                        if (treia.equals("Καρδιά") || treia.equals("Heart")) apantisi3.setImageResource(R.drawable.shapes6);
                        if (treia.equals("Λουλούδι") || treia.equals("Flower")) apantisi3.setImageResource(R.drawable.shapes7);
                        if (treia.equals("Οβάλ") || treia.equals("Oval")) apantisi3.setImageResource(R.drawable.shapes8);

                        if (tessera.equals("Τρίγωνο") || tessera.equals("Triangle")) apantisi4.setImageResource(R.drawable.shapes0);
                        if (tessera.equals("Ρόμβος") || tessera.equals("Rhombus")) apantisi4.setImageResource(R.drawable.shapes1);
                        if (tessera.equals("Ορθογώνιο") || tessera.equals("Rectangular")) apantisi4.setImageResource(R.drawable.shapes2);
                        if (tessera.equals("Τετράγωνο") || tessera.equals("Square")) apantisi4.setImageResource(R.drawable.shapes3);
                        if (tessera.equals("Κύλινδρος") || tessera.equals("Cylinder")) apantisi4.setImageResource(R.drawable.shapes4);
                        if (tessera.equals("Κύκλος") || tessera.equals("Circle")) apantisi4.setImageResource(R.drawable.shapes5);
                        if (tessera.equals("Καρδιά") || tessera.equals("Heart")) apantisi4.setImageResource(R.drawable.shapes6);
                        if (tessera.equals("Λουλούδι") || tessera.equals("Flower")) apantisi4.setImageResource(R.drawable.shapes7);
                        if (tessera.equals("Οβάλ") || tessera.equals("Oval")) apantisi4.setImageResource(R.drawable.shapes8);

                       /* ena = answers.get(0).getText().toString();
                        dio = answers.get(1).getText().toString();
                        treia = answers.get(2).getText();
                        tessera = answers.get(3).getText();*/

                        apantisi1.setEnabled(true);
                        apantisi2.setEnabled(true);
                        apantisi3.setEnabled(true);
                        apantisi4.setEnabled(true);
                        //progressStatus = 81;
                        lockLoop = false;

                    }
                });

            }
        }, 1500);
    }

    public int getRandomWithExclusion(Random rnd, int start, int end, ArrayList<Integer> exclude) {
        Random rand = new Random();
        int range = end - start + 1;

        int random = rand.nextInt(range);
        while (exclude.contains(random)) {
            random = rand.nextInt(range);
        }

        return random;
    }


    private void showAlertDialog3() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dilaoggelio);
        dialog.setTitle(R.string.tittledialog3);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(R.string.letsplayagain);
        dialog.findViewById(R.id.dialogButtonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShapesActivity.this.finish();

            }
        });



        dialog.setCancelable(false);
        if(!ShapesActivity.this.isFinishing())
            dialog.show();
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

    @Override
    protected void onResume() {

        boolean isSoundEnabled;
        isSoundEnabled = (boolean) Preferences.get(this, RythmiseisActivity.SOUNDSETTINGS, RythmiseisActivity.ISSOUNDENABLED, true);
        if(isSoundEnabled && !MainActivity.mediaPlayer.isPlaying())
            MainActivity.mediaPlayer.start();

        super.onResume();
    }
}