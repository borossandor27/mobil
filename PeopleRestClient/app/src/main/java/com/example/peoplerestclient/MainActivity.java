package com.example.peoplerestclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, ageInput;
    private Button submitButton;
    private TextView peopleTextview;
    private final String base_url = "https://retoolapi.dev/cRJhEP/people";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        try {
            Response response = RequestHandler.get(base_url);
            String people = response.getContent();
            peopleTextview.setText(people);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        nameInput = findViewById(R.id.namInput);
        emailInput = findViewById(R.id.emailInput);
        ageInput = findViewById(R.id.ageInput);
        submitButton = findViewById(R.id.submitButton);
        peopleTextview = findViewById(R.id.textPeople);
    }
}