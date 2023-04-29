package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //-- 1. lépés Deklarálás ------------------
    private Button buttonSubmit;
    private EditText editTextInput;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String szoveg = editTextInput.getText().toString().trim();
                textViewResult.setText(szoveg);
            }
        });
    }

    //-- 2. lépés Inicializálás ---------------
    private void init(){
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextInput = findViewById(R.id.editTextInput);
        textViewResult = findViewById(R.id.textViewResult);
    }
}