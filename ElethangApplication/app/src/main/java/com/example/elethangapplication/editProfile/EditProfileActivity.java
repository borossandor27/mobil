package com.example.elethangapplication.editProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elethangapplication.MainActivity;
import com.example.elethangapplication.R;
import com.example.elethangapplication.RequestHandler;
import com.example.elethangapplication.Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {
    private TextView textName,textUsername,textEmail;
    private EditText editTextName, editTextUsername,editTextEmail;
    private Button btnEdit, btnCancel;
    private SharedPreferences sharedPreferences;

    private boolean edit = false;
    private User user;
    private String name, username, email, password;

    //emulator
    //private String url = "http://10.0.2.2:8000/api/";
    private String url = "http://192.168.0.48:8000/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        init();

        sharedPreferences = EditProfileActivity.this.getSharedPreferences("token", Context.MODE_PRIVATE);

        RequestTaskUSer taskUSer = new RequestTaskUSer();
        taskUSer.execute();

        noEditText();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit){
                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    edit = false;
                    noEditText();
                    textName.setVisibility(View.VISIBLE);
                    textUsername.setVisibility(View.VISIBLE);
                    textEmail.setVisibility(View.VISIBLE);
                    btnCancel.setText("Vissza");
                    btnEdit.setText("Módosítás");
                }
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit){
                    edit = true;
                    editTextVisible();
                    textName.setVisibility(View.GONE);
                    textUsername.setVisibility(View.GONE);
                    textEmail.setVisibility(View.GONE);
                    btnCancel.setText("Vissza");
                    btnEdit.setText("Mentés");
                }else {
                    name = editTextName.getText().toString().trim();
                    username = editTextUsername.getText().toString().trim();
                    email = editTextEmail.getText().toString().trim();

                    new RequestTaskEdit().execute();
                }
            }
        });

    }
    private void  noEditText(){
        editTextName.setVisibility(View.GONE);
        editTextUsername.setVisibility(View.GONE);
        editTextEmail.setVisibility(View.GONE);
    }
    private void editTextVisible(){
        editTextName.setVisibility(View.VISIBLE);
        editTextUsername.setVisibility(View.VISIBLE);
        editTextEmail.setVisibility(View.VISIBLE);
    }
    private class RequestTaskUSer extends AsyncTask<Void, Void, Response>{

        @Override
        protected Response doInBackground(Void... voids) {
            Response response =null;
            try {
                response  = RequestHandler.getAuth(url +"user",sharedPreferences.getString("token", ""));
            }catch (IOException e ){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Gson gson = new Gson();
            if (response == null){
                Toast.makeText(EditProfileActivity.this, "Hiba történt a betöltés során", Toast.LENGTH_SHORT).show();
            }else if (response.getResponseCode() >400){
                Toast.makeText(EditProfileActivity.this, response.getContent(), Toast.LENGTH_SHORT).show();

            }else
                user = gson.fromJson(response.getContent(), User.class);

                textName.setText(user.getName());
                textUsername.setText(user.getUsername());
                textEmail.setText(user.getEmail());
                editTextName.setText(user.getName());
                editTextUsername.setText(user.getUsername());
                editTextEmail.setText(user.getEmail());
        }
    }
    private class RequestTaskEdit extends AsyncTask<Void, Void, Response>{

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                String loginString = "";

                    loginString = new JSONObject()
                            .put("name", name)
                            .put("username", username)
                            .put("email", email)
                            .toString();

                //String data = String.format("{\"name\":\"%s\",\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}", name, username, email, password);
                response = RequestHandler.put(url + "updateSelf/", loginString,sharedPreferences.getString("token", "") );
            }catch (IOException | JSONException e ){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Gson gson = new Gson();
            if (response == null){
                Toast.makeText(EditProfileActivity.this, "Hiba történt a betöltés során", Toast.LENGTH_SHORT).show();
            }else if (response.getResponseCode() >= 400){
                Toast.makeText(EditProfileActivity.this, response.getContent(), Toast.LENGTH_SHORT).show();
                Log.d("izé", response.getContent());
            }else {
                Toast.makeText(EditProfileActivity.this, "Sikeres Módosítás", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
    private void init(){
        textName = findViewById(R.id.textName);
        textUsername = findViewById(R.id.textUsername);
        textEmail = findViewById(R.id.textEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);

    }
}