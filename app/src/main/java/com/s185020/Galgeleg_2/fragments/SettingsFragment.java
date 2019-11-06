package com.s185020.Galgeleg_2.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_2.MainActivity;
import com.s185020.Galgeleg_2.R;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private static SettingsFragment instance = null;
    private Integer choice;
    private Button button_getWordFromDR, button_getWordFromDB, button_play;
    private TextView textView;
    private static int difficultyLevel = 1;
    private EditText et_difLevel;
    private Handler handler = new Handler();
    private Runnable konfettiTask;

    private SettingsFragment() {
    }

    public static SettingsFragment getInstance() {
        if (instance == null) {
            instance = new SettingsFragment();
        }
        return instance;
    }

    public static int getDifficultyLevel() {
        return difficultyLevel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View settings_view = inflater.inflate(R.layout.fragment_settings, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("INDSTILLINGER");

        textView = settings_view.findViewById(R.id.settings_textView);
        button_getWordFromDR = settings_view.findViewById(R.id.button_dr);
        button_getWordFromDB = settings_view.findViewById(R.id.button_database);
        button_play = settings_view.findViewById(R.id.button_play_from_settings);
        et_difLevel = settings_view.findViewById(R.id.et_diffLvl);
        //todo - sæt interaktivt
        button_getWordFromDR.setOnClickListener(this);
        button_getWordFromDB.setOnClickListener(this);
        button_play.setOnClickListener(this);


        //Gets whether the selector wheel wraps when reaching the min/max value.
        //   et_difLevel.setWrapSelectorWheel(true);
/*
        et_difLevel
        et_difLevel.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                System.out.println("Selected Number : " + newVal);
                difficultyLevel = newVal;
            }
        });*/


        return settings_view;
    }

    public void onClick(View v) {
        if (v == button_getWordFromDR) {
            startGame(1);
        }
        //todo hent difficulty level fra settings
        if (v == button_getWordFromDB) {
           HighScoreFragment.getInstance().deleteSaved();
            // startGame(2);

        }
        if (v == button_play) {
            startGame(3);
        }
    }

    private void startGame(Integer choice) {
        PlayFragment playFragment = PlayFragment.getInstance();
        playFragment.setChoice(choice);
        getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, playFragment)
                .addToBackStack(null)
                .commit();
    }
}




/*konfettiTask = new Runnable() {
            public void run() {
                KonfettiView konfetti = settings_view.findViewById(R.id.viewKonfetti);


                /*Size size = new Size() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return null;
                    }

                    @Override
                    public long value() {
                        return 1;
                    }

                    @Override
                    public long min() {
                        return 1;
                    }

                    @Override
                    public long max() {
                        return 1;
                    }

                    @Override
                    public long multiple() {
                        return 1;
                    }
                };*/

//   .addSizes(new Size(12, 5))

/*
                konfetti.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.RECT, Shape.CIRCLE)
                        .addSizes(new Size(12, 5f))
                        .setPosition(-50f, konfetti.getWidth() + 50f, -50f, -50f)
                        .streamFor(300, 5000L);

                        ParticleSystem ps = new ParticleSystem(konfetti);
                        konfetti.start(ps);

                        }
                        };
                        handler.postDelayed(konfettiTask, 2000);
                        */

// udfør om 10 sekunder*/