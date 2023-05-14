package com.example.elethangapplication.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.elethangapplication.Token;
import com.example.elethangapplication.register.RegisterActivity;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private EditText etUsername;
    private EditText etPassword;
    private SharedPreferences sharedPreferences;
    private TextView registerLink;

    //otthon gép
    private String url = "http://192.168.0.48:8000/api/login";
    //private String url = "http://10.0.2.2:8000/login";
    //private String url = "http://10.148.149.190:8000/api/login";
    //private String url = "http://10.0.2.2:8000/api/dogAdoption";
    //emulator
    //private String url = "http://10.0.2.2:8000/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        sharedPreferences = LoginActivity.this.getSharedPreferences("token", Context.MODE_PRIVATE);
        init();
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestTask task = new RequestTask();
                task.execute();
            }
        });

    }
    public void init(){
        buttonLogin = findViewById(R.id.buttonLogin);
        etUsername = findViewById(R.id.editUsername);
        etPassword = findViewById(R.id.editpassword);
        registerLink = findViewById(R.id.registerLink);
    }


    private class RequestTask extends AsyncTask<Void, Void, Response> {
        private String body;

        @Override
        protected void onPreExecute() {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            // TODO: validáció
            if (username.isEmpty()){
                Toast.makeText(LoginActivity.this, "Felhasználó kötelező", Toast.LENGTH_SHORT).show();
            }
            else if (password.isEmpty()){
                Toast.makeText(LoginActivity.this, "Jelszó kötelező", Toast.LENGTH_SHORT).show();
            }
            LoginUser user = new LoginUser(username, password);
            Gson jsonconv = new Gson();
            body = jsonconv.toJson(user);
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                response = RequestHandler.post(url, body);
                Response finalResponse = response;
                runOnUiThread(()->{
                    Toast.makeText(LoginActivity.this, finalResponse.getContent(), Toast.LENGTH_SHORT).show();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (response == null) {
                //TODO megfelelő hibaüzenet
                Toast.makeText(LoginActivity.this, "Hiba történt a bejelentkezés során", Toast.LENGTH_SHORT).show();

            }else if (response.getResponseCode() >= 400){
                Toast.makeText(LoginActivity.this, response.getContent(), Toast.LENGTH_SHORT).show();
            }
            //TODO: válasz vizsgálata és másik activity indítása
            else {
                Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                Token token = gson.fromJson(response.getContent(), Token.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", token.getToken());
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                
            }
        }
    }
}