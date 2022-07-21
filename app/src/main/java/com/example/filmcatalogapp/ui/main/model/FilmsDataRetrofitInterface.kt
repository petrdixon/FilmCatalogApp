package com.example.filmcatalogapp.ui.main.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmsDataRetrofitInterface {
    @GET("Top250Movies/k_frk4eh40")
    fun getTop250(
//        @Header("X-Yandex-API-Key") token: String, // значения для отправки хэдера в запросе
//        @Query("id") lat: String,
//        @Query("title") lon: String
    ): Call<ModelTop250>


    @GET("Title/k_frk4eh40/{filmID}") // подстановка в url своего пераметра
    fun getFilmDetails(
        @Path("filmID") filmID: String
//        @Header("X-Yandex-API-Key") token: String,
//        @Query("id") lat: String,
//        @Query("title") lon: String
    ): Call<ModelFilmDetails>

}
