package com.example.connect4.Old;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.example.connect4.DDBB.PartidaSQLiteHelper;
import com.example.connect4.R;


public class FragmentDetail extends Fragment {

    String info;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_old, container, false);

        get(savedInstanceState);

        return view;
    }

    private void get(Bundle savedInstanceState){
        TextView txt = view.findViewById(R.id.Info);

        if(savedInstanceState != null && (savedInstanceState.getSerializable("txt") != null)){
            info = (String) savedInstanceState.getSerializable("txt");
            txt.setText(info);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ViewGame(String id){
        TextView txt = getView().findViewById(R.id.Info);
        String[] gameID = new String[]{id};

        PartidaSQLiteHelper ddbb = new PartidaSQLiteHelper(getContext(), "Partides", null, 1);
        SQLiteDatabase db = ddbb.getReadableDatabase();

        String[] campos = new String[]{"_id", "alias", "date", "grillSize", "timeControl", "usedTime", "result"};
        Cursor c = db.query(
                "Partides", campos, "_id=?", gameID, null,null,null);
        c.moveToFirst();
        info = c.getString(1) + "\n" + c.getString(2) + "\n" + c.getString(3) + "\n" +
                c.getString(4) + "\n" + c.getString(5) + "\n" + c.getString(6);

        txt.setText(info);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("txt", info);
    }
}
