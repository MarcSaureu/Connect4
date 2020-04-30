package com.example.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button startgame = findViewById(R.id.StartGameButton);
        Button exit3 = findViewById(R.id.exit_button3);

        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText alias = findViewById(R.id.Alias);
                RadioGroup graella = findViewById(R.id.Graella);
                CheckBox temps = findViewById(R.id.checkBox);

                int selected = graella.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selected);


                Bundle data = new Bundle();

                data.putString(getString(R.string.aliaskey), alias.getText().toString());
                if(selected == -1){
                    data.putInt("graellakey", 7);
                }else{
                    data.putInt(getString(R.string.graellakey), Integer.parseInt(radioButton.getText().toString()));
                }
                data.putBoolean(getString(R.string.tempskey), temps.isChecked());

                Intent intent = new Intent (ConfigActivity.this,GameActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                finish();
            }
        });
        exit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
