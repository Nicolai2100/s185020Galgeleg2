package com.s185020.Galgeleg_3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_3.MainActivity;
import com.s185020.Galgeleg_3.R;
import com.s185020.Galgeleg_3.logic.Helper;

public class HighscoreFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View highscore_view = inflater.inflate(R.layout.fragment_highscore, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("HIGHSCORE");
        ListView listView = highscore_view.findViewById(R.id.list_view_highscore);
        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.list_element, Helper.getHighscoreList()));
        return highscore_view;
    }
}
