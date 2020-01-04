package com.s185020.Galgeleg_3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_3.MainActivity;
import com.s185020.Galgeleg_3.R;

public class HelpFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View help_view = inflater.inflate(R.layout.fragment_help, container, false);
        //todo alle steder
        ((MainActivity) getActivity()).setActionBarTitle("INDSTILLINGER");


        return help_view;
    }
}