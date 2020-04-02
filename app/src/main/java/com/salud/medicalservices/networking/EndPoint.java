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


//
//    @POST("users/me")
//    Call<ResponseBody> getInfo(@Header("Authorization") String authHeader);

    @POST("users")
    Call<ResponseBody> postRegistrarUsuario(@Body RegisterUser user);



    //https://mivida.azurewebsites.net/api/users/auth

    @POST("users/auth")
    Call<Loguin> postAutenticacion(@Body AuthUser user);

    @GET("users/me")
    Call<Usuario> obtenerInformacionUsuario(@Header("Authorization") String authHeader);

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





    Call<ResponseBody> getCursosMail(
            @Query("courseStates") String courseStates,
            @Query("studentId") String studentId,
            @Query("teacherId") String teacherId,
            @Query("access_token") String access_token);

    //llamar asi en el activity

    /*

    private void call(String token) {


        EndPoint endPoint = HelperWs.getConfiguration().create(EndPoint.class);

        //Llamamos al endpoint
        Call<ResponseBody> response_categorys =
                endPoint.getInfo("Bearer"+" "+token);

        response_categorys.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody respuesta = response.body();
                String ok = "ok";
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(MainActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });


    }


     */
}
