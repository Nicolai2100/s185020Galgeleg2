package com.s185020.Galgeleg_2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_2.MainActivity;
import com.s185020.Galgeleg_2.R;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private static SettingsFragment instance = null;
    private Button button_getWordFromDR, button_getWordFromDB, button_play;
    private TextView textView;
    private int difficultyLevel;
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
        et_difLevel.setHint("Tast 1, 2 eller 3");

        button_getWordFromDR.setOnClickListener(this);
        button_getWordFromDB.setOnClickListener(this);
        button_play.setOnClickListener(this);
        et_difLevel.setOnClickListener(this);
        return settings_view;
    }

    public void onClick(View v) {
        if (v == et_difLevel){
            button_play.setVisibility(View.GONE);
        }
        if (v == button_getWordFromDR) {
            startGame(1, 0);
        }
        if (v == button_getWordFromDB) {
            String difLevel = et_difLevel.getText().toString();
            et_difLevel.setText("");

            if (!difLevel.matches("[1-3]")) {
                Toast.makeText(getActivity(), "Kun tal mellem 1 og 3!", Toast.LENGTH_SHORT).show();
            } else {
                closeKeyboard();
                try {
                    difficultyLevel = Integer.parseInt(difLevel);
                } catch (Exception e) {
                    difficultyLevel = 2;
                } finally {
                    startGame(2, difficultyLevel);
                }
            }
        }
        if (v == button_play) {
            startGame(3, 0);
        }
    }

    private void startGame(Integer choice, Integer difficultyLevel) {
        PlayFragment playFragment = PlayFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("choice", choice);
        bundle.putInt("difLevel", difficultyLevel);
        playFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, playFragment)
                .addToBackStack(null)
                .commit();
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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