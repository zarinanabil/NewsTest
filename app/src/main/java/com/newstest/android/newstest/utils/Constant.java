package com.newstest.android.newstest.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.newstest.android.newstest.R;


public class Constant {

    public static ProgressDialog getProgressDialog(Context context, int msg) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.ProgressDialogStyle);
        progressDialog.setMessage(context.getResources().getString(msg));
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static void showToast(Context context, int msg){

        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
