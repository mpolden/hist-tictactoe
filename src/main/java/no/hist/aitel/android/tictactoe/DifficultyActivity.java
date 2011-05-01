package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty);
        Button easy = (Button) findViewById(R.id.button_easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("mode", GameActivity.MODE_SINGLEPLAYER);
                intent.putExtra("difficulty", GameAI.EASY);
                startActivity(intent);
            }
        });
    }
}
