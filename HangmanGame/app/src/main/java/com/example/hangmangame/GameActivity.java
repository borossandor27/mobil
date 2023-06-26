package com.example.hangmangame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    char[] alphabet_en, alphabet_hu, alphabet_ru;

    final int MAX_ERRORS = 13; //-- 13 kép van
    int badGuess = 0;
    StringBuilder gameLanguage;
    ConstraintLayout layoutLetters;
    Flow lettersFlow;
    StringBuilder thoughtWord, subjectArea, resultGuesswork;
    ImageView imageGallows;
    TextView textViewTip;

    /**
     * Játék aktivitás létrehozása
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
        loadExpressions();
        startGame();
    }

    private void endGame() {
    }

    private void loadExpressions() {
    }

    /**
     * A menu hozzáadása a felülethez
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        switch (gameLanguage.toString()) {
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

    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    /**
     * A választott nyelv ABC betűinek megfelelő parancsgombok elhelyezése
     * a játéktéren tippeléshez
     *
     * @param letters char[] - az ABC betűi
     */
    private void addLetterButtons(char[] letters) {
        removeAllLetterButtons(layoutLetters);
        int[] referenseIds = new int[letters.length];
        for (int i = 0; i < letters.length; i++) {
            MaterialButton myButton = new MaterialButton(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(convertToPx(45),
                    convertToPx(45)
            );
            myButton.setLayoutParams(params);
            myButton.setText(String.valueOf(letters[i]).toUpperCase());
            myButton.setTag(String.valueOf(letters[i]).toUpperCase());

            myButton.setTextSize(15);
            //myButton.setWidth();
            //int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, <HEIGHT>, getResources().getDisplayMetrics());
            myButton.setId(View.generateViewId());
            final int id_ = myButton.getId();
            referenseIds[i] = id_;

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2023. 04. 30. Tippelte ezt a karaktert -> style="@style/Widget.MaterialComponents.Button.TextButton"
                    myButton.setEnabled(false);
                    //                myButton.setTextAppearance(com.google.android.material.R.style.Widget_MaterialComponents_Button_TextButton);
                    // TODO: 2023. 05. 15. Találat ellenőrzése
                    guessingProcessing((String) myButton.getText());
                    // TODO: 2023. 05. 15. Kijelző frissítése
                    placeOfExecution();
                    //Toast.makeText(GameActivity.this,"Clicked = " + myButton.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            layoutLetters.addView(myButton);
        }
        lettersFlow.setReferencedIds(referenseIds);
    }

    /**
     * Menüválasztások feldolgozása
     *
     * @param item
     * @return
     */
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
                addLetterButtons(alphabet_en);
                Toast.makeText(this, "Angol nyelvet válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuLanguageMagyar:
                addLetterButtons(alphabet_hu);
                Toast.makeText(this, "Magyar nyelvet válsztott", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuLanguageRusszkij:
                addLetterButtons(alphabet_ru);
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
        char[] ruchars = {'\u0410', '\u0411', '\u0412', '\u0413', '\u0414', '\u0415', '\u0401', '\u0416', '\u0417', '\u0418', '\u0419', '\u041A', '\u041B', '\u041C', '\u041D', '\u041E', '\u041F', '\u0420', '\u0421', '\u0422', '\u0423', '\u0424', '\u0425', '\u0426', '\u0427', '\u0428', '\u0429', '\u042A', '\u042B', '\u042C', '\u042D', '\u042E', '\u042F'};
        alphabet_ru = (new String(ruchars)).toCharArray();
        //-- Ez miért nem??? char[] russianAlphabet = getAlphabet(LocaleLanguage.RUSSIAN);
        alphabet_en = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        alphabet_hu = "aábcdeéfghiíjklmnoóöőpqrstuúüűvwxyz".toCharArray();
        //-- Ez miért nem??? char[] russianAlphabet = getAlphabet(LocaleLanguage.RUSSIAN);

        layoutLetters = (ConstraintLayout) findViewById(R.id.layoutLetters);
        lettersFlow = (Flow) findViewById(R.id.lettersFlow);
        imageGallows = (ImageView) findViewById(R.id.imageGallows);
        textViewTip = (TextView) findViewById(R.id.textViewTip);
        gameLanguage = new StringBuilder("hu");
        subjectArea = new StringBuilder("It");
        badGuess = 0;
    }

    /**
     * Lehetséges karaktereket (a cél nyelv ABC-je) szimbolizáló parancsgombok elhelyezése
     * - játék indításakor
     * - cél nyelv megváltoztatásakor
     *
     * @param layoutLetters
     */
    public void removeAllLetterButtons(ConstraintLayout layoutLetters) {
        for (int i = 0; i < this.layoutLetters.getChildCount(); i++) {
            View v = this.layoutLetters.getChildAt(i);
            if (v instanceof MaterialButton) {
                this.layoutLetters.removeView((MaterialButton) v);
            }
        }
    }

    /**
     * Kivégzőhely megjelenítése
     * - új játék indításakor
     * - tippelés után
     */
    private void placeOfExecution() {
        //-- találati szintnek megfelelő kép megjelenítése
        switch (badGuess) {
            case 0:
                imageGallows.setBackgroundResource(R.drawable.akasztofa00);
                break;
            case 1:
                imageGallows.setBackgroundResource(R.drawable.akasztofa01);
                break;
            case 2:
                imageGallows.setBackgroundResource(R.drawable.akasztofa02);
                break;
            case 3:
                imageGallows.setBackgroundResource(R.drawable.akasztofa03);
                break;
            case 4:
                imageGallows.setBackgroundResource(R.drawable.akasztofa04);
                break;
            case 5:
                imageGallows.setBackgroundResource(R.drawable.akasztofa05);
                break;
            case 6:
                imageGallows.setBackgroundResource(R.drawable.akasztofa06);
                break;
            case 7:
                imageGallows.setBackgroundResource(R.drawable.akasztofa07);
                break;
            case 8:
                imageGallows.setBackgroundResource(R.drawable.akasztofa08);
                break;
            case 9:
                imageGallows.setBackgroundResource(R.drawable.akasztofa09);
                break;
            case 10:
                imageGallows.setBackgroundResource(R.drawable.akasztofa10);
                break;
            case 11:
                imageGallows.setBackgroundResource(R.drawable.akasztofa11);
                break;
            case 12:
                imageGallows.setBackgroundResource(R.drawable.akasztofa12);
                break;
            case 13:
                imageGallows.setBackgroundResource(R.drawable.akasztofa13);
                break;
            default:
                imageGallows.setBackgroundResource(R.drawable.akasztofa00);
                break;
        }

        //-- találati szintnek megfelelő szöveg megjelenítése
        textViewTip.setText(resultGuesswork.toString());
    }

    /**
     * Kitatál egy szót az adott témakörben
     *
     * @return kitalált szó
     */
    private void makeUpWord() {
        // TODO: 2023. 05. 01.
        thoughtWord = new StringBuilder("mikroprocesszor");
        resultGuesswork = new StringBuilder();
        for (int i = 0; i < thoughtWord.length(); i++) {
            if(thoughtWord.charAt(i)==' '){
                resultGuesswork.append(" ");
            } else {
                resultGuesswork.append("_");
            }
        }
        textViewTip.setText(resultGuesswork);
    }

    private void guessingProcessing(String tipChar) {
        // TODO: 2023. 05. 01. A gondolt szó tartalmazza az adott karaktert?

        if (thoughtWord.indexOf(tipChar.toLowerCase()) < 0) {
            //-- Nem található benne - rossz tipp
            if (badGuess < MAX_ERRORS) {
                badGuess++;
            } else {
                badGuess = MAX_ERRORS;
                endGame();
            }
        } else {
            //-- Tartalmazza - jó tipp
            for (int i = 0; i < thoughtWord.length(); i++) {
                if(String.valueOf(thoughtWord.charAt(i))==tipChar){
                    resultGuesswork.replace(i,i+1,tipChar);
                }
            }

            // TODO: 2023. 05. 01. A megfejtés teljes?
        }


    }

    private void startGame() {
        makeUpWord();
        placeOfExecution();
    }

    private void loadJson() {
        try {
            //-- fájl beolvasása szövegként ------
            InputStream inputStream = getAssets().open("expressions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            //-- JSON elemek átmeneti tárolásához a változók -----------
            String language, category, level, expressionInHungarian, expression, descriptionInHungarian, descriptionInTargetLanguage, picture;
            //--  fetch json file ----------
            String json;
            int count;
            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            count = jsonArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                language = jsonObject.getString("language");
                category = jsonObject.getString("category");
                level = jsonObject.getString("level");
                expressionInHungarian = jsonObject.getString("expressionInHungarian");
                expression = jsonObject.getString("expression");
                descriptionInHungarian = jsonObject.getString("descriptionInHungarian");
                descriptionInTargetLanguage = jsonObject.getString("descriptionInTargetLanguage");
                picture = jsonObject.getString("picture");

            }

        } catch (Exception e) {
            Log.e("TAG", "loadJson: error " + e);
        }
    }
}