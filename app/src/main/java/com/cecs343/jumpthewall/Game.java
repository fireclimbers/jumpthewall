package com.cecs343.jumpthewall;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class Game extends AppCompatActivity {

    MediaPlayer mySong1, mySong2, mySong3;

    @Override
    protected void onPause() {
        super.onPause();
        if (mySong1 != null)
        {
            mySong1.release();
        }
        if (mySong2 != null)
        {
            mySong2.release();
        }
        if (mySong3 != null)
        {
            mySong3.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GamePanel(this));

        mySong1 = MediaPlayer.create(this,R.raw.song1);
        mySong2 = MediaPlayer.create(this,R.raw.song2);
        mySong3 = MediaPlayer.create(this,R.raw.song3);

        mySong2.start();
        mySong2.setLooping(true);
    }

    public void closeActivity() {
        finish();
        //System.exit(0);
    }


}
