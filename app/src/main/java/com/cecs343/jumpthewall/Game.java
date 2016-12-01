package com.cecs343.jumpthewall;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Game extends AppCompatActivity {
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
        setContentView(new GamePanel(this));



        mySong1 = MediaPlayer.create(this,R.raw.song1);
        mySong1.start();

    }
}
