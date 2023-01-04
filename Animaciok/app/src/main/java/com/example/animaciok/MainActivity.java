package com.example.animaciok;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button buttonBounce, buttonFadeIn, buttonFadeOut, buttonRotate,buttonZoomIn, buttonZoomOut, buttonAnim;
    private TextView textViewBounce, textViewFadeIn, textViewFadeOut, textViewRotate, textViewZoomIn, textViewZoomOut;
    private Animation bounce, fadeIn, fadeOut, rotate, zoomIn, zoomOut;
    private ImageView imageViewFrameAnim;
    private boolean running;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        buttonBounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewBounce.startAnimation(bounce);
            }
        });

        buttonFadeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewFadeIn.startAnimation(fadeIn);
            }
        });

        buttonFadeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewFadeOut.startAnimation(fadeOut);
            }
        });

        buttonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewRotate.startAnimation(rotate);
            }
        });

        buttonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewZoomIn.startAnimation(zoomIn);
            }
        });

        buttonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewZoomOut.startAnimation(zoomOut);
            }
        });

        buttonAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running){
                    ((AnimationDrawable) imageViewFrameAnim.getBackground()).start();
                    running = true;
                } else {
                    ((AnimationDrawable) imageViewFrameAnim.getBackground()).stop();
                    running = false;

                }
            }
        });
    }
    private void init(){
        buttonBounce = findViewById(R.id.buttonBounce);
        buttonFadeIn = findViewById(R.id.buttonFaidIn);
        buttonFadeOut = findViewById(R.id.buttonFaidOut);
        buttonRotate = findViewById(R.id.buttonRotate);
        buttonZoomIn = findViewById(R.id.buttonZoomIn);
        buttonZoomOut = findViewById(R.id.buttonZoomOut);
        buttonAnim = findViewById(R.id.buttonFrameAnim);
        //-- TextViews ---------------------------------------
        textViewBounce=findViewById(R.id.textViewBounce);
        textViewFadeIn = findViewById(R.id.textViewFadeIn);
        textViewFadeOut = findViewById(R.id.buttonFaidOut);
        textViewRotate = findViewById(R.id.textViewRotate);
        textViewZoomIn = findViewById(R.id.textViewZoomIn);
        textViewZoomOut = findViewById(R.id.textViewZoomOut);
        //-- Animation ---------------------------------------
        bounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
        fadeIn =AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in );
        fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        rotate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        zoomIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_in);
        zoomOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_out);
        // ----------------------
        imageViewFrameAnim = findViewById(R.id.imageFrameAnim);
        //-- Animation helper -------------------------------------------
        running = false;
    }
}