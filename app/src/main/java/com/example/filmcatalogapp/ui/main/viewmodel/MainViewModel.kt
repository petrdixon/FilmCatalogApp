package com.example.filmcatalogapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> =
                        MutableLiveData()) : ViewModel() {
    fun getData(): LiveData<Any> {
        getDataFromLocalSource() // запускаем метод, получающий данные
        return liveDataToObserve
    }

    private fun getDataFromLocalSource() { // метод, получающий данные
        Thread {
            sleep(3000)
            liveDataToObserve.postValue(Any())
        }.start()
    }

}
