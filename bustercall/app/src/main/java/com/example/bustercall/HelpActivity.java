package com.example.bustercall;

import android.os.Bundle;
import android.view.View;

public class HelpActivity extends MainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

    }
    public void backbutton4(View v){
        finish();
    }
}

