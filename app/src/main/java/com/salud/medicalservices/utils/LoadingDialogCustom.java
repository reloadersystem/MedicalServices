package com.salud.medicalservices.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

import com.salud.medicalservices.R;

public class LoadingDialogCustom {

    private Activity mActivity;
    Dialog mDialog;
    private String mMessage;


    public LoadingDialogCustom(Activity mActivity, String mMessage) {
        this.mActivity = mActivity;
        this.mMessage = mMessage;
    }

    public void startLoadingDialog() {
        mDialog = new Dialog(mActivity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.dialog_loading_custom);

        TextView textView = mDialog.findViewById(R.id.textView);
        textView.setText(mMessage);
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }
}
