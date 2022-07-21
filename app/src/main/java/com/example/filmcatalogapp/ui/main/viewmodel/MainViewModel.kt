package com.example.filmcatalogapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmcatalogapp.ui.main.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Any as Any

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()
) : ViewModel() {

//    Получение через OkHTTP. Не используется, переделал на RetroFit
    fun getData(filmID: String): LiveData<Any> {
        liveDataToObserve.value = FilmsFromJson(filmID).getAllFilms()
        return liveDataToObserve
    }

    // Получение списка фильмов Топ250
    fun getDataTop250Retrofit(): LiveData<Any> {
        FilmsDataRetrofit().getTop250List(
            object : Callback<ModelTop250> {
                override fun onFailure(call: Call<ModelTop250>, t: Throwable) {
                    println("*** in MainViewModel onFailure $t")
                }
                override fun onResponse(call: Call<ModelTop250>, response: Response<ModelTop250>) {
                    val listDataFilms = response.body()?.items
                    liveDataToObserve.value = listDataFilms!!
                }
            }
        )
        return liveDataToObserve
    }

    // Получение информации об одном фильме
    fun getDataFilmDetailsRetrofit(filmID: String): LiveData<Any>{
        FilmsDataRetrofit().getFilmDetailsList(
            filmID,
            object : Callback<ModelFilmDetails> {
                override fun onFailure(call: Call<ModelFilmDetails>, t: Throwable) {
                    println("*** in Callback onFailure $t")
                }

                override fun onResponse(call: Call<ModelFilmDetails>, response: Response<ModelFilmDetails>) {
                    val listDataFilmDetails = response.body()
                    liveDataToObserve.value = listDataFilmDetails!!
                }
            }
        )
        return liveDataToObserve
    }

    private fun getDataFromLocalSource() { // метод, получающий данные из локального хранилища
        val repository = Repository("", 0, "", "")
        val listFilms: ArrayList<Repository> = repository.getAllFilms()
        liveDataToObserve.postValue(listFilms)
    }
}
