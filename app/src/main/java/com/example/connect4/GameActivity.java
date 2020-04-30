package com.example.connect4;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.Logic.Game;

public class GameActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ImageAdapter table;
    private Game game;
    private int size;
    private boolean time;
    private Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        size = intent.getIntExtra("graellakey", -1);
        time = intent.getBooleanExtra("tempskey", false);
        data.putInt("midakey", size);
        data.putString("aliaskey", intent.getStringExtra("aliaskey"));
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("game",  game);
        state.putSerializable("table",table);
    }
}
