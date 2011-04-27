package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MultiplayerModeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayermode);
        Button sharedscreen = (Button) findViewById(R.id.button_sharedscreen);
        sharedscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("mode", GameActivity.MODE_MULTIPLAYER_SHARED);
                startActivity(intent);
            }
        });
        Button network = (Button) findViewById(R.id.button_network);
        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), NetworkActivity.class);
                startActivity(intent);
            }
        });
    }
}
