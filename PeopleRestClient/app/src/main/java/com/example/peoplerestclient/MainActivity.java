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
        private String requestUrl, requestMethod, requestBody;

        public RequestTask(String requestUrl) {
            this.requestUrl = requestUrl;
            requestMethod = "GET";
        }

        public RequestTask(String requestUrl, String requestMethod) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
        }

        public RequestTask(String requestUrl, String requestMethod, String requestBody) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
            this.requestBody = requestBody;
        }


        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                switch (requestMethod) {
                    case "GET":
                        response = RequestHandler.get(base_url);
                        String people = response.getContent();
                        //-- write other thread ---
                        runOnUiThread(() -> {
                            peopleTextview.setText(people);
                        });
                        break;
                    case "POST":
                        response = RequestHandler.post(requestUrl, requestBody);
                        break;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            switch (requestMethod){
                case "GET":
                    String people = response.getContent();
                    peopleTextview.setText(people);
                    break;
                case "POST":
                    if (response.getResponseCode() == 201){
                        RequestTask task=new RequestTask(base_url);
                        task.execute();
                    }
                    break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        submitButton.setOnClickListener(view -> {
            //-- add new person
            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String age = ageInput.getText().toString().trim();
            // TODO: validate
            String json = String.format("{\"name\": \"%s\", \"email\": \"%5\", \"age\": \"%s\"}", name, email, age);
            RequestTask task = new RequestTask(base_url);
            task.execute();
        });
        RequestTask task = new RequestTask(base_url);
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