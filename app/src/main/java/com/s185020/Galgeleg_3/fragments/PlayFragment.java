package com.s185020.Galgeleg_3.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_3.MainActivity;
import com.s185020.Galgeleg_3.R;
import com.s185020.Galgeleg_3.logic.Galgelogik;

import java.util.HashMap;
import java.util.Random;


public class PlayFragment extends Fragment implements View.OnClickListener {

    private static PlayFragment instance = null;

    private Galgelogik spil;
    private Button button1;
    private EditText editText;
    private TextView textViewWord;
    private TextView textViewWrongCount;
    private ImageView imageView;
    private HashMap<Integer, Integer> hashMapPictureMistake;
    private Drawable drawable;
    private String wrongLettersGuessed = "";
    //Bundle
    private int choice;
    private int difLevel;
    private String wordChosen;

    private PlayFragment() {
    }

    public static PlayFragment getInstance() {
        if (instance == null) {
            instance = new PlayFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View play_view = inflater.inflate(R.layout.fragment_play, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("GALGESPILLET");
        instaHashMap();
        spil = new Galgelogik();

        if (getArguments() != null) {
            choice = getArguments().getInt("choice");
            difLevel = getArguments().getInt("difLevel");
            wordChosen = getArguments().getString("wordChosen");
        }

        button1 = play_view.findViewById(R.id.button);
        button1.setOnClickListener(this);
        editText = play_view.findViewById(R.id.editText);
        textViewWord = play_view.findViewById(R.id.textViewWord);
        textViewWrongCount = play_view.findViewById(R.id.textViewWrongCount);
        imageView = play_view.findViewById(R.id.imageView);
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.galge, null);
        imageView.setImageDrawable(drawable);

        if (choice == 1 || choice == 2) {
            try {
                button1.setText("Venter på tråd");
                getWordFromNet();
                Toast.makeText(getActivity(), "Ordet er klar!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (choice == 4) {
            spil.tilføjOrd(wordChosen);
        } else {
            Toast.makeText(getActivity(), "Ord hentes fra GalgeLogik-klassen", Toast.LENGTH_SHORT).show();
        }
        editText.setHint("Tast");
        textViewWord.setText(spil.getSynligtOrd());
        //winOrLose();
        return play_view;
    }

    @Override
    public void onClick(View v) {
        if (v == button1) {
            Editable guessEdit = editText.getText();
            String guessString = guessEdit.toString();
            editText.setText("");
            if (guessString.length() > 1) {
                Toast.makeText(getActivity(), "Kun et bogstav!", Toast.LENGTH_SHORT).show();

            } else if (guessString.length() < 1) {
                Toast.makeText(getActivity(), "Skriv et bogstav!", Toast.LENGTH_SHORT).show();

            } else if (!guessString.matches("[a-zA-ZÆØÅæøå]")) {
                Toast.makeText(getActivity(), "Kun bogstaver!", Toast.LENGTH_SHORT).show();

            } else {
                spil.gætBogstav(guessString.toLowerCase());
                textViewWord.setText(spil.getSynligtOrd());

                if (!spil.erSidsteBogstavKorrekt() && spil.getAntalForkerteBogstaver() <= 6) {
                    if (!wrongLettersGuessed.contains(guessString)) {
                        wrongLettersGuessed = wrongLettersGuessed + guessString;
                    }
                    textViewWrongCount.setText(getResources().getString(R.string.wrong_guess_count) + " " + spil.getAntalForkerteBogstaver() +
                            ". \n "+ wrongLettersGuessed + " ");

                    int rCode = hashMapPictureMistake.get(spil.getAntalForkerteBogstaver());
                    drawable = ResourcesCompat.getDrawable(getResources(), rCode, null);
                    imageView.setImageDrawable(drawable);

                    Toast.makeText(getActivity(), "Forkert bogstav", Toast.LENGTH_SHORT).show();

                } else if (spil.erSpilletSlut()) {
                    wrongLettersGuessed = "";
                    if (spil.erSpilletTabt()) {
                        gameOver(false, spil.getOrdet(), spil.getAntalForkerteBogstaver());
                    }
                    if (spil.erSpilletVundet()) {
                        gameOver(true, spil.getOrdet(), spil.getAntalForkerteBogstaver());
                    }
                }
            }
        }
    }

    private void gameOver(boolean won, String word, int wrongGuessCount) {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        Bundle bundle = new Bundle();
        bundle.putInt("choice", choice);
        bundle.putInt("difLevel", difLevel);
        bundle.putBoolean("won", won);
        bundle.putString("word", word);
        bundle.putInt("wrong_guess_count", wrongGuessCount);

        GameOverFragment gameOverFragment = GameOverFragment.getInstance();
        gameOverFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, gameOverFragment)
                .addToBackStack(null)
                .commit();
    }

    private void instaHashMap() {
        hashMapPictureMistake = new HashMap<>();
        hashMapPictureMistake.put(0, R.drawable.galge);
        hashMapPictureMistake.put(1, R.drawable.forkert1);
        hashMapPictureMistake.put(2, R.drawable.forkert2);
        hashMapPictureMistake.put(3, R.drawable.forkert3);
        hashMapPictureMistake.put(4, R.drawable.forkert4);
        hashMapPictureMistake.put(5, R.drawable.forkert5);
        hashMapPictureMistake.put(6, R.drawable.forkert6);
    }

    private void getWordFromNet() {

        GetWordFromDRRunnable runnable = new GetWordFromDRRunnable();
        new Thread(runnable).start();
    }

    class GetWordFromDRRunnable implements Runnable {

        @Override
        public void run() {
            if (choice == 1) {
                try {
                    button1.setEnabled(false);
                    spil.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (choice == 2) {
                try {
                    spil.hentOrdFraRegneark("" + difLevel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewWord.setText(spil.getSynligtOrd());
                    button1.setEnabled(true);
                    button1.setText("Gæt");
                }
            });
        }
    }

    //todo slet
    private void winOrLose() {
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    win();
                    //lose();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        t.start();
    }

    private void lose() {
        String ordet = spil.getOrdet();

        while (spil.erSpilletSlut() != true) {
            Random r = new Random();
            char c = (char) (r.nextInt(26) + 'a');
            if (!ordet.contains(Character.toString(c)))
                spil.gætBogstav(Character.toString(c));
        }
    }

    private void win() {
        String ordet = spil.getOrdet();
        for (int i = 0; i < ordet.length(); i++) {
            spil.gætBogstav(Character.toString(ordet.charAt(i)));
        }
    }
}