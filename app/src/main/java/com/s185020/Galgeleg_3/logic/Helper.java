package com.s185020.Galgeleg_3.logic;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.s185020.Galgeleg_3.MainActivity;
import com.s185020.Galgeleg_3.R;
import com.s185020.Galgeleg_3.fragments.WelcomeFragment;
import com.s185020.Galgeleg_3.fragments.WordListFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helper extends Application {
    private static final String TAG = "AD HELPER";
    public static Helper instance = null;


    public static List<String> highscoreList;
    public static List<String> savedWords;

    public static String SHARED_PREFS = "sharedPrefs";
    public static String SAVED_HIGHSCORE_LIST = "savedHighscoreList";

    //public static String SHARED_PREFS_SAVEDWORDS = "sharedPrefs";
    public static String SAVED_SAVEDWORDS_LIST = "savedSavedWordsList";

    public static SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.i(TAG, "onCreate");

        //Shared prefs instantieret
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadHighscoreList();
        loadSavedWordsList();
        instantiateSavedWordsList();
    }

    public static Helper getInstance() {
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
        Toast.makeText(getApplicationContext(), "Highscore slettet!", Toast.LENGTH_LONG).show();
    }

    public void deleteSavedWords() {
        savedWords = new ArrayList<>();
        Gson gson = new Gson();
        String json = gson.toJson(savedWords);
        editor.putString(SAVED_SAVEDWORDS_LIST, json);
        editor.apply();
        Toast.makeText(getApplicationContext(), "Gemte ord slettet!", Toast.LENGTH_LONG).show();

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
}