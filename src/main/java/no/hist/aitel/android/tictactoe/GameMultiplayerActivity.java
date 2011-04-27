package no.hist.aitel.android.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class GameMultiplayerActivity extends Activity {

    private static final String TAG = GameMultiplayerActivity.class.getSimpleName();
    private static final int JOIN_DIALOG_ID = 0;
    private static final int HOST_DIALOG_ID = 1;
    private static final int LISTENING_PORT = 8080;
    private String ip;
    private ServerSocket serverSocket;
    private TextView status;

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
                serverThread.start();
            }
        });
        this.ip = findIpAddress();
    }

    private String findIpAddress() {
        final WifiInfo wifiInfo = ((WifiManager) getSystemService(WIFI_SERVICE)).getConnectionInfo();
        return Formatter.formatIpAddress(wifiInfo.getIpAddress());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case JOIN_DIALOG_ID: {
                final Dialog joinDialog = new Dialog(GameMultiplayerActivity.this);
                joinDialog.setContentView(R.layout.joingamedialog);
                joinDialog.setTitle("Join game");
                joinDialog.setCancelable(true);
                TextView tv_joingame = (TextView) joinDialog.findViewById(R.id.textview_joingame);
                tv_joingame.setText(R.string.join_game_dialog);
                Button button_ok = (Button) joinDialog.findViewById(R.id.button_joingame_ok);
                button_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        joinDialog.dismiss();
                    }
                });
                Button button_cancel = (Button) joinDialog.findViewById(R.id.button_joingame_cancel);
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        joinDialog.dismiss();
                    }
                });
                return joinDialog;
            }
            case HOST_DIALOG_ID: {
                final Dialog hostDialog = new Dialog(GameMultiplayerActivity.this);
                hostDialog.setContentView(R.layout.hostgamedialog);
                hostDialog.setTitle("Host game");
                hostDialog.setCancelable(true);
                TextView tv_hostgame = (TextView) hostDialog.findViewById(R.id.textview_hostgame);
                tv_hostgame.setText(getResources().getString(R.string.host_game_dialog, ip));
                this.status = (TextView) hostDialog.findViewById(R.id.textview_hostgame_status);
                Button button_cancel = (Button) hostDialog.findViewById(R.id.button_hostgame_cancel);
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopServer();
                        hostDialog.dismiss();
                    }
                });
                return hostDialog;
            }
            default: {
                return null;
            }
        }
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            status.setText(msg.getData().getString("msg"));
        }
    };

    private void sendMessage(String s) {
        final Message msg = handler.obtainMessage();
        final Bundle bundle = new Bundle();
        bundle.putString("msg", s);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    private void sendMessage(int resId) {
        sendMessage(getResources().getString(resId));
    }

    private void stopServer() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                Log.d(TAG, "Closed server socket");
            } catch (IOException e) {
                Log.w(TAG, "Could not close socket", e);
            }
        }
    }

    private final Thread serverThread = new Thread() {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(LISTENING_PORT);
                sendMessage(R.string.waiting_for_player);
            } catch (IOException e) {
                Log.e(TAG, "IOException", e);
            }
            if (serverSocket == null) {
                sendMessage(R.string.server_socket_failed);
                Log.e(TAG, "Server socket is null");
                return;
            }
            while (true) {
                Socket client = null;
                try {
                    client = serverSocket.accept();
                    sendMessage(R.string.connected);
                } catch (IOException e) {
                    Log.e(TAG, "IOException", e);
                }
                if (client == null) {
                    sendMessage(R.string.client_socket_failed);
                    Log.e(TAG, "Client socket is null");
                    return;
                }
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        Log.d(TAG, String.format("Received: %s", line));
                        // do something here
                    }
                    break;
                } catch (IOException e) {
                    sendMessage(R.string.connection_failed);
                    Log.w(TAG, "IOException", e);
                }
            }
        }
    };
}