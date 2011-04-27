package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private static final String TAG = ClientThread.class.getSimpleName();
    private static final int LISTENING_PORT = 8080;
    private String remoteIp;
    private Handler handler;
    private Context context;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public ClientThread(Context context, Handler handler, String remoteIp) {
        this.remoteIp = remoteIp;
        this.handler = handler;
        this.context = context;
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

    public void send(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(remoteIp, LISTENING_PORT);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
        if (socket == null) {
            sendMessage(R.string.could_not_connect);
            return;
        }
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
    }

    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                Log.w(TAG, "Could not close socket socket", e);
            }
        }
    }
}
