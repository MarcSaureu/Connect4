package com.example.connect4.DDBB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.connect4.R;

import java.io.ByteArrayOutputStream;


public class PartidaSQLiteHelper extends SQLiteOpenHelper {
    Context context;
    String sqlCreate = "CREATE TABLE Partides " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "alias TEXT, " +
            "date TEXT, " +
            "grillSize TEXT, " +
            "timeControl BOOLEAN, " +
            "usedTime INTEGER, " +
            "result TEXT)";

    String sqlCreate2 = "CREATE TABLE Partides2 " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "alias TEXT, " +
            "date TEXT, " +
            "grillSize TEXT, " +
            "timeControl BOOLEAN, " +
            "usedTime INTEGER, " +
            "result BLOB)";

    public PartidaSQLiteHelper(Context context,String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(sqlCreate);
       db.execSQL(sqlCreate2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        upgrade(db);
        db.execSQL("DROP TABLE IF EXISTS Partides");
        db.execSQL(sqlCreate);
    }

    public void upgrade(SQLiteDatabase db){
        for (long i = 1; i < db.getMaximumSize()+1; i++){
            String id = Long.toString(i);
            String[] gameID = new String[]{id};
            String[] campos = new String[]{"_id", "alias", "date", "grillSize", "timeControl", "usedTime","result"};
            Cursor c = db.query("Partides", campos, "_id=?",gameID, null,null,null);
            c.moveToFirst();
            ContentValues Register = new ContentValues();

            Register.put("alias", c.getString(1)); //Afegim Alias
            Register.put("date", c.getString(2));//Afegim Data
            Register.put("grillSize", c.getString(3));//Afegim Size
            Register.put("timeControl", c.getString(4));//Afegim flag de temps
            Register.put("usedTime", c.getString(5));

            String result = c.getString(6);
            Bitmap icon;
            if(result.equals("WIN")){
                icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.victoria);
            }else if(result.equals("DRAW")){
                icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.empate);
            }else if(result.equals("LOSE")){
                icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.derrota);
            }else{
                icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.tiempoagotado);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.PNG,0,outputStream);
            byte[] blob = outputStream.toByteArray();
            Register.put("result", blob);//Afegim Status

            db.insert("Partides2", null, Register);
        }
    }
}
