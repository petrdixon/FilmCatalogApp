package com.example.filmcatalogapp.ui.main.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

// Создание объекта базы данных
@Database(entities = arrayOf(FilmEntity::class), version = 1, exportSchema = false) // при измении БД, нужно менять версию
abstract class FilmDataBase : RoomDatabase() {
    abstract fun interfaceDao(): InterfaceDao
}
