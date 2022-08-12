package com.example.filmcatalogapp.ui.main.model.room

import androidx.room.*

@Dao
interface InterfaceDao {

    @Query("SELECT * FROM FilmEntity")
    fun all(): List<FilmEntity>

    @Query("SELECT * FROM FilmEntity WHERE filmId LIKE :filmId") // LIKE :filmId - filmId получаемый параметр
    fun getDataByWord(filmId: String): List<FilmEntity>

    // запрос на добавление в БД текста заметки о фильме
    @Query("UPDATE FilmEntity SET noteAboutFilm = :textNote WHERE filmId LIKE :filmId") // LIKE :filmId - filmId получаемый параметр
    fun updateNote (filmId: String, textNote: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FilmEntity)

    @Update
    fun update(entity: FilmEntity)

    @Delete
    fun delete(entity: FilmEntity)
}