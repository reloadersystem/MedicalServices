package com.salud.medicalservices.utils;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class GeneralFragmentManager {

    public static void setFragmentWithReplace(Activity activity, int contenedor_id, Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(contenedor_id, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}
