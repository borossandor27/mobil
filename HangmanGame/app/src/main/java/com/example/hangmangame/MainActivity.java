package com.example.hangmangame;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    char[] alphabet_en, alphabet_hu;
    char[] alphabet_ru = {'\u0410', '\u0411', '\u0412', '\u0413', '\u0414', '\u0415', '\u0401', '\u0416', '\u0417', '\u0418', '\u0419', '\u041A', '\u041B', '\u041C', '\u041D', '\u041E', '\u041F', '\u0420', '\u0421', '\u0422', '\u0423', '\u0424', '\u0425', '\u0426', '\u0427', '\u0428', '\u0429', '\u042A', '\u042B', '\u042C', '\u042D', '\u042E', '\u042F'};

    //-- Ez miért nem??? char[] russianAlphabet = getAlphabet(LocaleLanguage.RUSSIAN);
    public final int MAX_ERRORS = 11; //-- 11 kép van
    public String gameLanguage;
    ConstraintLayout layoutLetters;
    Flow lettersFlow;
    List<Button> letterbtns;
    String thoughtWord, subjectArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        switch (gameLanguage) {
            case "en":
                addLetterButtons(alphabet_en);
                break;
            case "ru":
                addLetterButtons(alphabet_ru);
                break;
            default:
                addLetterButtons((alphabet_hu));
        }
        return true;
    }

    private void addLetterButtons(char[] letters) {
        //layoutLetters.removeAllViews();
        int[] referenseIds = new int[letters.length];
        for (int i = 0; i < letters.length; i++) {
            MaterialButton myButton = new MaterialButton(this);
            myButton.setText(String.valueOf(letters[i]).toUpperCase());
            myButton.setTextSize(10);
            myButton.setLayoutParams(new ConstraintLayout.LayoutParams(50, WRAP_CONTENT));
            myButton.setPadding(0, 0, 0, 0);
            myButton.setId(View.generateViewId());
            final int id_ = myButton.getId();
            referenseIds[i] = id_;
            myButton.setTag(String.valueOf(letters[i]).toUpperCase());
            //String ref = Arrays.stream(referenseIds).mapToObj(String::valueOf).collect(Collectors.joining(","));

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2023. 04. 30. Tippelte ezt a karaktert -> style="@style/Widget.MaterialComponents.Button.TextButton"
                }
            });
            layoutLetters.addView(myButton);
        }
        lettersFlow.setReferencedIds(referenseIds);
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

    private void init() {
        alphabet_en = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        alphabet_hu = "aábcdeéfghiíjklmnoóöőpqrstuúüűvwxyz".toCharArray();
        //-- Ez miért nem??? char[] russianAlphabet = getAlphabet(LocaleLanguage.RUSSIAN);

        layoutLetters = (ConstraintLayout) findViewById(R.id.layoutLetters);
        lettersFlow = new Flow(this);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        params.orientation = params.horizontalChainStyle;

        lettersFlow.setLayoutParams(params);
        gameLanguage = "hu";
        subjectArea="It";
    }

    public void getAllButtons(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof Button) {
                letterbtns.add((Button) v);
            }
        }
    }
    private void emptyPlaceOfExecution(){
        // TODO: 2023. 05. 01. Kivégzőhely előkészítése új játék indításakor
        thoughtWord =makeUpWord();
    }
    private String makeUpWord(){
        // TODO: 2023. 05. 01. Kitatál egy szót az adott témakörben
        String word="mikroprocesszor";
        return word;
    }
    private void guessing(String tipChar){
        // TODO: 2023. 05. 01. A gondolt szó tartalmazza az adott karaktert?
        // TODO: 2023. 05. 01. A megfejtés teljes?

    }
}