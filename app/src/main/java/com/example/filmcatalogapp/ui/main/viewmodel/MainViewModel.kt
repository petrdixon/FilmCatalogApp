package com.example.filmcatalogapp.ui.main.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmcatalogapp.ui.main.model.FilmsFromJson
import com.example.filmcatalogapp.ui.main.model.GetInternetStatus
import com.example.filmcatalogapp.ui.main.model.Repository
import kotlin.Any as Any

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()
) : ViewModel() {

    fun getData(filmID: String): LiveData<Any> {
        liveDataToObserve.setValue(FilmsFromJson(filmID).getAllFilms())
        return liveDataToObserve
    }

    fun getInternetStatus(context: Context) {
        context?.let {
            it.startService(Intent(it, GetInternetStatus::class.java).apply {
                putExtra("foo", "any")
            })
        }

//        return liveDataToObserve
    }


    private fun getDataFromJson(filmID: String) { // метод, получающий данные по API
        val arrayWithFilms = FilmsFromJson(filmID).getAllFilms()
        liveDataToObserve.postValue(arrayWithFilms)
    }

    private fun getDataFromLocalSource() { // метод, получающий данные из локал
        val repository = Repository("", 0, "", "")
        val listFilms: ArrayList<Repository> = repository.getAllFilms()
        liveDataToObserve.postValue(listFilms)
    }
}


//    fun getData(filmID: String): LiveData<Any> {
////        getDataFromLocalSource() // запускаем метод, получающий локальные данные
//        getDataFromJson(filmID)
//        println("*****  in MVM getData ${liveDataToObserve.value}")
//        return liveDataToObserve
//    }