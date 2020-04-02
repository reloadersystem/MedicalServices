package com.salud.medicalservices.networking;

import com.salud.medicalservices.entidades.AuthUser;
import com.salud.medicalservices.entidades.Loguin;
import com.salud.medicalservices.entidades.RegisterUser;
import com.salud.medicalservices.entidades.Usuario;
import com.salud.medicalservices.entidades.response.ResponseDepartamento;
import com.salud.medicalservices.entidades.response.ResponseDistrito;
import com.salud.medicalservices.entidades.response.ResponsePaises;
import com.salud.medicalservices.entidades.response.ResponseProvincia;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPoint {

    @POST("users")
    Call<ResponseBody> postRegistrarUsuario(@Body RegisterUser user);

    @POST("users/auth")
    Call<Loguin> postAutenticacion(@Body AuthUser user);

    @GET("users/me")
    Call<Usuario> obtenerInformacionUsuario(@Header("Authorization") String authHeader);

    @GET("categories")
    Call<ResponseBody> getCategorias(@Header("Authorization") String authHeader,
            @Query("Type") String type);
    @GET("countries")
    Call<ResponsePaises> obtenerPaises();

    @GET("countries/{idPais}/departments")
    Call<ResponseDepartamento> obtenerDepartamentos(@Path("idPais") String idPais);

    @GET("countries/{idPais}/departments/{idDepartamento}/provinces")
    Call<ResponseProvincia> obtenerProvincias(@Path("idPais") String idPais,
                                                @Path("idDepartamento") String idDepartamento);

    @GET("countries/{idPais}/departments/{idDepartamento}/provinces/{idProvincia}/districts")
    Call<ResponseDistrito> obtenerDistritos(@Path("idPais") String idPais,
                                           @Path("idDepartamento") String idDepartamento,
                                           @Path("idProvincia") String idProvincia);
}
