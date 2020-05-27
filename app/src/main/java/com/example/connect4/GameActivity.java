package com.example.connect4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.Logic.Game;
import com.example.connect4.Logic.Position;
import com.example.connect4.Logic.Status;

import java.util.Date;

public class GameActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ImageAdapter table;
    private Game game;
    private int size;
    private boolean time;
    private Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        TextView text = findViewById(R.id.clock);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        time = mySharedPreferences.getBoolean(getString(R.string.Control), false);
        size = Integer.parseInt(mySharedPreferences.getString(getString(R.string.Graella), "7"));
        data.putInt("midakey", size);
        data.putString("aliaskey", intent.getStringExtra("aliaskey"));

        if(savedInstanceState != null && (savedInstanceState.getSerializable("game") != null)){
            game = (Game) savedInstanceState.getSerializable("game");
            table = (ImageAdapter) savedInstanceState.getSerializable("table");
        }else{
            game = new Game(size, 4);
            table = new ImageAdapter(this);
            table.setGrid(size);
        }
        text.setText(game.getGameTime() + "seconds");
        GridView gridView = findViewById(R.id.grid_view);

        gridView.setAdapter(table);
        gridView.setNumColumns(size);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int col = position % size;
        Position pos = game.drop(col);
        TextView text = findViewById(R.id.clock);

        table.setChip(R.drawable.redchip, pos.getRow(), pos.getColumn());
        table.notifyDataSetChanged();

        if(time){
            game.manageTime();
        }
        long temps = new Date().getTime();
        text.setText(Math.abs(((temps - game.getStartTime()) / 1000) - game.getGameTime()) + " seconds");

        if(game.checkForFinish()){
            Intent next = new Intent(GameActivity.this, ResultsActivity.class);
            int time = (int)(new Date().getTime() - game.getStartTime()) / 1000;
            if(game.getStatus() == Status.PLAYER1_WINS) data.putString("statuskey", "Has Guanyat");
            if (game.getStatus() == Status.DRAW) data.putString("statuskey", "HAS EMPATAT");
            if (game.getStatus() == Status.TIMEOVER) data.putString("statuskey", "S'HA ACABAT EL TEMPS, HAS EMPATAT");
            data.putInt("usedTime",time);
            next.putExtras(data);
            startActivity(next);
            finish();
        }else{
            col = game.playOpponent();
            pos = game.drop(col);

            table.setChip(R.drawable.yellowchip, pos.getRow(), pos.getColumn());
            table.notifyDataSetChanged();
            if (time)
                game.manageTime();

            if (game.checkForFinish()) {
                Intent next = new Intent(GameActivity.this, ResultsActivity.class);
                int time = (int)(new Date().getTime() - game.getStartTime()) / 1000;
                if (game.getStatus() == Status.PLAYER2_WINS) data.putString("statuskey", "HAS PERDUT");
                if (game.getStatus() == Status.DRAW) data.putString("statuskey", "HAS EMPATAT");
                if (game.getStatus() == Status.TIMEOVER) data.putString("statuskey", "S'HA ACABAT EL TEMPS, HAS EMPATAT");
                data.putInt("usedTime",time);
                next.putExtras(data);
                startActivity(next);
                finish();
            }
        }


    }
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("game",  game);
        state.putSerializable("table",table);
    }
}
