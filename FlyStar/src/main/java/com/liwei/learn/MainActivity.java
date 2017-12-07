package com.liwei.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class MainActivity extends AppCompatActivity  {

    private FlutteringLayout heart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heart = (FlutteringLayout) findViewById(R.id.flutteringLayout);
    }
    public void OnClick(View v){
        heart.addHeart();

    }
    public void doClick(View v) {
        Intent in = new Intent(this,Main2Activity.class);
        startActivity(in);
    }
}
