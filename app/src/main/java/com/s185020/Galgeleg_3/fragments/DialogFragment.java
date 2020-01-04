package com.s185020.Galgeleg_3.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * Har fået hjælp fra https://www.youtube.com/watch?v=r_87U6oHLFc
 */

public class DialogFragment extends AppCompatDialogFragment {
    private boolean fromMainActivity;
    private DialogFragmentListener listener;
    private String message = null;

    public DialogFragment(String message, boolean fromMainActivity) {
        this.message = message;
        this.fromMainActivity = fromMainActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (fromMainActivity) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Obs!").setMessage(message).setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onYesClicked();
                }
            });
            return builder.create();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Obs!").setMessage(message).setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onYesClicked();
                }
            });
            return builder.create();
        }
    }

    public interface DialogFragmentListener {
        void onYesClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            if (fromMainActivity) {
                listener = (DialogFragmentListener) context;
            } else
                listener = (DialogFragmentListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogFragmentListener");
        }
    }
}
