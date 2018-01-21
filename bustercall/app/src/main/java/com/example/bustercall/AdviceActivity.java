package com.example.bustercall;

import android.os.Bundle;
import android.view.View;

/**
 * Created by 박주현 on 2017-06-21.
 */

public class AdviceActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
    }
    public void backbutton2(View v){
        finish();
    }
}
