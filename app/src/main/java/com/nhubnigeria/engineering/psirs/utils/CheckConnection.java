package com.nhubnigeria.engineering.psirs.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.nhubnigeria.engineering.psirs.activity.Login;

public class CheckConnection {
    static Context context;
    ConnectivityManager connectMgr;
    private static CheckConnection instance = new CheckConnection();
    boolean connected = false;
    Login login;

    public static CheckConnection getInstance(Context cxt) {
        context = cxt.getApplicationContext();
        return instance;
    }

    public boolean checkInternetConnection() {

        try {
            // get Connectivity Manager object to check connection
            connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //Check for network connections
            if (connectMgr.getNetworkInfo(0).getState() ==
                    NetworkInfo.State.CONNECTED ||
                    connectMgr.getNetworkInfo(0).getState() ==
                            NetworkInfo.State.CONNECTING ||
                    connectMgr.getNetworkInfo(1).getState() ==
                            NetworkInfo.State.CONNECTING ||
                    connectMgr.getNetworkInfo(0).getState() ==
                            NetworkInfo.State.CONNECTED) {

                connected = false;
                login.progressBar.setVisibility(View.VISIBLE);

                return connected;

            } else if (
                    connectMgr.getNetworkInfo(0).getState() ==
                            NetworkInfo.State.DISCONNECTED ||
                            connectMgr.getNetworkInfo(1).getState() ==
                                    NetworkInfo.State.DISCONNECTED) {


                return connected;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connected;
    }
}
