package com.s185020.Galgeleg_2.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_2.MainActivity;
import com.s185020.Galgeleg_2.R;
import com.s185020.Galgeleg_2.logic.Galgelogik;

import java.util.HashMap;


public class PlayFragment extends Fragment implements View.OnClickListener {

    private static PlayFragment instance = null;

    private Handler mainHandler = new Handler();
    private Galgelogik spil;
    private Button button1;
    private EditText editText;
    private TextView textViewWord;
    private TextView textViewWrongCount;
    private TextView textViewWrongLetters;
    private ImageView imageView;
    private HashMap<Integer, Integer> hashMapPictureMistake;
    private Drawable drawable;
    private int choice;

    private PlayFragment() { }

    public static PlayFragment getInstance() {
        if (instance == null) {
            instance = new PlayFragment();
        }
        return instance;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View play_view = inflater.inflate(R.layout.fragment_play, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("GALGESPILLET");

        instaHashMap();
        spil = new Galgelogik();
        spil.nulstil();

        //todo slet
        System.out.println(spil.getOrdet());
        button1 = play_view.findViewById(R.id.button);
        button1.setOnClickListener(this);
        editText = play_view.findViewById(R.id.editText);
        textViewWord = play_view.findViewById(R.id.textViewWord);
        textViewWrongCount = play_view.findViewById(R.id.textViewWrongCount);
        textViewWrongLetters = play_view.findViewById(R.id.textViewWrongLetters);
        imageView = play_view.findViewById(R.id.imageView);
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.galge, null);
        imageView.setImageDrawable(drawable);
//        textViewWord.setText(spil.getSynligtOrd());
        textViewWrongCount.setText(getResources().getString(R.string.wrong_guess_count) + 0);

        if ((choice == 1 | choice == 2)) {
            //todo ændre fonten på toasts
            // Toast.makeText(getActivity(), "Henter ord!", Toast.LENGTH_LONG).show();
            if (choice == 1) {
                try {
                    //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
                    getWordFromDR();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (choice == 2) {
                //todo implementer
                try {
                    spil.hentOrdFraRegneark(""+SettingsFragment.getDifficultyLevel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (choice == 3) {
                //Ord hentes fra GalgeLogik-klassen
                System.out.println("Ord hentes fra GalgeLogik-klassen");
            }
        }
        editText.setHint("Tast");
        textViewWord.setText(spil.getSynligtOrd());
        return play_view;
    }

    @Override
    public void onClick(View v) {
        if (v == button1) {
            Editable guessEdit = editText.getText();
            String guessString = guessEdit.toString();

            if (guessString.length() > 1) {
                Toast.makeText(getActivity(), "Kun et bogstav!", Toast.LENGTH_SHORT).show();

            } else if (guessString.length() < 1) {
                Toast.makeText(getActivity(), "Skriv et bogstav!", Toast.LENGTH_SHORT).show();

            } else if (!guessString.matches("[a-zA-ZÆØÅæøå]")) {
                Toast.makeText(getActivity(), "Kun bogstaver!", Toast.LENGTH_SHORT).show();

            } else {
                spil.gætBogstav(guessString.toLowerCase());
                textViewWord.setText(spil.getSynligtOrd());
                textViewWrongCount.setText(getResources().getString(R.string.wrong_guess_count) + spil.getAntalForkerteBogstaver());

                if (!spil.erSidsteBogstavKorrekt() && spil.getAntalForkerteBogstaver() <= 6) {
                    int rCode = hashMapPictureMistake.get(spil.getAntalForkerteBogstaver());
                    drawable = ResourcesCompat.getDrawable(getResources(), rCode, null);
                    imageView.setImageDrawable(drawable);
                    textViewWrongLetters.append(guessString);
                    Toast.makeText(getActivity(), "Forkert bogstav", Toast.LENGTH_SHORT).show();

                } else if (spil.erSpilletSlut()) {

                    if (spil.erSpilletTabt()) {
                        gameOver(false, spil.getOrdet(), spil.getAntalForkerteBogstaver());
                    }
                    if (spil.erSpilletVundet()) {
                        gameOver(true, spil.getOrdet(), spil.getAntalForkerteBogstaver());
                    }
                }
                editText.setText("");
            }
        }
    }

    private void gameOver(boolean won, String word, int wrongGuessCount) {
        Bundle bundle = new Bundle();
        bundle.putInt("choice", choice);
        bundle.putBoolean("won", won);
        bundle.putString("word", word);
        bundle.putInt("wrong_guess_count", wrongGuessCount);

        HighScoreFragment highScoreFragment = HighScoreFragment.getInstance();;
        highScoreFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, highScoreFragment)
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

    void getWordFromDR() {
        GetWordFromDRRunnable runnable = new GetWordFromDRRunnable();
        new Thread(runnable).start();
    }

    class GetWordFromDRRunnable implements Runnable {
        @Override
        public void run() {
            try {
                spil.hentOrdFraDr();
                Toast.makeText(getActivity(), "Ord hentet fra DR", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}