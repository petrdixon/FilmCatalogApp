package com.example.filmcatalogapp.ui.main.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

// создание сущности
@Entity
data class FilmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val filmId: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val imDbRating: Double,
    val type: String,
    val runtimeStr: String,
    val releaseDate: String,
    val plot: String,
    val image: String,

    val noteAboutFilm: String,
    val dateTimeRequest: String
)
