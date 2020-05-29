package com.example.connect4.Game;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.connect4.Logic.Position;
import com.example.connect4.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogFrag extends Fragment {

    View view;
    AdapterItem adapter;
    ArrayList<Item> items;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log, container, false);

        buildLogTitle();
        buildLog(savedInstanceState);

        return view;
    }

    private void buildLogTitle() {
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        TextView log = view.findViewById(R.id.loger);

        String Alias = mySharedPreferences.getString(getString(R.string.Alias), "P1");
        String Size = mySharedPreferences.getString(getString(R.string.Graella), "7");
        boolean time = mySharedPreferences.getBoolean(getString(R.string.Control), false);

        log.setText("LOG..." + "\n" + "Alias = " + Alias + "; Mida grealla = " + Size + "\n");

        if (time){
            log.append("Control de Temps");
        }else{
            log.append("Sense Control de Temps");
        }
    }


    private void buildLog(Bundle savedInstanceState) {

        if(savedInstanceState != null && (savedInstanceState.getSerializable("adapter") != null)){
            items = (ArrayList<Item>) savedInstanceState.getSerializable("itemArray");
            adapter = (AdapterItem) savedInstanceState.getSerializable("adapter");
        } else {
            items = new ArrayList<Item>();
            adapter = new AdapterItem(getActivity(), items);
        }

        ListView listview =  view.findViewById(R.id.milista);
        listview.setAdapter(adapter);
    }

    public void showPosition(Position pos, Date start, Date end, Long timerest, boolean time){

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String Text1 = ("Casella Ocupada:    (" + (pos.getColumn() + 1) +", "+ (pos.getRow() + 1) + ") \n" );
        String Text2 = ("Temps inici: "+dateFormat.format(start)+";Temps final: "+dateFormat.format(end));
        String Text3 = "Temps restant: " + timerest.toString() + "secs";

        if(time){
            Item item = new Item(Text1,Text2,Text3);
            items.add(item);
        }else{
            Item item = new Item(Text1,Text2);
            items.add(item);
        }

        adapter.notifyDataSetChanged();

    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("adapter",  adapter);
        state.putSerializable("itemArray",  items);
    }
}
