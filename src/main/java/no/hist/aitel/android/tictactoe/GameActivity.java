package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameActivity extends Activity {

    private GameView gameView;
    private GameView.ICellListener cellListener;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.game);
        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setFocusable(true);
        gameView.setFocusableInTouchMode(true);
        gameView.setCellListener(new MyCellListener());
        gameView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int sxy = gameView.getSxy();
                    x = (x - 0) / sxy;
                    y = (y - 0) / sxy;
                    Toast.makeText(getApplicationContext(), String.valueOf(x + " " + y), Toast.LENGTH_SHORT).show();
                    if (gameView.isEnabled() && x >= 0 && x < gameView.getBoardSize() && y >= 0 & y < gameView.getBoardSize()) {
                        int cell = x + gameView.getBoardSize() * y;
                        GamePlayer state = cell == gameView.getSelectedCell() ? gameView.getSelectedValue() : gameView.getController().get(x, y);
                        state = state == GamePlayer.EMPTY ? gameView.getCurrentPlayer() : GamePlayer.EMPTY;
                        gameView.setSelectedCell(cell);
                        gameView.setSelectedValue(state);
                        if (gameView.getController().get(x, y) == GamePlayer.EMPTY) {
                            setCell(x, y, state);
                            if (gameView.getCurrentPlayer() == GamePlayer.PLAYER1) {
                                gameView.setCurrentPlayer(GamePlayer.PLAYER2);
                            } else {
                                gameView.setCurrentPlayer(GamePlayer.PLAYER1);
                            }
                        }
                        if (cellListener != null) {
                            cellListener.onCellSelected();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void setCell(int x, int y, GamePlayer player) {
        if(gameView.getController().put(x, y, player) == GameState.VALID_MOVE) {
            GameState s = gameView.getController().getState();
            switch (s) {
                case WIN: {
                    
                }
                case DRAW: {

                }
            }
        }
        gameView.invalidate();
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
