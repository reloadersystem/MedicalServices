package com.salud.medicalservices.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.salud.medicalservices.R;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class ToastCustom {

    public static void showToast(int tipToast, Context context, LayoutInflater inflater, String mensaje ,@Nullable int backgroundColor){

        switch (tipToast){
            case 1:
                toastBasicColor(context, inflater,mensaje, backgroundColor);
                break;
        }
    }

    private static void toastBasicColor(Context context, LayoutInflater inflater, String mensaje, int backgroundColor) {
        /*View view  = inflater.inflate(R.layout.toast_custom, null);
        ViewGroup vg = (ViewGroup) view.findViewById(R.id.custom_toast_layout_id);*/

        View layout = inflater.inflate(R.layout.toast_custom, null);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setTextColor(Color.WHITE);
        text.setText(mensaje);
        CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
        lyt_card.setCardBackgroundColor(context.getResources().getColor(backgroundColor));

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
