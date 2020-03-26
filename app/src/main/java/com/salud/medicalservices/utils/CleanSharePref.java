package com.salud.medicalservices.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CleanSharePref {

    public static void deleteTemp(Context context) {

        SharedPreferences settings2 = context.getSharedPreferences("RecyclerTemp", Context.MODE_PRIVATE);
        settings2.edit().clear().commit();

    }
}
