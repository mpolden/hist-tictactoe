package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    private GameView gameView;
    private GameView.ICellListener cellListener;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.game);

        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setBoardSize(4);
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
