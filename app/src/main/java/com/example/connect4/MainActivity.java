package com.example.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.connect4.Preferences.PreferencesActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button help = findViewById(R.id.help_button);
        Button start = findViewById(R.id.start_button);
        Button exit = findViewById(R.id.exit_button);
        Button consult = findViewById(R.id.database_button);

        help.setOnClickListener(this);
        start.setOnClickListener(this);
        exit.setOnClickListener(this);
        consult.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.help_button:
                Intent intent = new Intent (MainActivity.this,HelpActivity.class);
                startActivity(intent);
                break;

            case R.id.start_button:
                Intent intent1 = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.exit_button:
                finish();
                break;

            case R.id.database_button:
                break;
        }
    }
}
