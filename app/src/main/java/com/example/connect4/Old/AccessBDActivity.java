package com.example.connect4.Old;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.connect4.R;

public class AccessBDActivity extends AppCompatActivity implements FragmentList.GameListener, View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old);
        getSupportActionBar().hide();

        Button exit = findViewById(R.id.BackButton2);

        FragmentList fragmentList = (FragmentList) getFragmentManager().findFragmentById(R.id.FrgListOLD);

        exit.setOnClickListener(this);
        fragmentList.setGameListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.BackButton2) {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPartidaSelected(String id) {
        FragmentDetail fragmentDetail = (FragmentDetail) getFragmentManager().findFragmentById(R.id.FrgDetailOLD);
        boolean isDetail = (fragmentDetail != null && fragmentDetail.isInLayout());

        ResulPFrag resulPFrag = (ResulPFrag) getFragmentManager().findFragmentById(R.id.FrgResultOLD);
        boolean isResult = (resulPFrag != null && resulPFrag.isInLayout());

        if(isDetail && isResult){
            fragmentDetail.ViewGame(id);
            resulPFrag.getImage(id);
        }else{
            Intent i = new Intent(this, DetailRegActivity.class);
            i.putExtra(getString(R.string.id_key), id);
            startActivity(i);
        }

    }
}
