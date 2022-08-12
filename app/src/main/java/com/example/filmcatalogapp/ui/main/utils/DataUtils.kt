package com.example.filmcatalogapp.ui.main.utils

import androidx.room.PrimaryKey
import com.example.filmcatalogapp.ui.main.model.DataFilms
import com.example.filmcatalogapp.ui.main.model.ModelFilmDetails
import com.example.filmcatalogapp.ui.main.model.room.FilmEntity

// класс конвертации FilmEntity в ModelFilmDetails и обратно
class DataUtils {
    fun convertFilmEntityToModelFilmDetails(listFilmEntity: List<FilmEntity>):
            List<ModelFilmDetails> {
        return listFilmEntity.map {
            ModelFilmDetails(
                it.filmId,
                it.title,
                it.fullTitle,
                it.year,
                it.imDbRating.toString(),
                it.type,
                it.runtimeStr,
                it.releaseDate,
                it.plot,
                it.image,
                it.noteAboutFilm,
                it.dateTimeRequest
            )
        }
    }

    fun convertModelFilmDetailsToFilmEntity(modelFilmDetails: ModelFilmDetails): FilmEntity {
        return FilmEntity(
            0,
            modelFilmDetails.id,
            modelFilmDetails.title,
            modelFilmDetails.fullTitle,
            modelFilmDetails.year,
            modelFilmDetails.imDbRating.toDouble(),
            modelFilmDetails.type,
            modelFilmDetails.runtimeStr,
            modelFilmDetails.releaseDate,
            modelFilmDetails.plot,
            modelFilmDetails.image,
            modelFilmDetails.noteAboutFilm,
            modelFilmDetails.dateTimeRequest
        )
    }

//    ModelFilmDetails
//    val id: String,
//    val title: String,
//    val fullTitle: String,
//    val year: String,
//    val imDbRating: String,
//    val type: String,
//    val runtimeStr: String,
//    val releaseDate: String,
//    val plot: String,
//    val image: String
//
//    FilmEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Long,
//
//    val filmId: String,
//    val title: String,
//    val fullTitle: String,
//    val year: String,
//    val imDbRating: Double,
//    val type: String,
//    val runtimeStr: String,
//    val releaseDate: String,
//    val plot: String,
//    val image: String,
//    val noteAboutFilm: String


}