package com.example.hangmangame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    char[] alphabet_en = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    char[] alphabet_hu = "aábcdeéfghiíjklmnoóöőpqrstuúüűvwxyz".toCharArray();
    char[] alphabet_ru = {'\u0410', '\u0411', '\u0412', '\u0413', '\u0414', '\u0415', '\u0401', '\u0416', '\u0417', '\u0418', '\u0419', '\u041A', '\u041B', '\u041C', '\u041D', '\u041E', '\u041F', '\u0420', '\u0421', '\u0422', '\u0423', '\u0424', '\u0425', '\u0426', '\u0427', '\u0428', '\u0429', '\u042A', '\u042B', '\u042C', '\u042D', '\u042E', '\u042F'};
    //char[] russianAlphabet = getAlphabet(LocaleLanguage.RUSSIAN);
    public final int MAX_ERRORS = 11; //-- 11 kép van

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemHard:
                Toast.makeText(this, "Nehéz szintet válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemEasy:
                Toast.makeText(this, "Könnyű szintet válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemBiology:
                Toast.makeText(this, "Biolőgia kategóriát választott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemMathematics:
                Toast.makeText(this, "Matematika kategóriát válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemBuildings:
                Toast.makeText(this, "Épületeket válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemIt:
                Toast.makeText(this, "Informatikát válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuLanguageEnglish:
                Toast.makeText(this, "Angol nyelvet válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuLanguageMagyar:
                Toast.makeText(this, "Magyar nyelvet válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuLanguageRusszkij:
                Toast.makeText(this, "Orosz nyelvet válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemSetup:
                Toast.makeText(this, "Profil beállítást válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuExit:
                Toast.makeText(this, "Kilépés a programból", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    char[] charsForScript(Character.UnicodeScript script) {
        StringBuilder sb = new StringBuilder();
        for (int cp = 0; cp < Character.MAX_VALUE; ++cp) {
            if (Character.isValidCodePoint(cp) && script == Character.UnicodeScript.of(cp)) {
                sb.appendCodePoint(cp);
            }
        }
        return sb.toString().toCharArray();
    }
}