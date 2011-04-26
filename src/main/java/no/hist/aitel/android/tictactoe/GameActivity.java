package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class GameActivity extends Activity {
    public static final String PREFS_NAME = "Prefs";

    private GameView gameView;
    private GameView.ICellListener cellListener;
    private int boardSize;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.game);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boardSize = settings.getInt("boardSize", 3);

        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setBoardSize(boardSize);
        gameView.setFocusable(true);
        gameView.setFocusableInTouchMode(true);
        gameView.setCellListener(new MyCellListener());

    }

    private class MyCellListener implements GameView.ICellListener {
        public void onCellSelected() {
            /*if (gameView.getCurrentPlayer() == State.PLAYER1) {
                int cell = gameView.getSelection();
                mButtonNext.setEnabled(cell >= 0);
            }*/
        }
    }
}
