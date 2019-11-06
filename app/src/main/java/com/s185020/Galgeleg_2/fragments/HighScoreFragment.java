package com.s185020.Galgeleg_2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.s185020.Galgeleg_2.MainActivity;
import com.s185020.Galgeleg_2.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HighScoreFragment extends Fragment implements View.OnClickListener {

    private Button button_play, button_settings, button_save, button_load;
    private TextView textView;
    private TextView textView_highScore;
    private ListView listView;
    private String word;
    private int choice;
    private int score;
    private int wrongGuessCount;
    private boolean won;
    private static final String FILE_NAME = "example.txt";
    private View highScore_view;
    private static HighScoreFragment instance = null;
    private List<String> highscoreList;
    private String SHARED_PREFS = "sharedPrefs";

    private String SAVED_LIST = "savedList";

    private String currentScoreString;

    SharedPreferences sharedPreferences;

    private HighScoreFragment() {
    }

    public static HighScoreFragment getInstance() {
        if (instance == null) {
            instance = new HighScoreFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        highScore_view = inflater.inflate(R.layout.fragment_highscore, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("HIGHSCORE");

        if (getArguments() != null) {
            choice = getArguments().getInt("choice");
            wrongGuessCount = getArguments().getInt("wrong_guess_count");
            won = getArguments().getBoolean("won");
            word = getArguments().getString("word");
        }
        textView = highScore_view.findViewById(R.id.textView_highscore);
        textView_highScore = highScore_view.findViewById(R.id.textView_highscore);
        button_play = highScore_view.findViewById(R.id.button_play_from_main);
        button_play.setText("Play");
        button_settings = highScore_view.findViewById(R.id.button_settings);
        button_settings.setText("Settings");
        button_save = highScore_view.findViewById(R.id.button_save);
        button_load = highScore_view.findViewById(R.id.button_load);
        listView = highScore_view.findViewById(R.id.list_view);

//        listView.setOnItemClickListener((AdapterView.OnItemClickListener) settings_view);
        button_play.setOnClickListener(this);
        button_settings.setOnClickListener(this);
        button_save.setOnClickListener(this);
        button_load.setOnClickListener(this);

        // listView.setAdapter(new ArrayAdapter(this, a));

        if (won) {
            //todo afspil cheers
            Toast.makeText(getActivity(), "Flot, du vandt!", Toast.LENGTH_LONG).show();
        } else {
            //todo afspil boo
            Toast.makeText(getActivity(), "Trist, du tabte!", Toast.LENGTH_LONG).show();
        }

        //todo færdiggør
        score = (1000 / 7) * (7 - wrongGuessCount);
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        currentScoreString = ("" + (10000 + score)).substring(1) + " points - " + formatter.format(date);

        textView.setText(getResources().getString(R.string.word_to_guess) + word +
                "\nAntal forkerte gæt: " + wrongGuessCount + "\n\nPoint: " + score);
        button_play.setText("Prøv igen?");

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        loadData();
        addCurrentDataToList();
        saveData();

        snap();

        return highScore_view;
    }

    private void addCurrentDataToList() {
        highscoreList.add(currentScoreString);

        //List<String> list = Arrays.asList("def123", "abc999", "zzz000", "abc123", "zzz111");
        //List<String> list = highscoreList.subList(0,highscoreList.size());

        Comparator<String> cmp = new Comparator<String>() {
            public int compare(String o1, String o2) {
                int diff = (o1.substring(0, 4)).compareTo(o2.substring(0, 4));
                //return (diff == 0) ? (Integer.valueOf(o2.substring(4)).compareTo(Integer.valueOf(o1.substring(4)))): diff;
                return diff;
            }
        };
        Collections.sort(highscoreList, cmp);

  /*      HashMap<String, String> stringMap = new HashMap<>();
        List <String> highscoreList2 = new ArrayList<>();

        for (String string : highscoreList) {
            stringMap.put(string.substring(0, 5), string.substring(5));
            highscoreList2.add(string.substring(0, 5));
        }
        Collections.sort(highscoreList2);

        highscoreList.clear();
        for (String string : highscoreList2) {
            highscoreList.add(string + stringMap.get(string));
        }


        try {
            highscoreList = highscoreList.subList(0, 11);
        } catch (Exception e) {

        }
        */

    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(highscoreList);
        editor.putString(SAVED_LIST, json);
        editor.apply();
    }

    private void loadData() {
        //       sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SAVED_LIST, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        highscoreList = gson.fromJson(json, type);

        if (highscoreList == null) {
            highscoreList = new ArrayList<>();
        }
    }

    void snap() {
if (highscoreList != null)
        for (String string : highscoreList) {
            System.out.println(string);
        }
    }

    public void onClick(View v) {
        if (v == button_play) {
            playAgain();
        } else if (v == button_settings) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, SettingsFragment.getInstance())
                    .addToBackStack(null)
                    .commit();
        } else if (v == button_load) {


        }
    }

    /*public void printToTextview() {

        textView_highScore.setText(load());

    }*/

  /*

    private void loadData() {
        SharedPreferences sharedPreferences = MainUI.getContextOfApp().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currentRadioChannel = sharedPreferences.getFloat(SAVED_LIST, 0.0f);
        currentRadioFrequency = sharedPreferences.getString(CURRENT_RADIOFREQUENCY, "FM");
    }

*/

    //todo kan det gøres smartere?
    //ny knap -> finis går tilbage til forrige aktivitet
    private void playAgain() {
        Bundle bundle = new Bundle();
        bundle.putInt("choice", choice);
        PlayFragment playFragment = PlayFragment.getInstance();

        if (choice == 2) {
            //todo implement
            bundle.putString("choice", getArguments().getString("diflevel"));
        }

        playFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, playFragment)
                .addToBackStack(null)
                .commit();
    }

    public void deleteSaved() {
        highscoreList = null;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(highscoreList);
        editor.putString(SAVED_LIST, json);
        editor.apply();
    }
}
