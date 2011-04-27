package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private static final String TAG = ServerThread.class.getSimpleName();
    private static final int LISTENING_PORT = 8080;
    private Handler handler;
    private Context context;
    private String localIp;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;

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

    public BufferedReader getIn() {
        return in;
    }

    public void send(String msg) {
        if (out != null) {
            out.println(msg);
        }
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
                this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
                this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (IOException e) {
                sendMessage(R.string.connection_failed);
                Log.w(TAG, "IOException", e);
            }
        }
    }
}
