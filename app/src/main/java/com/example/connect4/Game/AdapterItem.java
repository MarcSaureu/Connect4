package com.example.connect4.Game;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.connect4.R;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterItem extends BaseAdapter implements Serializable {

    private Activity activity;
    private ArrayList<Item> items;

    public AdapterItem (Activity activity, ArrayList<Item> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Item log = items.get(position);

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.position_item, null);
        }

        TextView numCasella =  v.findViewById(R.id.Casella);
        numCasella.setText(log.getTitle());

        TextView timeUsed =  v.findViewById(R.id.TempsOcupat);
        timeUsed.setText(log.getDescription());

        TextView TimeLeft =  v.findViewById(R.id.TempsRestant);
        TimeLeft.setText(log.getTemps());

        return v;
    }
}
