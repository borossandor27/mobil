package com.example.akasztofaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.beallitasok_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemSetup:
                // TODO: 2023. 05. 11. A felhasználó szerkesztheti a profilját
                Toast.makeText(this, "Profil szerkesztés", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemEasy:
                Toast.makeText(this, "Könnyű szint", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemHard:
                Toast.makeText(this, "Nehéz szint", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemBiology:
                Toast.makeText(this, "Biológiát választott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemBuildings:
                Toast.makeText(this, "Épületek", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemIt:
                Toast.makeText(this, "IT kategória", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuExit:
                Toast.makeText(this, "Kilépés a programból", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}