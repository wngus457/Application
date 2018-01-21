package com.example.bustercall;

import android.os.Bundle;
import android.view.View;

/**
 * Created by 박주현 on 2017-06-21.
 */

public class VersionActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_version);
    }
    public void backbutton3(View v){
        finish();
    }
}
