package com.salud.medicalservices.HelperSharePreference.storage;


import com.salud.medicalservices.HelperSharePreference.entity.Product;

public interface SharedPreferencesHelper {

    void saveProduct(Product product);

    Product product();

    void clear();
}
