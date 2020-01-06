package com.s185020.Galgeleg_3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_3.MainActivity;
import com.s185020.Galgeleg_3.R;
import com.s185020.Galgeleg_3.logic.Helper;

public class WordListFragment extends Fragment implements View.OnClickListener, DialogFragment.DialogFragmentListener {
    private Button saveWordButton;
    private ListView listView;
    private EditText editText;
    private String wordChosen = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View choseWordView = inflater.inflate(R.layout.fragment_word_list, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("VÆLG ORD FRA LISTE");

        saveWordButton = choseWordView.findViewById(R.id.button_choose_word);
        saveWordButton.setOnClickListener(this);
        editText = choseWordView.findViewById(R.id.editText_choose_word);
        listView = choseWordView.findViewById(R.id.list_view_word_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wordChosen = listView.getItemAtPosition(position).toString();
                DialogFragment dialogFragment = new DialogFragment("Ønsker du at spille med ordet " + wordChosen + "?", false);
                dialogFragment.setTargetFragment(WordListFragment.this, 1);
                dialogFragment.show(getFragmentManager(), "");
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String wordToEdit = listView.getItemAtPosition(position).toString();
                editText.setText(wordToEdit);
                Helper.getSavedWordsList().remove(position);
                return true;
            }
        });
        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.list_element, Helper.getSavedWordsList()));
        return choseWordView;
    }

    @Override
    public void onClick(View v) {
        if (v == saveWordButton) {
            String newWord = editText.getText().toString();
            newWord = newWord.trim();
            if (newWord.length() < 1){
                Toast.makeText(getActivity(), "Det indtastede ord er for kort!", Toast.LENGTH_LONG).show();
                return;
            }
            Helper.getInstance().addSavedWordToList(newWord);
            listView.setAdapter(new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.list_element, Helper.getSavedWordsList()));
            MainActivity.getInstance().closeKeyboard();
        }
    }

    @Override
    public void onYesClicked() {
        //todo deleted addToBackStack
        Bundle bundle = new Bundle();
        bundle.putInt("choice", 4);
        bundle.putString("wordChosen", wordChosen);
        Fragment fragment = PlayFragment.getInstance();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment)
                .commit();
    }
}
