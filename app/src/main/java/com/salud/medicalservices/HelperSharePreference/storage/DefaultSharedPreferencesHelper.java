package com.salud.medicalservices.HelperSharePreference.storage;

import android.content.SharedPreferences;

import com.salud.medicalservices.HelperSharePreference.entity.Product;
import com.salud.medicalservices.HelperSharePreference.utils.GsonHelper;


public class DefaultSharedPreferencesHelper implements SharedPreferencesHelper {

    private final SharedPreferences sharedPreferences;
    private final GsonHelper gsonHelper;
    private final String shareCount;

    public DefaultSharedPreferencesHelper(GsonHelper gsonHelper, SharedPreferences sharedPreferences, String shareCount) {
        this.gsonHelper = gsonHelper;
        this.sharedPreferences = sharedPreferences;
        this.shareCount = shareCount;
    }

    @Override
    public void saveProduct(Product product) {
        SharedPreferences.Editor editor = editor();
        editor.putString(shareCount, gsonHelper.objectToJSON(product).toString());
        editor.apply();
    }

    @Override
    public Product product() {
        String productStr = sharedPreferences.getString(shareCount, "");
        Product product = gsonHelper.jsonToObject(productStr, Product.class);
        return product;
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = editor();
        editor.clear();
        editor.apply();
    }

    private SharedPreferences.Editor editor() {
        return sharedPreferences.edit();
    }
}
