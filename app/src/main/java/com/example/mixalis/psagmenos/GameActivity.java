package com.example.mixalis.psagmenos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Database.Answer;
import Database.ExternalDbOpenHelper;
import Database.Question;
import Misc.Preferences;

import static com.example.mixalis.psagmenos.R.string.tittledialogcry;

/**
 * Created by mixalis on 14/11/2015.
 */
public class GameActivity extends Activity {
    private int progressStatus = 81;
    Integer score=0;      //apothikeuei tin progressstatus
    Integer scoreteliko=0; //tha tin prosthetei sto proigoumeno score
    private Handler handler = new Handler();
    private ProgressBar progressBar;
    ProgressBar bar;
    String epelexes;
    private Button apantisi1;
    private Button apantisi2;
    private Button apantisi3;
    private Button apantisi4;
    private int lifes = 3;
    ArrayList<Integer> lastQuestionNumber = new ArrayList<>();
    int randomNumer;
    Thread thread;
    ArrayList<Question> questions;

    TextView erwtisi;
    TextView scoreview;
    int questionsCounter = 0;
    int highScore;
    public final static String GAMEACTIVITY = "gameactivity";
    public final static String HIGHSCORE = "highscore";
    TextView highScoreText;
    boolean lockLoop = true;
    boolean isAlphabete = false;
    MediaPlayer questionMediaPlayer;
    MediaPlayer colorMediaPlayer;
    boolean isColor = false;
    ImageView colorImage;
    String currentLanguage;
    Integer[] drawbleAnswersId = new Integer[]{R.drawable.text_cornermauro, R.drawable.text_cornermple,R.drawable.text_cornerprassino, R.drawable.text_cornerkokkino,R.drawable.text_cornerkafe, R.drawable.text_cornerkitrino, R.drawable.text_corneraspro,R.drawable.text_cornergri,R.drawable.text_cornerportokali};
    HashMap<String,Integer> mapColorsToBackground;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        isColor =  intent.getBooleanExtra("iscolor", false);
        isAlphabete = intent.getBooleanExtra("isalphabete", false);
        setContentView(!isColor && !isAlphabete ?R.layout.game:R.layout.gamexrwmata);
        if(isAlphabete) {
            findViewById(R.id.imageColorCategory).setVisibility(View.INVISIBLE);
            findViewById(R.id.linear4).setVisibility(View.INVISIBLE);
            findViewById(R.id.linear5).setVisibility(View.INVISIBLE);
        }
        progressBar = (ProgressBar) findViewById(R.id.progressbar1);
        erwtisi = (TextView) findViewById(R.id.erwtisi);
        scoreview = (TextView) findViewById(R.id.score);
        highScoreText = (TextView) findViewById(R.id.highscore);

        apantisi1 = (Button) findViewById(R.id.apantisi1);
        apantisi2 = (Button) findViewById(R.id.apantisi2);
        apantisi3 = (Button) findViewById(R.id.apantisi3);
        apantisi4 = (Button) findViewById(R.id.apantisi4);

        if(isColor){
            colorImage = (ImageView) findViewById(R.id.imageColorCategory);
            createHashMap();
            findViewById(R.id.linear4).setVisibility(View.INVISIBLE);
            findViewById(R.id.linear5).setVisibility(View.INVISIBLE);
        }

        // ProgressBar pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        String fName = intent.getStringExtra("firstName");
        String lName = intent.getStringExtra("lastName");
        epelexes=fName;
        SharedPreferences preferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        currentLanguage = preferences.getString(MainActivity.LANGUAGE_KEY, "el");
        highScore = (int) Preferences.get(this, GAMEACTIVITY, HIGHSCORE, 0);
        highScoreText.setText(String.valueOf(highScore));
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

