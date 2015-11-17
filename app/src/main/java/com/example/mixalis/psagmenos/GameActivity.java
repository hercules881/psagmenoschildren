package com.example.mixalis.psagmenos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Database.Answer;
import Database.ExternalDbOpenHelper;
import Database.Question;
import Misc.Preferences;

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
    ExternalDbOpenHelper dbHelper;
    TextView erwtisi;
    TextView scoreview;
    int questionsCounter = 0;
    int highScore;
    public final static String GAMEACTIVITY = "gameactivity";
    public final static String HIGHSCORE = "highscore";
    TextView highScoreText;
    boolean lockLoop = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        progressBar = (ProgressBar) findViewById(R.id.progressbar1);
        erwtisi = (TextView) findViewById(R.id.erwtisi);
        scoreview = (TextView) findViewById(R.id.score);
        highScoreText = (TextView) findViewById(R.id.highscore);

        apantisi1 = (Button) findViewById(R.id.apantisi1);
        apantisi2 = (Button) findViewById(R.id.apantisi2);
        apantisi3 = (Button) findViewById(R.id.apantisi3);
        apantisi4 = (Button) findViewById(R.id.apantisi4);

        // ProgressBar pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        Intent intent = getIntent();
        String fName = intent.getStringExtra("firstName");
        String lName = intent.getStringExtra("lastName");
        epelexes=fName;

        highScore = (int) Preferences.get(this, GAMEACTIVITY, HIGHSCORE, 0);
        highScoreText.setText(String.valueOf(highScore));

        dbHelper = new ExternalDbOpenHelper(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                questions= (ArrayList<Question>) dbHelper.getQuestionForCategory(epelexes);   //erwtiseis
                randomNumer = getRandomNumer(questions.size()-1);
                //kratame ton arithmo tis proigoumenis erwtisis gia na min ksanapesei!
                lastQuestionNumber.add(randomNumer);
                questions.get(randomNumer - 1);
                lockLoop = false;
                GameActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        erwtisi.setText(questions.get(randomNumer - 1).getText());
                        ArrayList<Answer> answers = (ArrayList<Answer>) dbHelper.getPossibleAnswersForQuestion(questions.get(randomNumer - 1));//apantiseis
                        answers.get(0);
                        apantisi1.setText(answers.get(0).getText());
                        apantisi2.setText(answers.get(1).getText());
                        apantisi3.setText(answers.get(2).getText());
                        apantisi4.setText(answers.get(3).getText());
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

                                if(questionsCounter == 10) {
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

                                //kratame ton arithmo tis proigoumenis erwtisis gia na min ksanapesei!
                                lastQuestionNumber.add(randomNumer);
                                erwtisi.setText(questions.get(randomNumer).getText());
                                ArrayList<Answer>answers=(ArrayList<Answer>) dbHelper.getPossibleAnswersForQuestion(questions.get(randomNumer));//apantiseis
                                answers.get(0);
                                apantisi1.setText(answers.get(0).getText());
                                apantisi2.setText(answers.get(1).getText());
                                apantisi3.setText(answers.get(2).getText());
                                apantisi4.setText(answers.get(3).getText());
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
        thread.start();

        apantisi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;

                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer-1));
                ArrayList<Answer> answers = (ArrayList<Answer>) dbHelper.getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer : answers) {
                    if (answer.getIsValidAnswer() == 1 && (apantisi1.getText().toString().equals(answer.getText()))) {
                        apantisi1.setBackgroundResource(R.drawable.text_cornerprassino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                       isCorrectAnswer = true;
                        scoreteliko=scoreteliko+score;
                        scoreview.setText(String.valueOf(scoreteliko));
                        break;
                    } else {
                        apantisi1.setBackgroundResource(R.drawable.text_cornerkokkino);   //setBackgroundColor(GameActivity.this.getResources().getColor(R.color.rigth_answer_green));
                    }

                }
                if(!isCorrectAnswer) {
                    findViewById(lifes == 3 ? R.id.zwi3 : lifes == 2 ? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                    lifes--;
                }
                if(lifes == 0){
                   if(scoreteliko>highScore)
                    showAlertDialog2();
                    else
                   showAlertDialog();
                    return;
                }

                goToNextQuestion();

            }
        });

        apantisi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;


                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer-1));
                ArrayList<Answer>answers=(ArrayList<Answer>) dbHelper.getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer :answers){
                    if(answer.getIsValidAnswer()==1 && (apantisi2.getText().toString().equals(answer.getText()))){
                        apantisi2.setBackgroundResource(R.drawable.text_cornerprassino);
                        isCorrectAnswer = true;
                        scoreteliko=scoreteliko+score;
                        scoreview.setText(String.valueOf(scoreteliko));
                        break;
                    }
                    else {
                        apantisi2.setBackgroundResource(R.drawable.text_cornerkokkino);

                    }

                }

                if(!isCorrectAnswer) {
                    findViewById(lifes == 3 ? R.id.zwi3 : lifes == 2 ? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                    lifes--;
                }
                if(lifes == 0){
                    if(scoreteliko>highScore)
                        showAlertDialog2();
                    else
                        showAlertDialog();
                    return;
                }

                goToNextQuestion();
            }
        });

        apantisi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;


                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer-1));
                ArrayList<Answer>answers=(ArrayList<Answer>) dbHelper.getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer :answers){
                    if(answer.getIsValidAnswer()==1 && (apantisi3.getText().toString().equals(answer.getText()))){
                        apantisi3.setBackgroundResource(R.drawable.text_cornerprassino);
                        isCorrectAnswer = true;
                        scoreteliko=scoreteliko+score;
                        scoreview.setText(String.valueOf(scoreteliko));
                        break;
                    }
                    else {
                        apantisi3.setBackgroundResource(R.drawable.text_cornerkokkino);


                    }
                }
                if(!isCorrectAnswer) {
                    findViewById(lifes == 3 ? R.id.zwi3 : lifes == 2 ? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                    lifes--;
                }
                if(lifes == 0){
                    if(scoreteliko>highScore)
                        showAlertDialog2();
                    else
                        showAlertDialog();
                    return;
                }

                goToNextQuestion();
            }
        });

        apantisi4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockLoop = true;

                boolean isCorrectAnswer = false;
                Question question = (questions.get(randomNumer-1));
                ArrayList<Answer>answers=(ArrayList<Answer>) dbHelper.getPossibleAnswersForQuestion(question);//apantiseis
                for (Answer answer :answers){
                    if(answer.getIsValidAnswer()==1 && (apantisi4.getText().toString().equals(answer.getText()))){
                        apantisi4.setBackgroundResource(R.drawable.text_cornerprassino);
                        isCorrectAnswer = true;
                        scoreteliko=scoreteliko+score;
                        scoreview.setText(String.valueOf(scoreteliko));
                        break;
                    }
                    else {
                        apantisi4.setBackgroundResource(R.drawable.text_cornerkokkino);


                    }
                }
                if(!isCorrectAnswer) {
                    findViewById(lifes == 3 ? R.id.zwi3 : lifes == 2 ? R.id.zwi2 : R.id.zwi1).setVisibility(View.INVISIBLE);
                    lifes--;
                }
                if(lifes == 0){
                    if(scoreteliko>highScore)
                        showAlertDialog2();
                    else
                        showAlertDialog();
                    return;
                }

                goToNextQuestion();
            }
        });

    }

    private void showAlertDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogcry);
        dialog.setTitle("Λυπάμαι χάσατε..");
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





    private int getRandomNumer(int size){
            Random r = new Random();
          return   randomNumer = r.nextInt(size);
        }

    public int getRandomWithExclusion(Random rnd, int start, int end, ArrayList<Integer> exclude) {
        Random rand = new Random();
        int range = end - start + 1;

        int random = rand.nextInt(range) + 1;
        while(exclude.contains(random)) {
            random = rand.nextInt(range) + 1;
        }

        return random;
    }


    private void goToNextQuestion(){
        questionsCounter++;
        if(questionsCounter == 10) {
            progressStatus=0;
            if(scoreteliko>highScore)
                showAlertDialog2();
            else
                showAlertDialog();
            return;
        }
        apantisi1.setEnabled(false);
        apantisi2.setEnabled(false);
        apantisi3.setEnabled(false);
        apantisi4.setEnabled(false);
        Timer timer = new Timer();
                                    /* Duration of wait */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        randomNumer = getRandomWithExclusion(new Random(), 1 , questions.size() , lastQuestionNumber);

                        //kratame ton arithmo tis proigoumenis erwtisis gia na min ksanapesei!
                        lastQuestionNumber.add(randomNumer);
                        erwtisi.setText(questions.get(randomNumer-1).getText());
                        ArrayList<Answer>answers=(ArrayList<Answer>) dbHelper.getPossibleAnswersForQuestion(questions.get(randomNumer-1));//apantiseis
                        answers.get(0);
                        apantisi1.setText(answers.get(0).getText());
                        apantisi2.setText(answers.get(1).getText());
                        apantisi3.setText(answers.get(2).getText());
                        apantisi4.setText(answers.get(3).getText());
                        apantisi1.setBackgroundResource(R.drawable.text_corner);
                        apantisi2.setBackgroundResource(R.drawable.text_corner);
                        apantisi3.setBackgroundResource(R.drawable.text_corner);
                        apantisi4.setBackgroundResource(R.drawable.text_corner);
                        apantisi1.setEnabled(true);
                        apantisi2.setEnabled(true);
                        apantisi3.setEnabled(true);
                        apantisi4.setEnabled(true);
                        progressStatus = 81;
                        lockLoop = false;

                    }
                });

            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        thread.interrupt();
        super.onDestroy();
    }
}



