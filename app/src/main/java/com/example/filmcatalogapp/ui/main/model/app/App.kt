package com.example.filmcatalogapp.ui.main.model.app

import android.app.Application
import androidx.room.Room
import com.example.filmcatalogapp.ui.main.model.room.FilmDataBase
import com.example.filmcatalogapp.ui.main.model.room.InterfaceDao

// Нужно прописать в манифесте
// В классе App сформируем базу данных и метод получения DAO.
// Класс App нужен для получения контекста DB из любой части приложения
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
    companion object {
        private var appInstance: App? = null
        private var db: FilmDataBase? = null
        private const val DB_NAME = "History.db"
        fun getInterfaceDao(): InterfaceDao {
            if (db == null) {
                synchronized(FilmDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw
                        IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            FilmDataBase::class.java,
                            DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.interfaceDao()
        }
    }
}
