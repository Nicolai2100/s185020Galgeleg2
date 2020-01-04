package com.s185020.Galgeleg_2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_2.R;

public class ChooseWordListFragment extends Fragment implements View.OnClickListener {
    private Button playButton, selectWordButton;
    private ListView listView;
    private EditText editText;
    private String wordChosen = "snapsak";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View choseWordView = inflater.inflate(R.layout.fragment_choose_word_fragment, container, false);

        playButton = choseWordView.findViewById(R.id.button_play_choose_word);
        selectWordButton = choseWordView.findViewById(R.id.button_choose_word);
        playButton.setOnClickListener(this);
        selectWordButton.setOnClickListener(this);
        editText = choseWordView.findViewById(R.id.editText_choose_word);
        listView = choseWordView.findViewById(R.id.list_view_choose_word);
//        listView.setOnItemClickListener(this);

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
        if (v == selectWordButton){

        }
        if (v == listView){

        }
    }
}
