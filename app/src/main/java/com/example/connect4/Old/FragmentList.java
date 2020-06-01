package com.example.connect4.Old;


import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.RequiresApi;

import com.example.connect4.DDBB.PartidaSQLiteHelper;
import com.example.connect4.R;


public class FragmentList extends ListFragment {
    private GameListener listener;

    public interface GameListener{
        void onPartidaSelected(String id);
    }
    public void setGameListener(GameListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PartidaSQLiteHelper ddbb = new PartidaSQLiteHelper(getContext(),"Partides", null, 1);

        SQLiteDatabase db = ddbb.getReadableDatabase();

        //String[] camps = new String[] {"_id", "alias", "date", "result"};
        String[] camps = new String[] {"_id", "alias", "date"};
        Cursor cursor = db.query("Partides", camps,null,null,null,null,null);

        //String[] from = new String[] {"alias", "date", "result"};
        String[] from = new String[] {"alias", "date"};
        //int [] to = new int[] {R.id.data1, R.id.data2, R.id.data3};
        int [] to = new int[] {R.id.data1, R.id.data2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.fragment_game_data, cursor, from, to, 0);

        this.setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        if(listener!=null){
            String stringId = String.valueOf(id);
            listener.onPartidaSelected(stringId);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (GameListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    " must implement OnPartidaSelected()");
        }
    }
}
