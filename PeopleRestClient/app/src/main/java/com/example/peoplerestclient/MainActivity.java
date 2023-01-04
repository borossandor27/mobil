package com.example.peoplerestclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, ageInput;
    private Button submitButton;
    private TextView peopleTextview;
    //-- retool.com/api-generator ---------
    private final String base_url = "https://retoolapi.dev/cRJhEP/people";

    /*
    This class was deprecated in API level 30.
    Use the standard java.util.concurrent
        or
        Kotlin concurrency utilities (https://developer.android.com/topic/libraries/architecture/coroutines) instead.
     */
    private class RequestTask extends AsyncTask<Void, Void, Response> {


        @Override
        protected Response doInBackground(Void... voids) {
            try {
                Response response = RequestHandler.get(base_url);
                String people = response.getContent();
                //-- other thread ---
                runOnUiThread(() -> {
                    peopleTextview.setText(people);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        RequestTask task = new RequestTask();
        task.execute();

    }

    private void init() {
        nameInput = findViewById(R.id.namInput);
        emailInput = findViewById(R.id.emailInput);
        ageInput = findViewById(R.id.ageInput);
        submitButton = findViewById(R.id.submitButton);
        peopleTextview = findViewById(R.id.textPeople);
        //-- gorgetes figyelese -----------------
        peopleTextview.setMovementMethod(new ScrollingMovementMethod());
    }
}