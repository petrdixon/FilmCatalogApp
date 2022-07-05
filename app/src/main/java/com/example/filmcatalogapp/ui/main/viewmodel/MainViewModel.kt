package com.example.filmcatalogapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmcatalogapp.ui.main.model.FilmsFromJson
import com.example.filmcatalogapp.ui.main.model.Repository
import kotlin.Any as Any

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()
) : ViewModel() {
    fun getData(filmID: String): LiveData<Any> {
//        getDataFromLocalSource() // запускаем метод, получающий локальные данные

        getDataFromJson(filmID)

        return liveDataToObserve
    }

    private fun getDataFromLocalSource() { // метод, получающий данные из локал
        var repository = Repository("", 0, "", "")
        var listFilms: ArrayList<Repository> = repository.getAllFilms()
        liveDataToObserve.postValue(listFilms)
    }

    private fun getDataFromJson(filmID: String) { // метод, получающий данные по API
        var arrayWithFilms = FilmsFromJson(filmID).getAllFilms()
        liveDataToObserve.postValue(arrayWithFilms)

    }


}
