package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    public static final String PREFS_NAME = "Prefs";
    Spinner boardsizeSpinner;
    int boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boardSize = settings.getInt("boardSize", 3);
        boardsizeSpinner = (Spinner) findViewById(R.id.spinner_boardsize);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.boardsize_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardsizeSpinner.setAdapter(adapter);
        boardsizeSpinner.setOnItemSelectedListener(new BoardSizeSelectionListener());
        boardsizeSpinner.setSelection(boardSize - 3);
    }

    private class BoardSizeSelectionListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(getApplicationContext(), "The board size is " +
                    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
            boardSize = pos + 3;
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }

    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("boardSize", boardSize);
        editor.commit();
    }
}
