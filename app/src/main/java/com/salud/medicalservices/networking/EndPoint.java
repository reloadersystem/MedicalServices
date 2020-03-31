package com.salud.medicalservices.networking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EndPoint {



    @GET("users/me")
    Call<ResponseBody> getInfo(@Header("Authorization") String authHeader);

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
