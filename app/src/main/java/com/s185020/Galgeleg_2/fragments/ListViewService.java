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

public class ListViewService extends Fragment {

    public static ListViewService instance = null;
    private ListView listView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View listViewService_view = inflater.inflate(R.layout.fragment_welcome, container, false);

        listView = listView.findViewById(R.id.list_view);

        return listViewService_view;
    }

    private ListViewService() {
        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.listview_layout, R.id.list_element, MainActivity.getHighscoreList()));
    }

    public static ListViewService getInstance() {
        if (instance == null) {
            instance = new ListViewService();
        }
        return instance;
    }


}
