package com.example.mixalis.psagmenos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Database.Question;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.gameshapes);

        String epelexes = intent.getExtras().getString("epelexes");
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(epelexes);

        apantisi1 = (ImageButton) findViewById(R.id.apantisi1);
        apantisi2 = (ImageButton) findViewById(R.id.apantisi2);
        apantisi3 = (ImageButton) findViewById(R.id.apantisi3);
        apantisi4 = (ImageButton) findViewById(R.id.apantisi4);
        erwtisi = (TextView) findViewById(R.id.erwtisi);
        imageshape = (ImageView) findViewById(R.id.imageshapesCategory);


}



}