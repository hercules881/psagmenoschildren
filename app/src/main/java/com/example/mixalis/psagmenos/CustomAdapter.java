package com.example.mixalis.psagmenos;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;

import static android.view.View.*;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by mixalis on 13/11/2015.
 */
class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private  String[] itemname;

    public CustomAdapter(EnarxiActivity context, String[] itemname) {
        super(context, R.layout.enarxi, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;

    }

    public View getView( final int position, final View view, final ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        final View rowView=inflater.inflate(R.layout.listviewenarxi, null, true);
        final TextView text1 = (TextView) rowView.findViewById(R.id.text);
       // final TextView text2 = (TextView) rowView.findViewById(R.id.text1);

        TextView text2 = (TextView) super.getView(position, view, parent);


        //text2.setVisibility(GONE);
        // rowView.setClickable(true);

        text1.setText(itemname[position]);
        return rowView;

    };



}
