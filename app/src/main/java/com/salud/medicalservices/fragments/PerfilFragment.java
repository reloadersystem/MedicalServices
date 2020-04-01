package com.salud.medicalservices.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.salud.medicalservices.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    String id,firstName,lastName,email,address,countryId,departmentId,
            provinceId,districtId,gender,phone,identityDocument,birthDate,perfil;

    TextView tv_nombres_completos,tv_perfil,tv_numero_documento;

    EditText edt_correo,edt_celular,edt_direccion,edt_fecha_nacimiento_perfil;

    View rootview;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_perfil, container, false);
        getActivity().setTitle("Perfil");
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        obtenerDatosPreferencesUsuario();
        obtenerDatosPreferencesPerful();
        init();
    }

    private void init() {

        tv_nombres_completos = getActivity().findViewById(R.id.tv_nombres_completos);
        tv_perfil = getActivity().findViewById(R.id.tv_perfil);
        tv_numero_documento = getActivity().findViewById(R.id.tv_numero_documento);

        edt_correo = getActivity().findViewById(R.id.edt_correo);
        edt_celular = getActivity().findViewById(R.id.edt_celular);
        edt_direccion = getActivity().findViewById(R.id.edt_direccion);
        edt_fecha_nacimiento_perfil = getActivity().findViewById(R.id.edt_fecha_nacimiento_perfil);

        tv_nombres_completos.setText(firstName + " " + lastName);
        tv_perfil.setText(perfil);
        tv_numero_documento.setText(identityDocument);

        edt_correo.setText(email);
        edt_celular.setText(phone);
        edt_direccion.setText(address);
        edt_fecha_nacimiento_perfil.setText(birthDate);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Mi Perfil");
    }

    private void obtenerDatosPreferencesUsuario() {
        SharedPreferences pref = getContext().getSharedPreferences("usuario", 0);
        id = pref.getString("id", "");
        firstName = pref.getString("firstName", "");
        lastName = pref.getString("lastName", "");
        email = pref.getString("email", "");
        address = pref.getString("address", "");

        countryId = pref.getString("countryId", "");
        departmentId = pref.getString("departmentId", "");
        provinceId = pref.getString("provinceId", "");
        districtId = pref.getString("districtId", "");
        gender = pref.getString("gender", "");

        phone = pref.getString("phone", "");
        identityDocument = pref.getString("identityDocument", "");
        birthDate = pref.getString("birthDate", "");

    }

    private void obtenerDatosPreferencesPerful() {
        SharedPreferences pref = getContext().getSharedPreferences("perfil", 0);
        perfil = pref.getString("perfil", "");

    }
}
