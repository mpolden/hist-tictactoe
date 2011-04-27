package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private static final String TAG = ClientThread.class.getSimpleName();
    private static final int LISTENING_PORT = 8080;
    private String remoteIp;
    private Handler handler;
    private Context context;
    private BufferedReader in;
    private PrintWriter out;

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
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    @Override
    public void run() {
        Socket s = null;
        try {
            s = new Socket(remoteIp, LISTENING_PORT);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
        if (s == null) {
            sendMessage(R.string.could_not_connect);
            return;
        }
        try {
            this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
    }
}
