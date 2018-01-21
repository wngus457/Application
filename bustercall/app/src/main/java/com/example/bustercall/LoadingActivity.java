package com.example.bustercall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadingActivity extends Activity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new splashhandler(), 1000);} //1초후 로딩화면 종료

         class splashhandler implements Runnable{
            public void run() {
                startActivity(new Intent(getApplication(), MainActivity.class));//로딩화면이 끝난후 보여지는 클래스
                LoadingActivity.this.finish(); //로딩화면 종료
            }
        }



}