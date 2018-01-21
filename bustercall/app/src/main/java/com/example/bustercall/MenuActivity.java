package com.example.bustercall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by 박주현 on 2017-06-21.
 */

public class MenuActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void backbutton(View v){
        finish();
    }
    public void provision(View v){
        Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
        startActivity(intent);
    }
    public void help(View v){
        Intent intent = new Intent(getApplicationContext(), AdviceActivity.class);
        startActivity(intent);
    }
    public void version(View v){
        Intent intent = new Intent(getApplicationContext(), VersionActivity.class);
        startActivity(intent);
    }
}
