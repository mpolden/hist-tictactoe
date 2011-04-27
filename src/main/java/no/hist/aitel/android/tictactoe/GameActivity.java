package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class GameActivity extends Activity {

    public static final int MODE_SINGLEPLAYER = 0;
    public static final int MODE_MULTIPLAYER_SHARED = 1;
    public static final int MODE_MULTIPLAYER_JOIN = 2;
    public static final int MODE_MULTIPLAYER_HOST = 3;
    private static final String TAG = GameActivity.class.getSimpleName();
    private GameView gameView;
    private TextView status;
    private int mode;
    private ServerThread server;
    private ClientThread client;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.game);
        this.mode = getIntent().getExtras().getInt("mode");
        status = (TextView) findViewById(R.id.status);
        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setFocusable(true);
        gameView.setFocusableInTouchMode(true);
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
                        GamePlayer state = cell == gameView.getSelectedCell() ? gameView.getSelectedValue() : gameView.getBoard().get(x, y);
                        state = state == GamePlayer.EMPTY ? gameView.getCurrentPlayer() : GamePlayer.EMPTY;
                        gameView.setSelectedCell(cell);
                        gameView.setSelectedValue(state);
                        if (gameView.getBoard().get(x, y) == GamePlayer.EMPTY) {
                            setCell(x, y, state);
                            if (gameView.getBoard().getState() == GameState.NEUTRAL) {
                                if (gameView.getCurrentPlayer() == GamePlayer.PLAYER1) {
                                    gameView.setCurrentPlayer(GamePlayer.PLAYER2);
                                    status.setText("Player 2's turn");
                                } else {
                                    gameView.setCurrentPlayer(GamePlayer.PLAYER1);
                                    status.setText("Player 1's turn");
                                }
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        switch (mode) {
            case MODE_SINGLEPLAYER: {
                break;
            }
            case MODE_MULTIPLAYER_SHARED: {
                break;
            }
            case MODE_MULTIPLAYER_JOIN: {
                String remoteIp = getIntent().getExtras().getString("remoteIp");
                this.client = new ClientThread(getApplicationContext(), handler, remoteIp);
                client.start();
                new Thread() {
                    @Override
                    public void run() {
                        String line;
                        try {
                            while ((line = client.getIn().readLine()) != null) {
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "IOException", e);
                        }
                    }
                };
            }
            case MODE_MULTIPLAYER_HOST: {
                this.server = new ServerThread(getApplicationContext(), handler, findIpAddress());
                server.start();
                new Thread() {
                    @Override
                    public void run() {
                        String line;
                        try {
                            while ((line = server.getIn().readLine()) != null) {
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "IOException", e);
                        }
                    }
                };
            }
        }
    }

    public void setCell(int x, int y, GamePlayer player) {
        if (gameView.getBoard().put(x, y, player) == GameState.VALID_MOVE) {
            GameState s = gameView.getBoard().getState();
            switch (s) {
                case WIN: {
                    gameView.setEnabled(false);
                    status.setText(player.toString() + " WINS!");
                    break;
                }
                case DRAW: {
                    gameView.setEnabled(false);
                    status.setText("DRAW");
                    break;
                }
            }
        }
        gameView.invalidate();
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            status.setText(msg.getData().getString("msg"));
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (client != null) {
            client.close();
        }
        if (server != null) {
            server.close();
        }
    }

    private String findIpAddress() {
        final WifiInfo wifiInfo = ((WifiManager) getSystemService(WIFI_SERVICE)).getConnectionInfo();
        return Formatter.formatIpAddress(wifiInfo.getIpAddress());
    }
}

