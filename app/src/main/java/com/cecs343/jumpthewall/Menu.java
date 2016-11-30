package com.cecs343.jumpthewall;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.view.WindowManager;

/**
 * Created by aaronpham on 11/29/16.
 */

public class Menu extends AppCompatActivity{
    MediaPlayer mySong1;

    @Override
    protected void onPause() {
        super.onPause();
        if (mySong1 != null) {
            mySong1.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
    }

    public void openNewActivity(View view) {
        setContentView(new GamePanel(this));
        mySong1 = MediaPlayer.create(this,R.raw.song1);
        mySong1.start();
    }

    public void closeActivity(View view) {
        finish();
        System.exit(0);
    }

}
