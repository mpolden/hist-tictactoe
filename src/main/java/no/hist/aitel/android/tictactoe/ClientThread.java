package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {

    private static final String TAG = ClientThread.class.getSimpleName();
    private String remoteIp;
    private int remotePort;
    private Handler handler;
    private Context context;

    public ClientThread(String remoteIp, int remotePort, Handler handler, Context context) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
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

    @Override
    public void run() {
        Socket s = null;
        try {
            s = new Socket(remoteIp, remotePort);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
        if (s == null) {
            sendMessage(R.string.could_not_connect);
            return;
        }
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
            out.println("X Y");
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
    }
}
