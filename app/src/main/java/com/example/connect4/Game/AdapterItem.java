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

    protected Activity activity;
    protected ArrayList<Item> items;

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

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.position_item, null);
        }

        Item dir = items.get(position);

        TextView title =  v.findViewById(R.id.Casella);
        title.setText(dir.getTitle());

        TextView description =  v.findViewById(R.id.TempsOcu);
        description.setText(dir.getDescription());

        TextView time =  v.findViewById(R.id.Tempsrestant);
        time.setText(dir.getTemps());

        return v;
    }
}
