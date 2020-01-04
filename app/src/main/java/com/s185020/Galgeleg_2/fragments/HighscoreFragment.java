package com.s185020.Galgeleg_2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_2.MainActivity;
import com.s185020.Galgeleg_2.R;

public class HighscoreFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View highscore_view = inflater.inflate(R.layout.fragment_highscore, container, false);

        ListView listView = highscore_view.findViewById(R.id.list_view_highscore);
        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.list_element, MainActivity.getHighscoreList()));

        return highscore_view;
    }
}
