package com.example.connect4.Old;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.R;

public class OldActivity extends AppCompatActivity implements FragmentList.GameListener, View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old);
        getSupportActionBar().hide();

        FragmentList fragmentList = (FragmentList) getFragmentManager().findFragmentById(R.id.FrgListOLD);

        fragmentList.setGameListener(this);

        Button exit = findViewById(R.id.BackButton2);

        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BackButton2:
                finish();
                break;
        }
    }

    @Override
    public void onParidaSelected(String id) {

    }
}
