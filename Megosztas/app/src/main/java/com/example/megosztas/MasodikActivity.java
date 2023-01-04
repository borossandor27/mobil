package com.example.megosztas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MasodikActivity extends AppCompatActivity {

private TextView textViewResult;
private Button buttonVissza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masodik);
        init();
        String seged = getSharedPreferencesData();
        textViewResult.setText(seged);
        buttonVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MasodikActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getSharedPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String result = sharedPreferences.getString("editTextErteke", "Nincs adat");
        return result;
    }

    private  void init(){
        textViewResult = findViewById(R.id.textViewResult);
        buttonVissza = findViewById(R.id.buttonVissza);
    }
}