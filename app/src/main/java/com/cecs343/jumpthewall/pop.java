package com.cecs343.jumpthewall;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
/**
 * Created by aaronpham on 12/2/16.
 */

public class pop extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.optionpopup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *.4), (int)(height * .3));

    }

    public void openHelp(View view) { startActivity(new Intent(this, helpPopUp.class)); }

    public void mute(View view) {

    }


}
