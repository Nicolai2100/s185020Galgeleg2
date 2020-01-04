package com.s185020.Galgeleg_3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_3.MainActivity;
import com.s185020.Galgeleg_3.R;

import java.util.List;

public class ChooseWordListFragment extends Fragment implements View.OnClickListener {
    private Button playButton, saveWordButton;
    private ListView listView;
    private EditText editText;
    private String wordChosen = "snapsak";
    private List<String> words;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View choseWordView = inflater.inflate(R.layout.fragment_choose_word_fragment, container, false);

        playButton = choseWordView.findViewById(R.id.button_play_choose_word);
        saveWordButton = choseWordView.findViewById(R.id.button_choose_word);
        playButton.setOnClickListener(this);
        saveWordButton.setOnClickListener(this);
        editText = choseWordView.findViewById(R.id.editText_choose_word);
        listView = choseWordView.findViewById(R.id.list_view_choose_word);

//        listView.setOnItemClickListener(this);

        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.list_element, MainActivity.getSavedWordsList() ));
        return choseWordView;
    }

    @Override
    public void onClick(View v) {
        if (v == playButton){
            Bundle bundle = new Bundle();
            bundle.putInt("choice", 4);
            bundle.putString("wordChosen", wordChosen);

          //todo deleted addToBackStack

            Fragment fragment = PlayFragment.getInstance();
           fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment)
                    .commit();
        }
        if (v == saveWordButton){
            String newWord = editText.getText().toString();
            MainActivity.addSavedWordToList(newWord);
            listView.setAdapter(new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.list_element, MainActivity.getSavedWordsList() ));

        }
        if (v == listView){

        }
    }
}
