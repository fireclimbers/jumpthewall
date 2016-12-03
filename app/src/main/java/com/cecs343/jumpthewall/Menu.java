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

    MediaPlayer menuSong;

    @Override
    protected void onPause() {
        super.onPause();
        if (menuSong != null)
        {
            menuSong.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        menuSong = MediaPlayer.create(this,R.raw.menusong);
        menuSong.start();
        menuSong.setLooping(true);
    }

    public void openNewActivity(View view) {
        startActivity(new Intent(this,Game.class));
    }
    public void optionMenu(View view) { startActivity(new Intent(this, pop.class));}

    public void closeActivity(View view) {
        finish();
        System.exit(0);
    }

}
