package com.s185020.Galgeleg_3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.s185020.Galgeleg_3.fragments.DialogFragment;
import com.s185020.Galgeleg_3.fragments.HelpFragment;
import com.s185020.Galgeleg_3.fragments.HighscoreFragment;
import com.s185020.Galgeleg_3.fragments.WelcomeFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogFragment.DialogFragmentListener {

    public static MainActivity instance;

    public static List<String> highscoreList;
    public static List<String> savedWords;

    public static String SHARED_PREFS = "sharedPrefs";
    public static String SAVED_HIGHSCORE_LIST = "savedHighscoreList";

    //public static String SHARED_PREFS_SAVEDWORDS = "sharedPrefs";
    public static String SAVED_SAVEDWORDS_LIST = "savedSavedWordsList";

    public static SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentLayout, fragment)
                    .commit();
        }

        //todo følg op på det her
        setTitle("Velkommen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        instance = this;

        //Shared prefs instantieret
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadHighscoreList();
        loadSavedWordsList();
        instantiateSavedWordsList();
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void addSavedWordToList(String newWord) {
        savedWords.add(newWord);
        saveSavedWordsList();
    }

    private void loadSavedWordsList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SAVED_SAVEDWORDS_LIST, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        savedWords = gson.fromJson(json, type);
        if (savedWords == null) {
            savedWords = new ArrayList<>();
        }
    }

    public void saveSavedWordsList() {
        Gson gson = new Gson();
        String json = gson.toJson(getSavedWordsList());
        editor.putString(SAVED_SAVEDWORDS_LIST, json);
        editor.apply();
    }

    public static List getHighscoreList() {
        return highscoreList;
    }

    public static List getSavedWordsList() {
        return savedWords;
    }

    public void addNewHighscoreToList(String newScore) {
        highscoreList.add(newScore);
        Collections.sort(highscoreList, Collections.reverseOrder());
        saveHighscoreList();
    }

    public void saveHighscoreList() {
        Gson gson = new Gson();
        String json = gson.toJson(highscoreList);
        editor.putString(SAVED_HIGHSCORE_LIST, json);
        editor.apply();
    }

    private void loadHighscoreList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SAVED_HIGHSCORE_LIST, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        highscoreList = gson.fromJson(json, type);

        if (highscoreList == null) {
            highscoreList = new ArrayList<>();
        }
    }

    public void deleteSavedHighscore() {
        highscoreList = new ArrayList<>();
        Gson gson = new Gson();
        String json = gson.toJson(highscoreList);
        editor.putString(SAVED_HIGHSCORE_LIST, json);
        editor.apply();
        Toast.makeText(getActivity(), "Highscore slettet!", Toast.LENGTH_LONG).show();
    }

    public void deleteSavedWords() {
        savedWords = new ArrayList<>();
        Gson gson = new Gson();
        String json = gson.toJson(savedWords);
        editor.putString(SAVED_SAVEDWORDS_LIST, json);
        editor.apply();
        Toast.makeText(getActivity(), "Gemte ord slettet!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.item_help) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, new HelpFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.item_delete_highscore) {
            new DialogFragment("Ønsker du at slette den gemte highscoreliste?", true).show(getSupportFragmentManager(), "");
            return true;
        }
        if (item.getItemId() == R.id.item_view_highscore) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, new HighscoreFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.item_delete_saved_words) {
            deleteSavedWords();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void onYesClicked() {
        //Highscore slettes
        deleteSavedHighscore();
    }

    private void instantiateSavedWordsList() {
        if (savedWords.isEmpty()) {
            savedWords.add("hovedbanegården");
            savedWords.add("sankthansaften");
            savedWords.add("julemærkehjem");
            savedWords.add("speciallægepraksis");
            savedWords.add("gedebukkeben");
        }
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
