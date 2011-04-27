package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
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

    private static final String PREFS_NAME = "Prefs";
    public static final int MODE_SINGLEPLAYER = 0;
    public static final int MODE_MULTIPLAYER_SHARED = 1;
    public static final int MODE_MULTIPLAYER_JOIN = 2;
    public static final int MODE_MULTIPLAYER_HOST = 3;
    private static final String TAG = GameActivity.class.getSimpleName();
    private GameView gameView;
    private TextView status;
    private int mode;
    private int boardSize;
    private int inarow;
    private ServerThread server;
    private ClientThread client;
    private boolean canMove;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.game);
        this.mode = getIntent().getExtras().getInt("mode");
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        boardSize = settings.getInt("boardSize", 3);
        inarow = settings.getInt("inarow", boardSize);
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
                    if (canMove && x >= 0 && x < gameView.getBoardSize() && y >= 0 & y < gameView.getBoardSize()) {
                        int cell = x + gameView.getBoardSize() * y;
                        GamePlayer state = cell == gameView.getSelectedCell() ? gameView.getSelectedValue() : gameView.getBoard().get(x, y);
                        state = state == GamePlayer.EMPTY ? gameView.getCurrentPlayer() : GamePlayer.EMPTY;
                        gameView.setSelectedCell(cell);
                        gameView.setSelectedValue(state);
                        if (gameView.getBoard().get(x, y) == GamePlayer.EMPTY) {
                            setCell(x, y, state);
                            canMove = false;
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
                gameView.makeBoard(boardSize, inarow);
                break;
            }
            case MODE_MULTIPLAYER_SHARED: {
                gameView.makeBoard(boardSize, inarow);
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
                                if (line.startsWith("size ")) {
                                    String[] sizes = line.split(" ");
                                    boardSize = Integer.parseInt(sizes[1]);
                                    inarow = Integer.parseInt(sizes[2]);
                                    client.send("size ok");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gameView.makeBoard(boardSize, inarow);
                                        }
                                    });
                                } else {
                                    final int[] xy = parseLine(line);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gameView.getBoard().put(xy[0], xy[1], GamePlayer.PLAYER1);
                                            canMove = true;
                                        }
                                    });
                                }
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "IOException", e);
                        }
                    }
                };
            }
            case MODE_MULTIPLAYER_HOST: {
                gameView.makeBoard(boardSize, inarow);
                this.server = new ServerThread(getApplicationContext(), handler, findIpAddress());
                server.start();
                new Thread() {
                    @Override
                    public void run() {
                        String line;
                        try {
                            while ((line = server.getIn().readLine()) != null) {
                                if ("size ok".equals(line)) {
                                    canMove = true;
                                } else {
                                    final int[] xy = parseLine(line);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gameView.getBoard().put(xy[0], xy[1], GamePlayer.PLAYER2);
                                            canMove = true;
                                        }
                                    });
                                }
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "IOException", e);
                        }
                    }
                };
            }
        }
    }

    private int[] parseLine(String line) {
        final String[] coordinates = line.split(" ");
        return new int[]{Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
    }

    public void setCell(int x, int y, GamePlayer player) {
        if (gameView.getBoard().put(x, y, player) == GameState.VALID_MOVE) {
            switch (mode) {
                case MODE_MULTIPLAYER_HOST: {
                    server.send(String.format("%d %d", x, y));
                    break;
                }
                case MODE_MULTIPLAYER_JOIN: {
                    client.send(String.format("%d %d", x, y));
                    break;
                }
            }
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
            if (msg.arg1 == 1) {
                server.send(String.format("size %d %d", boardSize, inarow));
            } else {
                status.setText(msg.getData().getString("msg"));
            }
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

