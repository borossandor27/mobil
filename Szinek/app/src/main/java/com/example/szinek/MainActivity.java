package com.example.szinek;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //-- Felhasználható objektumok
    private RelativeLayout layout;
    private TextView hatterSzin;
    private Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int red = rnd.nextInt(256);
                int green = rnd.nextInt(256);
                int blue = rnd.nextInt(256);
                layout.setBackgroundColor(Color.rgb(red,green,blue));
                if ((red + green + blue) > 450){
                    hatterSzin.setTextColor(Color.BLACK);
                } else {
                    hatterSzin.setTextColor(Color.WHITE);
                }
                String szin = String.format("(%d, %d, %d)",red,green,blue);
                hatterSzin.setText(szin);
            }
        });
    }

    private void init(){
        //-- Változók inicializálása
        layout = findViewById(R.id.layout);
        hatterSzin = findViewById(R.id.hatterSzin);
        rnd = new Random();
    }
}