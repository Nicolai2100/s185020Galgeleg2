package com.s185020.Galgeleg_3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.s185020.Galgeleg_3.fragments.DialogFragment;
import com.s185020.Galgeleg_3.fragments.HelpFragment;
import com.s185020.Galgeleg_3.fragments.HighscoreFragment;
import com.s185020.Galgeleg_3.fragments.WelcomeFragment;
import com.s185020.Galgeleg_3.logic.Helper;

public class MainActivity extends AppCompatActivity implements DialogFragment.DialogFragmentListener {

    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentLayout, new WelcomeFragment())
                    .commit();
        }
        setTitle("GALGELEG");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        instance = this;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.item_help) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, new HelpFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.item_delete_highscore) {
            new DialogFragment("Ønsker du at slette den gemte highscoreliste?", true).show(getSupportFragmentManager(), "");
            return true;
        }
        if (item.getItemId() == R.id.item_view_highscore) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, new HighscoreFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.item_delete_saved_words) {
            Helper.getInstance().deleteSavedWords();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void onYesClicked() {
        //Highscore slettes
        Helper.getInstance().deleteSavedHighscore();
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
