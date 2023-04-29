package com.example.thinkanumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imageHp1, imageHp2, imageHp3, imageHp4, imageHp5;
    private ImageView[] eletek;
    private TextView textTippeltSzam;
    private Button btnNovel, btnCsokkent, btnTippel, btnKonnyu, btnNehez;
    private int gondoltSzam, tippeltSzam, elet;
    private AlertDialog.Builder builderVege, builderNehezseg;
    private Toast customToast;
    private int maxSzam;
    private boolean nehezseg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        ujJatek();

        btnTippel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gondoltSzam < tippeltSzam){
                    Toast.makeText(MainActivity.this,
                            "A gondolt szám kisebb", Toast.LENGTH_SHORT).show();
                    eletLevon();
                } else if (gondoltSzam > tippeltSzam){
                    Toast.makeText(MainActivity.this,
                            "A gondolt szám nagyobb", Toast.LENGTH_SHORT).show();
                    eletLevon();
                } else {
                    builderVege.setTitle("Győzelem");
                    AlertDialog dialog = builderVege.create();
                    dialog.show();
                }
            }
        });
        btnNovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tippeltSzam < maxSzam){
                    tippeltSzam++;
                    textTippeltSzam.setText(""+tippeltSzam);
                }else{
                    Toast.makeText(MainActivity.this,
                            "A szám nem lehet nagyobb mint 10", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCsokkent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tippeltSzam > 1){
                    tippeltSzam--;
                    textTippeltSzam.setText(String.valueOf(tippeltSzam));
                }else{
                    Toast.makeText(MainActivity.this,
                            "A szám nem lehet kisebb mint 1", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnKonnyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nehezseg = false;
                builderNehezseg.create().show();
            }
        });

        btnNehez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nehezseg = true;
                builderNehezseg.create().show();
            }
        });
    }

    private void eletLevon() {
        /*
        switch (elet){
            case 5:
                imageHp5.setImageResource(R.drawable.heart1);
                break;
            case 4:
                imageHp4.setImageResource(R.drawable.heart1);
                break;
            case 3:
                imageHp3.setImageResource(R.drawable.heart1);
                break;
            case 2:
                imageHp2.setImageResource(R.drawable.heart1);
                break;
            case 1:
                imageHp1.setImageResource(R.drawable.heart1);
                break;
            default:
                break;
        }
         */
        elet--;
        eletek[elet].setImageResource(R.drawable.heart1);
        //customToast.show();
        if (elet < 1){
            builderVege.setTitle("Vesztettél").create().show();
        }
    }

    private void init() {
        imageHp1 = findViewById(R.id.image_hp1);
        imageHp2 = findViewById(R.id.image_hp2);
        imageHp3 = findViewById(R.id.image_hp3);
        imageHp4 = findViewById(R.id.image_hp4);
        imageHp5 = findViewById(R.id.image_hp5);
        textTippeltSzam = findViewById(R.id.text_tippelt_szam);
        btnNovel = findViewById(R.id.btn_novel);
        btnCsokkent = findViewById(R.id.btn_csokkent);
        btnTippel = findViewById(R.id.btn_tipp);
        btnKonnyu = findViewById(R.id.btn_konnyu);
        btnNehez = findViewById(R.id.btn_nehez);
        eletek = new ImageView[]{imageHp1,imageHp2,imageHp3,imageHp4,imageHp5};

        builderVege = new AlertDialog.Builder(MainActivity.this);
        builderVege.setCancelable(false).setMessage("Szeretne új játékot játszani?")
            .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ujJatek();
                }
            })
            .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        builderNehezseg = new AlertDialog.Builder(MainActivity.this);
        builderNehezseg.setCancelable(false).setTitle("Nehézség váltása")
                .setMessage("Biztosan új játékot kezd a kiválasztott nehézséggel?")
                .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nehezsegAllit();
                    }
                })
                .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        customToast = Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
        customToast.setGravity(Gravity.CENTER, 0,0);
        customToast.setView(getLayoutInflater().inflate(R.layout.custom_toast,(ViewGroup)findViewById(R.id.custom_toast_layout)));
        maxSzam = 10;
        nehezseg = false;
    }

    private void nehezsegAllit() {
        if (nehezseg){
            maxSzam = 40;
            imageHp4.setVisibility(View.VISIBLE);
            imageHp5.setVisibility(View.VISIBLE);
        }else{
            maxSzam = 10;
            imageHp4.setVisibility(View.GONE);
            imageHp5.setVisibility(View.GONE);
        }
        ujJatek();
    }

    private void ujJatek() {
        tippeltSzam = 1;
        elet = 3;
        if (maxSzam == 40) {
            elet = 5;
        }
        gondoltSzam = (int)(Math.random()*maxSzam)+1;
        textTippeltSzam.setText(String.valueOf(tippeltSzam));
        /*
        imageHp1.setImageResource(R.drawable.heart2);
        imageHp2.setImageResource(R.drawable.heart2);
        imageHp3.setImageResource(R.drawable.heart2);
        imageHp4.setImageResource(R.drawable.heart2);
        imageHp5.setImageResource(R.drawable.heart2);
         */
        for (ImageView elet: eletek) {
            elet.setImageResource(R.drawable.heart2);
        }
    }


}
