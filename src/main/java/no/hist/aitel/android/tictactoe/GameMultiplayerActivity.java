package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class GameMultiplayerActivity extends Activity {

    private static final int JOIN_DIALOG_ID = 0;
    private static final int HOST_DIALOG_ID = 1;
    private static final int LISTENING_PORT = 8080;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer);
        findViewById(R.id.button_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(JOIN_DIALOG_ID);
            }
        });
        findViewById(R.id.button_host).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(HOST_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case JOIN_DIALOG_ID: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.join_game_dialog)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                return builder.create();
            }
            case HOST_DIALOG_ID: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.host_game_dialog)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                return builder.create();
            }
            default: {
                return null;
            }
        }
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        super.onPrepareDialog(id, dialog, args);
    }

    private void hostGame() {
    }

    private void joinGame() {
    }
}