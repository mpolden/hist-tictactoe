package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {

    private static final String TAG = ServerThread.class.getSimpleName();
    private static final int LISTENING_PORT = 8080;
    private Handler handler;
    private Context context;
    private String localIp;
    private ServerSocket serverSocket;

    public ServerThread(Context context, Handler handler, String localIp) {
        this.context = context;
        this.handler = handler;
        this.localIp = localIp;
    }

    private void sendMessage(String s) {
        final Message msg = handler.obtainMessage();
        final Bundle bundle = new Bundle();
        bundle.putString("msg", s);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    private void sendMessage(int resId) {
        sendMessage(context.getResources().getString(resId));
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(LISTENING_PORT);
            sendMessage(String.format(context.getResources().getString(R.string.waiting_for_player), localIp));
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
                    //runOnUiThread(doit);
                }
                break;
            } catch (IOException e) {
                sendMessage(R.string.connection_failed);
                Log.w(TAG, "IOException", e);
            }
        }
    }
}
