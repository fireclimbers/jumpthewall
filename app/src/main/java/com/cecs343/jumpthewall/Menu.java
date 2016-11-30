package com.cecs343.jumpthewall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

/**
 * Created by aaronpham on 11/29/16.
 */

public class Menu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    public void openNewActivity(View view) {
        setContentView(new GamePanel(this));

    }

    public void closeActivity(View view) {
        finish();
        System.exit(0);
    }

}
