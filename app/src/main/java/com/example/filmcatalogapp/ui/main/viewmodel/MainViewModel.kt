package com.example.filmcatalogapp.ui.main.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmcatalogapp.ui.main.model.Repository
import kotlin.Any as Any

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> =
        MutableLiveData()
) : ViewModel() {
    fun getData(): LiveData<Any> {
        getDataFromLocalSource() // запускаем метод, получающий данные
        return liveDataToObserve
    }

    private fun getDataFromLocalSource() { // метод, получающий данные
        var repository = Repository("",0,"", "")
        var listFilms: ArrayList<Repository> = repository.getAllFilms()
        liveDataToObserve.postValue(listFilms)

    }

}
