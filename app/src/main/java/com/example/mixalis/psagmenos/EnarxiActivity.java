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
import android.widget.ListView;
import android.widget.TextView;

import static android.media.MediaPlayer.create;

/**
 * Created by mixalis on 13/11/2015.
 */
public class EnarxiActivity extends Activity {
    ListView listView;

    String[] katigories = new String[]{
            "Μαθηματικά",
            "Γλώσσα",
            "Χρώματα"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enarxi);

        CustomAdapter adapter = new CustomAdapter(this, katigories);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // Intent myIntent = new Intent(EnarxiActivity.this, GameActivity.class);
                   // EnarxiActivity.this.startActivity(myIntent);
                    Intent i = new Intent(getApplicationContext(), GameActivity.class);
                   // i.putExtra("epelexes","Γεωγραφία");
                    i.putExtra("firstName", katigories[position]);
                    startActivity(i);
        }
        });
    }

}
