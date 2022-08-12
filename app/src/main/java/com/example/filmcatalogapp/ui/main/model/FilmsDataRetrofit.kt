package com.example.filmcatalogapp.ui.main.model

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmsDataRetrofit {

    private val filmsApi = Retrofit.Builder()
        .baseUrl("https://imdb-api.com/en/API/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create())
        )
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        ).build()
    )
        .build().create(FilmsDataRetrofitInterface::class.java) // указываем интерфейс

    fun getTop250List(callback: Callback<ModelTop250>) {
        filmsApi.getTop250().enqueue(callback)
    }

    fun getFilmDetailsList(filmID: String, callback: Callback<ModelFilmDetails>) {
        filmsApi.getFilmDetails(filmID).enqueue(callback)
    }

}

