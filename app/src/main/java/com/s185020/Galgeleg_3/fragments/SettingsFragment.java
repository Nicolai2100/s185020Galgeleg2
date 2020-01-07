package com.s185020.Galgeleg_3.fragments;

import android.content.Context;
import android.os.Bundle;
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

import com.s185020.Galgeleg_3.MainActivity;
import com.s185020.Galgeleg_3.R;


public class SettingsFragment extends Fragment implements View.OnClickListener, DialogFragment.DialogFragmentListener {

    private static SettingsFragment instance = null;
    private Button button_getWordFromDR, button_getWordFromDB, button_play, button_play_from_list;
    private int difficultyLevel;
    private View settings_view;

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
        settings_view = inflater.inflate(R.layout.fragment_settings, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("INDSTILLINGER");
        ((MainActivity) getActivity()).getSupportActionBar().show();

        button_getWordFromDR = settings_view.findViewById(R.id.button_dr);
        button_getWordFromDB = settings_view.findViewById(R.id.button_database);
        button_play = settings_view.findViewById(R.id.button_play_from_settings);
        button_play_from_list = settings_view.findViewById(R.id.button_play_from_list);
        button_getWordFromDR.setOnClickListener(this);
        button_getWordFromDB.setOnClickListener(this);
        button_play.setOnClickListener(this);
        button_play_from_list.setOnClickListener(this);
        return settings_view;
    }

    public void onClick(View v) {
        if (v == button_getWordFromDR) {
            startGame(1, 0);
        }
        if (v == button_getWordFromDB) {
            DialogFragment dialogFragment = new DialogFragment("Vælg sværhedsgrad", this.getClass().getSimpleName());
            dialogFragment.setTargetFragment(SettingsFragment.this, 1);
            dialogFragment.show(getFragmentManager(), "");
        }
        if (v == button_play_from_list) {
            Fragment fragment = new WordListFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment)
                    .addToBackStack(null)
                    .commit();
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

    @Override
    public String onYesClicked(String choice) {
        if (!choice.matches("[1-3]")) {
            Toast.makeText(getActivity(), "Skriv et tal mellem 1 og 3!", Toast.LENGTH_SHORT).show();
        } else {
            closeKeyboard();
            try {
                difficultyLevel = Integer.parseInt(choice);
            } catch (Exception e) {
                difficultyLevel = 2;
            } finally {
                startGame(2, difficultyLevel);
            }
        }
        return "";
    }
}
