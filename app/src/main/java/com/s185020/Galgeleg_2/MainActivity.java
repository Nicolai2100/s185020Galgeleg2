package com.s185020.Galgeleg_2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.s185020.Galgeleg_2.fragments.HelpFragment;
import com.s185020.Galgeleg_2.fragments.HighScoreFragment;
import com.s185020.Galgeleg_2.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_ACTION_BAR);
        //?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentLayout, fragment)
                    .commit();
        }
        //todo følg op på det her
        setTitle("Velkommen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            Toast.makeText(getActivity(), "ITEM HELP PRESSED", Toast.LENGTH_LONG).show();
            Fragment fragment = new HelpFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, fragment)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.item_delete_highscore) {
            Toast.makeText(this, "ITEM DELETE SAVED PRESSED", Toast.LENGTH_LONG).show();
          //  HighScoreFragment.getInstance().deleteSaved();
            return true;
        }
       /* if (item.getItemId() == R.id.item_show_highscore) {

            Toast.makeText(this, "ITEM DELETE SAVED PRESSED", Toast.LENGTH_LONG).show();
            //todo implemenet
            HighScoreFragment.getInstance().deleteSaved();

            return true;
        }*/

        else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public Activity getActivity() {
        return this;
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
