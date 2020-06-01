package com.example.connect4.Old;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.connect4.DDBB.PartidaSQLiteHelper;
import com.example.connect4.R;

public class ResulPFrag extends Fragment {
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
        view = inflater.inflate(R.layout.fragment_result_old, container, false);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImage(String id){
        ImageView imageview = view.findViewById(R.id.ResultInfo);
        String[] gameID = new String[]{id};

        PartidaSQLiteHelper ddbb = new PartidaSQLiteHelper(getContext(), "Partides", null, 1);
        SQLiteDatabase db = ddbb.getReadableDatabase();

        String[] campos = new String[]{"_id","result"};
        Cursor c = db.query(
                "Partides", campos, "_id=?", gameID, null,null,null);
        c.moveToFirst();
        String result = c.getString(1);
        if(result.equals("WIN")){
            imageview.setImageResource(R.drawable.victoria);
        }else if(result.equals("DRAW")){
            imageview.setImageResource(R.drawable.empate);
        }else if(result.equals("LOSE")){
            imageview.setImageResource(R.drawable.derrota);
        }else{
            imageview.setImageResource(R.drawable.tiempoagotado);
        }
    }

}
