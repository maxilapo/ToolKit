package com.maxime.toolkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.maxime.toolkit.objects.User;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.maxime.toolkit.R.layout.activity_main);

        User.getInstance();

        Button showGalleryBtn = (Button) findViewById(R.id.btn_show_gallery);

        showGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(MainActivity.this, ProductGalleryActivity.class);
                startActivity(galleryIntent);
            }
        });

        Button showGif = (Button) findViewById(R.id.btn_show_gif);
        showGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gifIntent = new Intent(MainActivity.this, GifsActivity.class);
                startActivity(gifIntent);
            }
        });

    }
}
