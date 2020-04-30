package com.example.connect4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

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

        Intent intent = getIntent();
        log.setText(intent.getStringExtra("aliaskey")+" Mida Graella: ");
        log.append((intent.getIntExtra("midakey",-1))+ " "+ intent.getStringExtra("statuskey"));
        date.setText(new Date().toString());
        email.requestFocus();


        sendEmail.setOnClickListener(this);
        exit2.setOnClickListener(this);
        restart.setOnClickListener(this);


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
                Intent intent1 = new Intent(ResultsActivity.this, ConfigActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.exit_button2:
                finish();
                break;
        }
    }
}