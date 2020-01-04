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

    public static List<String> highscoreList;
    public static List<String> savedWords;

    public static String SHARED_PREFS = "sharedPrefs";
    public static String SAVED_HIGHSCORE_LIST = "savedList";
    public static String SAVED_SAVEDWORDS_LIST = "savedList";

    public static SharedPreferences sharedPreferences;

    public static void addSavedWordToList(String newWord) {
        savedWords.add(newWord);
        saveSavedWordsList();
    }

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

        //Shared prefs instantieret
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        loadHighscoreList();
        loadSavedWordsList();


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

    //todo lav om til ikke-static
    public static void saveSavedWordsList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
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


    public static void addCurrentDataToList(String newScore) {
        highscoreList.add(newScore);
        Collections.sort(highscoreList, Collections.reverseOrder());
        saveHighscoreList();
    }

    //todo lav om til ikke-static
    public static void saveHighscoreList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(highscoreList);
        editor.putString(SAVED_HIGHSCORE_LIST, json);
        editor.apply();
        Toast.makeText(getActivity(), "Highscore slettet!", Toast.LENGTH_LONG).show();
    }

    public void deleteSavedWords() {
        savedWords = new ArrayList<>();
        SharedPreferences.Editor editor = sharedPreferences.edit();
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
            Fragment fragment = new HelpFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.item_delete_highscore) {
            DialogFragment dialogFragment = new DialogFragment();
            dialogFragment.show(getSupportFragmentManager(), " snap");
            return true;
        }
        if (item.getItemId() == R.id.item_view_highscore) {
            Fragment fragment = new HighscoreFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.item_delete_saved_words) {
            deleteSavedWords();

            return true;
        }else {
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

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
