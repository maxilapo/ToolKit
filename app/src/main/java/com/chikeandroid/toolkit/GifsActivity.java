package com.chikeandroid.toolkit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Maxime
 */

public class GifsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifs);

        ImageView gifImageView = (ImageView) findViewById(R.id.iv_gif);

        Glide.with(this)
                .load("http://i.imgur.com/Vth6CBz.gif")
                .asBitmap()
                .placeholder(R.drawable.productplaceholder)
                .error(R.drawable.productplaceholder)
                .into(gifImageView);
    }

}
