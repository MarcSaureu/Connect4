package com.example.connect4.Old;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.R;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_old);
        getSupportActionBar().hide();

        FragmentDetail detail = (FragmentDetail)getFragmentManager().findFragmentById(R.id.FrgListOLD);

        detail.ViewGame(getIntent().getStringExtra(getString(R.string.id_key)));

        Button exit = findViewById(R.id.BackButton3);

        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BackButton3:
                finish();
                break;
        }
    }
}
