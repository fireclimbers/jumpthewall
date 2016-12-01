package com.cecs343.jumpthewall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.view.WindowManager;

/**
 * Created by aaronpham on 11/29/16.
 */

public class Menu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
    }

    public void openNewActivity(View view) {
        startActivity(new Intent(this,Game.class));
    }

    public void closeActivity(View view) {
        finish();
        System.exit(0);
    }

}
