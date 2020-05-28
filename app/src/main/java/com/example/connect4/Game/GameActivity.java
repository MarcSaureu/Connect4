package com.example.connect4.Game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.Logic.Position;
import com.example.connect4.R;

import java.util.Date;

public class GameActivity extends AppCompatActivity implements GridFrag.OnPositionSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        GridFrag grid = (GridFrag) getFragmentManager().findFragmentById(R.id.game_frag);

        grid.setOnPositionListener(this);
    }

    @Override
    public void onPositionSelected(Position pos, Date start, Date end, Long timerest, boolean time){
        LogFrag LogFrag = (LogFrag) getFragmentManager().findFragmentById(R.id.log_frag);
        boolean logexists = (LogFrag != null && LogFrag.isInLayout());

        if(logexists)
            LogFrag.showPosition(pos,start,end, timerest,time);
    }
}