       // ExternalDbOpenHelper.sharedInstance() = new ExternalDbOpenHelper(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                questions= (ArrayList<Question>) ExternalDbOpenHelper.sharedInstance().getQuestionForCategory(epelexes);   //erwtiseis
                randomNumer = getRandomNumer(questions.size()-1);
                //kratame ton arithmo tis proigoumenis erwtisis gia na min ksanapesei!
                if(isAlphabete){
                    String filename = String.format(currentLanguage.equals("el")?"a%d":"englisha%d",randomNumer);
                    int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                    questionMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                    questionMediaPlayer.start();

                }
                if(isColor){
                    String filename = String.format("color%d",randomNumer);
                    final int id = GameActivity.this.getResources().getIdentifier(filename,"drawable",GameActivity.this.getPackageName());
                    GameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            colorImage.setImageResource(id);
                        }
                    });
                    String filenameSound = String.format(currentLanguage.equals("el")?"colorsound%d":"colorsoundenglish%d",randomNumer);
                    int idSound = GameActivity.this.getResources().getIdentifier(filenameSound,"raw",GameActivity.this.getPackageName());
                    questionMediaPlayer = MediaPlayer.create(GameActivity.this,idSound);
                    questionMediaPlayer.start();

                }

                lastQuestionNumber.add(randomNumer);
                questions.get(randomNumer);
                lockLoop = false;
                GameActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        erwtisi.setText(questions.get(randomNumer).getText());
                        ArrayList<Answer> answers = (ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(questions.get(randomNumer));//apantiseis
                        answers.get(0);
                        apantisi1.setText(answers.get(0).getText());
                        apantisi2.setText(answers.get(1).getText());
                        apantisi3.setText(answers.get(2).getText());
                        apantisi4.setText(answers.get(3).getText());
                        if(isColor){
                            apantisi1.setBackgroundResource(mapColorsToBackground.get(apantisi1.getText().toString().trim()));
                            apantisi2.setBackgroundResource(mapColorsToBackground.get(apantisi2.getText().toString().trim()));
                            apantisi3.setBackgroundResource(mapColorsToBackground.get(apantisi3.getText().toString().trim()));
                            apantisi4.setBackgroundResource(mapColorsToBackground.get(apantisi4.getText().toString().trim()));

                        }
                    }
                });

            }
        }).start();

        //pairnoume tuxaio arithmo gia erwtisi!

        // answers.get(0).getIsValidAnswer(); an einai swsti i pantisi einai 1 aliws einai 0!


        TextView title = (TextView) findViewById(R.id.title);
        title.setText(epelexes);
        thread =  new Thread(new Runnable() {
            public void run() {

                while (progressStatus > 0) {
                    if(lockLoop)
                        continue;
                    progressStatus -= 1;
                    score=progressStatus; //gia vathmologia

                    // Update the progress bar and display the
                    //current value in the text view

                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            //an teleivse o xronos allakse erwtisi!!!
                            if(progressStatus == 0){
                                questionsCounter++;

                                if(questionsCounter == questions.size()) {
                                    progressStatus=0;
                                    if(scoreteliko>highScore)
                                        showAlertDialog2();
                                    else
                                        showAlertDialog();
                                    return;
                                }

                                findViewById(lifes ==3?R.id.zwi3:lifes == 2? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                                lifes--;
                                if(lifes == 0){
                                    if(scoreteliko>highScore)
                                        showAlertDialog2();
                                    else
                                        showAlertDialog();
                                    return;

                                }
                                randomNumer = getRandomWithExclusion(new Random(), 0 , questions.size()-1 , lastQuestionNumber);
                                if(randomNumer>=questions.size())
                                    return;
                                if(isAlphabete){
                                    String filename = String.format(currentLanguage.equals("el")?"a%d":"englisha%d",randomNumer);
                                    int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                                    questionMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                                    questionMediaPlayer.start();

                                }

                                //kratame ton arithmo tis proigoumenis erwtisis gia na min ksanapesei!
                                lastQuestionNumber.add(randomNumer);
                                erwtisi.setText(questions.get(randomNumer).getText());
                                ArrayList<Answer>answers=(ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(questions.get(randomNumer));//apantiseis
                                answers.get(0);
                                apantisi1.setText(answers.get(0).getText());
                                apantisi2.setText(answers.get(1).getText());
                                apantisi3.setText(answers.get(2).getText());
                                apantisi4.setText(answers.get(3).getText());
                                if(isColor){
                                    apantisi1.setBackgroundResource(mapColorsToBackground.get(apantisi1.getText().toString().trim()));
                                    apantisi2.setBackgroundResource(mapColorsToBackground.get(apantisi2.getText().toString().trim()));
                                    apantisi3.setBackgroundResource(mapColorsToBackground.get(apantisi3.getText().toString().trim()));
                                    apantisi4.setBackgroundResource(mapColorsToBackground.get(apantisi4.getText().toString().trim()));

                                }

                                progressStatus = 81;

                            }
                            // textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    //an teleiwsoun oi zwes stamata tin loopa!!!
                    if (lifes == 0)
                        break;

                    try {
                        // Sleep for 200 milliseconds.

                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if(!isColor && !isAlphabete)
            thread.start();

        apantisi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;

                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer));
                ArrayList<Answer> answers = (ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer : answers) {
                    if(!isColor) {
                        if (answer.getIsValidAnswer() == 1 && (apantisi1.getText().toString().equals(answer.getText()))) {
                            apantisi1.setBackgroundResource(R.drawable.text_cornerprassino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                            isCorrectAnswer = true;
                            if(isAlphabete){
                                String filename =currentLanguage.equals("el")? "success":"correct";
                                int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                                colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                                colorMediaPlayer.start();
                            }
                            scoreteliko = scoreteliko + score;
                            scoreview.setText(String.valueOf(scoreteliko));
                            break;
                        } else {
                            apantisi1.setBackgroundResource(R.drawable.text_cornerkokkino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                        }
                    }else{
                        if (answer.getIsValidAnswer() == 1 && (apantisi1.getText().toString().equals(answer.getText()))) {
                            String filename =currentLanguage.equals("el")? "success":"correct";
                            int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                            colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                            colorMediaPlayer.start();
                            isCorrectAnswer = true;
                            scoreteliko = scoreteliko + score;
                            scoreview.setText(String.valueOf(scoreteliko));
                            break;
                        }

                    }
                }
                if(!isCorrectAnswer && !isColor && !isAlphabete) {
                    findViewById(lifes == 3 ? R.id.zwi3 : lifes == 2 ? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                    lifes--;
                }
                if(!isCorrectAnswer && (isColor ||isAlphabete)) {
                    String filename = currentLanguage.equals("el")?"failure":"wrong";
                    int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                    colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                    colorMediaPlayer.start();
                }

                if(lifes == 0){
                    if(scoreteliko>highScore)
                        showAlertDialog2();
                    else
                        showAlertDialog();
                    return;
                }
                if(questionMediaPlayer != null) {
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
                ArrayList<Answer>answers=(ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer :answers){
                    if(!isColor) {
                        if (answer.getIsValidAnswer() == 1 && (apantisi2.getText().toString().equals(answer.getText()))) {
                            apantisi2.setBackgroundResource(R.drawable.text_cornerprassino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                            isCorrectAnswer = true;
                            if(isAlphabete) {
                                String filename = currentLanguage.equals("el") ? "success" : "correct";
                                int id = GameActivity.this.getResources().getIdentifier(filename, "raw", GameActivity.this.getPackageName());
                                colorMediaPlayer = MediaPlayer.create(GameActivity.this, id);
                                colorMediaPlayer.start();
                            }
                            scoreteliko = scoreteliko + score;
                            scoreview.setText(String.valueOf(scoreteliko));
                            break;
                        } else {
                            apantisi2.setBackgroundResource(R.drawable.text_cornerkokkino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                        }
                    }else{
                        if (answer.getIsValidAnswer() == 1 && (apantisi2.getText().toString().equals(answer.getText()))) {
                            String filename =currentLanguage.equals("el")? "success":"correct";
                            int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                            colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                            colorMediaPlayer.start();
                            isCorrectAnswer = true;
                            scoreteliko = scoreteliko + score;
                            scoreview.setText(String.valueOf(scoreteliko));
                            break;
                        }

                    }
                }

                if(!isCorrectAnswer && !isColor && !isAlphabete) {
                    findViewById(lifes == 3 ? R.id.zwi3 : lifes == 2 ? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                    lifes--;
                }
                if(!isCorrectAnswer && (isColor||isAlphabete)) {
                    String filename = currentLanguage.equals("el")?"failure":"wrong";
                    int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                    colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                    colorMediaPlayer.start();
                }
                if(lifes == 0){
                    if(scoreteliko>highScore)
                        showAlertDialog2();
                    else
                        showAlertDialog();
                    return;
                }
                if(questionMediaPlayer != null) {
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
                ArrayList<Answer>answers=(ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer :answers){
                    if(!isColor) {
                        if (answer.getIsValidAnswer() == 1 && (apantisi3.getText().toString().equals(answer.getText()))) {
                            apantisi3.setBackgroundResource(R.drawable.text_cornerprassino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                            isCorrectAnswer = true;
                            if(isAlphabete) {
                                String filename = currentLanguage.equals("el") ? "success" : "correct";
                                int id = GameActivity.this.getResources().getIdentifier(filename, "raw", GameActivity.this.getPackageName());
                                colorMediaPlayer = MediaPlayer.create(GameActivity.this, id);
                                colorMediaPlayer.start();
                            }
                            scoreteliko = scoreteliko + score;
                            scoreview.setText(String.valueOf(scoreteliko));
                            break;
                        } else {
                            apantisi3.setBackgroundResource(R.drawable.text_cornerkokkino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                        }
                    }else{
                        if (answer.getIsValidAnswer() == 1 && (apantisi3.getText().toString().equals(answer.getText()))) {
                            String filename =currentLanguage.equals("el")? "success":"correct";
                            int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                            colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                            colorMediaPlayer.start();
                            isCorrectAnswer = true;
                            scoreteliko = scoreteliko + score;
                            scoreview.setText(String.valueOf(scoreteliko));
                            break;
                        }

                    }
                }
                if(!isCorrectAnswer && !isColor && !isAlphabete) {
                    findViewById(lifes == 3 ? R.id.zwi3 : lifes == 2 ? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                    lifes--;
                }
                if(!isCorrectAnswer && (isColor||isAlphabete)) {
                    String filename = currentLanguage.equals("el")?"failure":"wrong";
                    int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                    colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                    colorMediaPlayer.start();
                }
                if(lifes == 0){
                    if(scoreteliko>highScore)
                        showAlertDialog2();
                    else
                        showAlertDialog();
                    return;
                }
                if(questionMediaPlayer != null) {
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
                ArrayList<Answer>answers=(ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer :answers){
                    if(!isColor) {
                        if (answer.getIsValidAnswer() == 1 && (apantisi4.getText().toString().equals(answer.getText()))) {
                            apantisi4.setBackgroundResource(R.drawable.text_cornerprassino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                            isCorrectAnswer = true;
                            if(isAlphabete) {
                                String filename = currentLanguage.equals("el") ? "success" : "correct";
                                int id = GameActivity.this.getResources().getIdentifier(filename, "raw", GameActivity.this.getPackageName());
                                colorMediaPlayer = MediaPlayer.create(GameActivity.this, id);
                                colorMediaPlayer.start();
                            }
                            scoreteliko = scoreteliko + score;
                            scoreview.setText(String.valueOf(scoreteliko));
                            break;
                        } else {
                            apantisi4.setBackgroundResource(R.drawable.text_cornerkokkino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                        }
                    }else{
                        if (answer.getIsValidAnswer() == 1 && (apantisi4.getText().toString().equals(answer.getText()))) {
                            String filename =currentLanguage.equals("el")? "success":"correct";
                            int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                            colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                            colorMediaPlayer.start();
                            isCorrectAnswer = true;
                            scoreteliko = scoreteliko + score;
                            scoreview.setText(String.valueOf(scoreteliko));
                            break;
                        }

                    }
                }
                if(!isCorrectAnswer && !isColor && !isAlphabete) {
                    findViewById(lifes == 3 ? R.id.zwi3 : lifes == 2 ? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                    lifes--;
                }
                if(!isCorrectAnswer && (isColor||isAlphabete)) {
                    String filename = currentLanguage.equals("el")?"failure":"wrong";
                    int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                    colorMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                    colorMediaPlayer.start();
                }
                if(lifes == 0){
                    if(scoreteliko>highScore)
                        showAlertDialog2();
                    else
                        showAlertDialog();
                    return;
                }
                if(questionMediaPlayer != null) {
                    questionMediaPlayer.release();
                }
                goToNextQuestion();
            }
        });

    }

    private void createHashMap() {
        String[] colorAnswersId = new String[]{this.getString(R.string.black), this.getString(R.string.blue),this.getString(R.string.green), this.getString(R.string.red),this.getString(R.string.brown), this.getString(R.string.yellow), this.getString(R.string.white), this.getString(R.string.grey), this.getString(R.string.orange)};

        mapColorsToBackground  = new HashMap<>();
        for(int i = 0; i< colorAnswersId.length; i++){
           mapColorsToBackground.put(colorAnswersId[i].trim(),drawbleAnswersId[i]) ;
        }
    }

    private void showAlertDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogcry);
        dialog.setTitle(R.string.tittledialogcry);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Your score:"+scoreteliko);
        dialog.findViewById(R.id.dialogButtonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.this.finish();

            }
        });



        dialog.setCancelable(false);
        if(!GameActivity.this.isFinishing())
            dialog.show();
    }


    private void showAlertDialog2() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dilaoggelio);
        dialog.setTitle("High score");
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Your score:" + scoreteliko);
        dialog.findViewById(R.id.dialogButtonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.this.finish();

            }
        });
        Preferences.set(this, GAMEACTIVITY, HIGHSCORE, scoreteliko);

        dialog.setCancelable(false);
        if(!GameActivity.this.isFinishing())
            dialog.show();
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
                GameActivity.this.finish();

            }
        });



        dialog.setCancelable(false);
        if(!GameActivity.this.isFinishing())
            dialog.show();
    }




    private int getRandomNumer(int size){
        Random r = new Random();
        return   randomNumer = r.nextInt(size);
    }

    public int getRandomWithExclusion(Random rnd, int start, int end, ArrayList<Integer> exclude) {
        Random rand = new Random();
        int range = end - start + 1;

        int random = rand.nextInt(range);
        while(exclude.contains(random)) {
            random = rand.nextInt(range);
        }

        return random;
    }


    private void goToNextQuestion(){
        questionsCounter++;
        if(questionsCounter == questions.size()) {
            progressStatus=0;
            if(isAlphabete || isColor)
                showAlertDialog3();
           else if(scoreteliko>highScore)
                showAlertDialog2();
            else
                showAlertDialog();
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
                GameActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        randomNumer = getRandomWithExclusion(new Random(), 0 , questions.size()-1 , lastQuestionNumber);
                        if(colorMediaPlayer!=null)
                        colorMediaPlayer.release();
                        //kratame ton arithmo tis proigoumenis erwtisis gia na min ksanapesei!
                        if(isAlphabete){
                            String filename = String.format(currentLanguage.equals("el")?"a%d":"englisha%d",randomNumer);
                            int id = GameActivity.this.getResources().getIdentifier(filename,"raw",GameActivity.this.getPackageName());
                            questionMediaPlayer = MediaPlayer.create(GameActivity.this,id);
                            questionMediaPlayer.start();

                        }
                        if(isColor){
                            String filename = String.format("color%d",randomNumer);
                            final int id = GameActivity.this.getResources().getIdentifier(filename,"drawable",GameActivity.this.getPackageName());
                            GameActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    colorImage.setImageResource(id);
                                }
                            });
                            String filenameSound = String.format(currentLanguage.equals("el")?"colorsound%d":"colorsoundenglish%d",randomNumer);
                            int idSound = GameActivity.this.getResources().getIdentifier(filenameSound,"raw",GameActivity.this.getPackageName());
                            questionMediaPlayer = MediaPlayer.create(GameActivity.this,idSound);
                            questionMediaPlayer.start();
                        }
                        lastQuestionNumber.add(randomNumer);
                        erwtisi.setText(questions.get(randomNumer).getText());
                        ArrayList<Answer>answers=(ArrayList<Answer>) ExternalDbOpenHelper.sharedInstance().getPossibleAnswersForQuestion(questions.get(randomNumer));//apantiseis
                        answers.get(0);
                        apantisi1.setText(answers.get(0).getText());
                        apantisi2.setText(answers.get(1).getText());
                        apantisi3.setText(answers.get(2).getText());
                        apantisi4.setText(answers.get(3).getText());
                        apantisi1.setBackgroundResource(R.drawable.text_corner);
                        apantisi2.setBackgroundResource(R.drawable.text_corner);
                        apantisi3.setBackgroundResource(R.drawable.text_corner);
                        apantisi4.setBackgroundResource(R.drawable.text_corner);
                        if(isColor){
                            apantisi1.setBackgroundResource(mapColorsToBackground.get(apantisi1.getText().toString()));
                            apantisi2.setBackgroundResource(mapColorsToBackground.get(apantisi2.getText().toString()));
                            apantisi3.setBackgroundResource(mapColorsToBackground.get(apantisi3.getText().toString()));
                            apantisi4.setBackgroundResource(mapColorsToBackground.get(apantisi4.getText().toString()));

                        }
                        apantisi1.setEnabled(true);
                        apantisi2.setEnabled(true);
                        apantisi3.setEnabled(true);
                        apantisi4.setEnabled(true);
                        progressStatus = 81;
                        lockLoop = false;

                    }
                });

            }
        }, isColor || isAlphabete?2000:1000);
    }

    @Override
    protected void onDestroy() {
        thread.interrupt();
        thread = null;
        lockLoop = true;
        progressStatus = 0;
        if(timer != null)
        timer.cancel();
        if(questionMediaPlayer != null) {
            questionMediaPlayer.release();
        }
        if(colorMediaPlayer != null){
            colorMediaPlayer.release();
        }
        super.onDestroy();
    }
}



