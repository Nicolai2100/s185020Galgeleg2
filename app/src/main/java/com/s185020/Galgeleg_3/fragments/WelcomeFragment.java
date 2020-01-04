package com.s185020.Galgeleg_3.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.s185020.Galgeleg_3.R;

public class WelcomeFragment extends Fragment {
    private Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View welcome_view = inflater.inflate(R.layout.fragment_welcome, container, false);
        final ConstraintLayout layout = welcome_view.findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });
        return welcome_view;
    }

    private void openSettings() {
        getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, SettingsFragment.getInstance())
                .addToBackStack(null)
                .commit();
    }
}