package com.example.peoplerestclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, ageInput;
    private Button submitButton;
    private TextView peopleTextview;
    private ListView peopleListView;
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
            switch (requestMethod) {
                case "GET":
                    String content = response.getContent();
                    // -- https://github.com/google/gson --
                    Gson converter = new Gson();
                    List<Person> people = Arrays.asList(converter.fromJson(content, Person[].class));
                    PeopleAdapter adapter = new PeopleAdapter(people);
                    peopleListView.setAdapter(adapter);
                    break;
                case "POST":
                    if (response.getResponseCode() == 201) {
                        RequestTask task = new RequestTask(base_url);
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
            String ageText = ageInput.getText().toString().trim();
            // TODO: validate
            int age = Integer.parseInt(ageText);
            Person person = new Person(0, name, email, age);
            Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = converter.toJson(person);
            RequestTask task = new RequestTask(base_url, "POST", json);
            task.execute();
            nameInput.setText("", TextView.BufferType.EDITABLE);
            emailInput.setText("", TextView.BufferType.EDITABLE);
            ageInput.setText("", TextView.BufferType.EDITABLE);
        });
        RequestTask task = new RequestTask(base_url);
        task.execute();
    }

    private void init() {
        nameInput = findViewById(R.id.namInput);
        emailInput = findViewById(R.id.emailInput);
        ageInput = findViewById(R.id.ageInput);
        submitButton = findViewById(R.id.submitButton);
        peopleListView = findViewById(R.id.peopleListView);
        peopleTextview = findViewById(R.id.textPeople);
        //-- gorgetes figyelese -----------------
        peopleTextview.setMovementMethod(new ScrollingMovementMethod());

    }

    private class PeopleAdapter extends ArrayAdapter<Person> {
        private List<Person> people;

        public PeopleAdapter(List<Person> objects) {
            super(MainActivity.this, R.layout.person_list_item, objects);
            people = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.person_list_item, null);
            Person actualPerson = people.get(position);
            TextView display = view.findViewById(R.id.display);
            TextView update = view.findViewById(R.id.update);
            TextView delete = view.findViewById(R.id.delete);
            display.setText(actualPerson.toString());
            update.setOnClickListener(v -> {
                // TODO: display update form for item
            });
            delete.setOnClickListener(v -> {
                // TODO: delete item using API
            });
            return view;
        }
    }
}