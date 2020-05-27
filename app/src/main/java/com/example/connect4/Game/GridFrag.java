package com.example.connect4.Game;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.connect4.ImageAdapter;
import com.example.connect4.Logic.Game;
import com.example.connect4.Logic.Position;
import com.example.connect4.Logic.Status;
import com.example.connect4.R;
import com.example.connect4.ResultsActivity;

import java.util.Date;

public class GridFrag extends Fragment implements AdapterView.OnItemClickListener {

    private ImageAdapter table;
    private Game game;
    private int size;
    private boolean time;
    Date start = new Date();
    Date end;

    View view;
    OnPositionSelectedListener mylistener;

    public interface OnPositionSelectedListener {
        void onPositionSelected(Position pos, Date start, Date end, Long Time, boolean time);
    }


    public void setOnPositionListener(OnPositionSelectedListener listener){
        this.mylistener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_grid, container, false);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        time = mySharedPreferences.getBoolean(getString(R.string.Control), false);
        size = Integer.parseInt(mySharedPreferences.getString(getString(R.string.Graella), "7"));

        manageTable(savedInstanceState);

        return view;

    }
        @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int col = position % size;
            Position pos = game.drop(col);

            table.setChip(R.drawable.redchip, pos.getRow(), pos.getColumn());
            table.notifyDataSetChanged();

            end = new Date();
            long timerest = ((new Date().getTime() - game.getStartTime()) / 1000) - game.getGameTime();
            if(mylistener!=null)
                mylistener.onPositionSelected(pos, start, end, Math.abs(timerest), time);

            timeControl();
            if (game.checkForFinish()) {
                GameFinished(1);
            } else {
                start = new Date();
                //JUGARA LA MAQUINA
                col = game.playOpponent();
                pos = game.drop(col);

                table.setChip(R.drawable.yellowchip, pos.getRow(), pos.getColumn());
                table.notifyDataSetChanged();

                end = new Date();
                timerest = ((new Date().getTime() - game.getStartTime()) / 1000) - game.getGameTime();
                if(mylistener!=null)
                    mylistener.onPositionSelected(pos, start, end, Math.abs(timerest), time);

                timeControl();
                if (game.checkForFinish())
                    GameFinished(0);
            }
            start = new Date();
    }
    private void GameFinished(int player){
        if (player == 1){
            Bundle data = new Bundle();
            Intent next = new Intent( getActivity(), ResultsActivity.class);
            int time = (int)(new Date().getTime() - game.getStartTime()) / 1000;

            if (game.getStatus() == Status.PLAYER1_WINS) {
                data.putString("statuskey", "HAS GUANYAT");
            }
            if (game.getStatus() == Status.DRAW) {
                data.putString("statuskey", "HAS EMPATAT");
            }
            if (game.getStatus() == Status.TIMEOVER) {
                data.putString("statuskey", "S'HA ACABAT EL TEMPS, HAS EMPATAT");
            }
            data.putInt("usedTime", time);
            next.putExtras(data);

            startActivity(next);
            getActivity().finish();
        } else {
            Bundle data = new Bundle();
            Intent next = new Intent( getActivity(), ResultsActivity.class);
            int time = (int)(new Date().getTime() - game.getStartTime()) / 1000;

            if (game.getStatus() == Status.PLAYER2_WINS){
                data.putString("statuskey", "HAS PERDUT");
            }
            if (game.getStatus() == Status.DRAW){
                data.putString("statuskey", "HAS EMPATAT");
            }
            if (game.getStatus() == Status.TIMEOVER){
                data.putString("statuskey", "S'HA ACABAT EL TEMPS, HAS EMPATAT");
            }
            data.putInt("usedTime", time);
            next.putExtras(data);

            startActivity(next);
            getActivity().finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void manageTable(Bundle savedInstanceState){

        if(savedInstanceState != null && (savedInstanceState.getSerializable("game") != null)){
            game = (Game) savedInstanceState.getSerializable("game");
            table = (ImageAdapter) savedInstanceState.getSerializable("table");
        } else {
            game = new Game(size, 4);
            table = new ImageAdapter(getContext());
            table.setGrid(size);
        }

        GridView gridView = view.findViewById(R.id.grid_view);
        timeControl();

        gridView.setAdapter(table);
        gridView.setNumColumns(size);
        gridView.setOnItemClickListener(this);
    }


    private void timeControl(){
        TextView text = view.findViewById(R.id.clock);

        if (time)
            game.manageTime();

        long time = ((new Date().getTime() - game.getStartTime()) / 1000) - game.getGameTime();
        if(time>=0)
            text.setText("0 seconds");
        else
            text.setText(Math.abs(time)  + " seconds");
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("game",  game);
        state.putSerializable("table", table);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mylistener = (OnPositionSelectedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnPositionSelectedListener");
        }
    }


}
