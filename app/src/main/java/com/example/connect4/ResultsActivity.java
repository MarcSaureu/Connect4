package com.example.connect4;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.DDBB.PartidaSQLiteHelper;
import com.example.connect4.Game.GameActivity;
import com.example.connect4.Preferences.PreferencesActivity;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        EditText date = findViewById(R.id.Dia_hora);
        EditText log = findViewById(R.id.Log_values);
        EditText email = findViewById(R.id.E_mail);

        Button sendEmail = findViewById(R.id.Enviar_email);
        Button restart = findViewById(R.id.Nova_Partida);
        Button exit2 = findViewById(R.id.exit_button2);

        sendEmail.setOnClickListener(this);
        exit2.setOnClickListener(this);
        restart.setOnClickListener(this);

        //Valors del Intent
        Intent intent = getIntent();
        String Status = intent.getStringExtra("statuskey");
        int usedTime = intent.getIntExtra("usedTime", 0);

        //Valors de la SharedPreferences
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String Alias = mySharedPreferences.getString(getString(R.string.Alias), "P1");
        String Size = mySharedPreferences.getString(getString(R.string.Graella), "7");
        boolean timeControl = mySharedPreferences.getBoolean(getString(R.string.Control),false);

        Date dat = new Date();

        date.setText(dat.toString());

        String Log = BuildLog(Alias, Size, Status, usedTime);
        email.requestFocus();

        log.setText(Log);


        //Base de Dades
        if(savedInstanceState == null){
           insert(Alias, Size, Status, dat, timeControl, usedTime);
        }

    }

    private void insert(String Alias, String Size, String Status, Date dat, boolean timeControl, int usedTime){
        //PartidaSQLiteHelper ddbb = new PartidaSQLiteHelper(this, "Partides", null, 1);
        PartidaSQLiteHelper ddbb = new PartidaSQLiteHelper(this, "Partides2", null, 2);
        SQLiteDatabase db = ddbb.getWritableDatabase();
        if(db != null){
            insertDB(db,Alias, Size, Status, dat, timeControl, usedTime);
            Toast.makeText(this, R.string.AddedDDBB,Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void insertDB(SQLiteDatabase db, String alias, String size, String status, Date dat, boolean timeControl, int usedTime) {
        ContentValues Register = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("es", "ES"));

        Register.put("alias", alias); //Afegim Alias
        Register.put("date", dateFormat.format(dat));//Afegim Data
        Register.put("grillSize", size);//Afegim Size
        Register.put("timeControl", timeControl);//Afegim flag de temps
        Register.put("usedTime", usedTime);
        //Register.put("result", status);//Afegim Status
        Bitmap icon;
        if(status.equals("WIN")){
            icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.victoria);
        }else if(status.equals("DRAW")){
            icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.empate);
        }else if(status.equals("LOSE")){
            icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.derrota);
        }else{
            icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.tiempoagotado);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG,0,outputStream);
        byte[] blob = outputStream.toByteArray();
        Register.put("result", blob);//Afegim Status

        db.insert("Partides2", null, Register);

        //db.insert("Partides", null, Register);
    }

    private String BuildLog(String alias, String size, String status, int usedtime) {
        return alias + " | Mida Graella: "+ size + " | Temps total: "+ usedtime+"s | " + status;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.config:
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {
        EditText edtdate = findViewById(R.id.Dia_hora);
        EditText edtemail = findViewById(R.id.E_mail);
        EditText edtLog = findViewById(R.id.Log_values);

        switch (v.getId()){
            case R.id.Enviar_email:
                Intent in = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + edtemail.getText().toString()));
                in.putExtra(Intent.EXTRA_SUBJECT, "Log - " + edtdate.getText().toString());
                in.putExtra(Intent.EXTRA_TEXT, edtLog.getText().toString());
                startActivity(in);
                break;

            case R.id.Nova_Partida:
                Intent intent1 = new Intent(ResultsActivity.this, GameActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.exit_button2:
                finish();
                break;
        }
    }
}