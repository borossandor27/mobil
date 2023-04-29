package com.example.megosztas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editTextInput;
    private Button buttonKuldes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        buttonKuldes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adatmentes();

            }
        });

    }

    private void adatmentes() {
        String seged = editTextInput.getText().toString().trim();
        if (seged.equals(null)){
            editTextInput.setError("Nem adott meg adatot!");
        } else {
            //--- Beviteli mező tartalmának fájlba írása -----
            SharedPreferences sharedPreferenses = getSharedPreferences("Data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferenses.edit();
            editor.putString("editTextErteke",seged);
            editor.apply();
            //-- Második aktivitás elindítása ---------------------
            Intent intent = new Intent(MainActivity.this, MasodikActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void init(){
        editTextInput = findViewById(R.id.editTextInput);
        buttonKuldes = findViewById(R.id.buttonKuldes);
    }
}