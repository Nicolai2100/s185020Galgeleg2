package com.s185020.Galgeleg_3.fragments;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;
import com.s185020.Galgeleg_3.MainActivity;
import com.s185020.Galgeleg_3.R;
import com.s185020.Galgeleg_3.logic.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameOverFragment extends Fragment implements View.OnClickListener {

    private static GameOverFragment instance = null;
    private Button button_play, button_settings;
    private TextView textView;
    private ListView listView;
    private String word;
    private int choice;
    private int difficultyLevel;
    private int wrongGuessCount;
    private boolean won;
    private View highScore_view;
    private String currentScoreString;
    private String textViewText;
    private MediaPlayer soundMP;
    private ConfettiManager confettiManager;

    private GameOverFragment() {
    }

    public static GameOverFragment getInstance() {
        if (instance == null) {
            instance = new GameOverFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        highScore_view = inflater.inflate(R.layout.fragment_game_over, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("HIGHSCORE");

        if (getArguments() != null) {
            choice = getArguments().getInt("choice");
            difficultyLevel = getArguments().getInt("difLevel");
            wrongGuessCount = getArguments().getInt("wrong_guess_count");
            won = getArguments().getBoolean("won");
            word = getArguments().getString("word");
        }
        textView = highScore_view.findViewById(R.id.textView_highscore);
        button_play = highScore_view.findViewById(R.id.button_play_from_main);
        button_settings = highScore_view.findViewById(R.id.button_settings);
        listView = highScore_view.findViewById(R.id.list_view_game_over);
        button_play.setOnClickListener(this);
        button_settings.setOnClickListener(this);

        //Beregn point
        int scoreInt = (int) Math.ceil((1000.0 / 7.0) * (7 - wrongGuessCount));
        String scoreString = scoreInt + "";
        scoreString = scoreString.replaceFirst("^0+(?!$)", "");

        playSoundAsync();
        //TODO ryd op
        if (won) {
            confettiManager = CommonConfetti.rainingConfetti(container, new int[]{Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN}).infinite();
            Toast.makeText(getActivity(), "Flot, du vandt!", Toast.LENGTH_LONG).show();
            textViewText = "Flot klaret! Du har vundet! Ordet var " + word + "." +
                    "\nAntal forkerte gæt: " + wrongGuessCount + "\n\nPoint: " + scoreString;
        } else {
            Toast.makeText(getActivity(), "Trist, du tabte!", Toast.LENGTH_LONG).show();
            textViewText = "Trist, du tabte! Ordet var: \"" + word + "\"" +
                    "\nAntal forkerte gæt: " + wrongGuessCount + "\n\nPoint: " + scoreString;
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        currentScoreString = (scoreString + " point - " + formatter.format(date));
        textView.setText(textViewText);
        Helper.getInstance().addNewHighscoreToList(currentScoreString);
        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.list_element, Helper.getHighscoreList()));
        return highScore_view;
    }

    public void onClick(View v) {
        if (v == button_play) {

            if (choice == 4) {
                //todo delete addToBackStack
                getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, new WordListFragment())
                        .addToBackStack(null)
                        .commit();

            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("choice", choice);
                bundle.putInt("difLevel", difficultyLevel);
                PlayFragment playFragment = PlayFragment.getInstance();
                playFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, playFragment)
                        .addToBackStack(null)
                        .commit();
            }

        } else if (v == button_settings) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, SettingsFragment.getInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void playSoundAsync() {
        MediaPlayerAsync runnable = new MediaPlayerAsync();
        new Thread(runnable).start();
    }

    @Override
    public void onStop() {
        super.onStop();
        confettiManager.setEmissionDuration(1);
    }

    class MediaPlayerAsync implements Runnable {

        @Override
        public void run() {
            if (won == true) {
                try {
                    soundMP = MediaPlayer.create(getActivity(), R.raw.tada1);
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    soundMP = MediaPlayer.create(getActivity(), R.raw.loser);
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            soundMP.start();
        }
    }
}
