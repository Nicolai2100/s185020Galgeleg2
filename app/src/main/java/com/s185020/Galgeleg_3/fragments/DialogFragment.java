package com.s185020.Galgeleg_3.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.s185020.Galgeleg_3.MainActivity;

/**
 * Har fået hjælp fra https://www.youtube.com/watch?v=r_87U6oHLFc
 */

public class DialogFragment extends AppCompatDialogFragment {
    private int contextCaller;
    private boolean fromMainActivity = false;
    private DialogFragmentListener listener;
    private String message = null;

    public DialogFragment(String message, int contextInstantiator) {
        this.message = message;
        this.contextCaller = contextInstantiator;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        if (contextCaller == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Obs!").setMessage(message).setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onYesClicked("");
                }
            });
            return builder.create();
        } else if (contextCaller == 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Obs!").setMessage(message).setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onYesClicked("");
                }
            });
            return builder.create();
            /**
             * Taget fra https://stackoverflow.com/questions/10903754/input-text-dialog-android
             */
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Sværhedsgrad");
            // Set up the input
            final EditText input = new EditText(getContext());
            input.setHint("Skriv et tal mellem 1 og 3");
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_Text = input.getText().toString();
                    listener.onYesClicked(m_Text);
                }
            });
            builder.setNegativeButton("Tilbage", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            return builder.create();
        }
    }

    public interface DialogFragmentListener {
        String onYesClicked(String choice);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            if (contextCaller == 1) {
                listener = (DialogFragmentListener) context;
            } else
                listener = (DialogFragmentListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogFragmentListener");
        }
    }
}
