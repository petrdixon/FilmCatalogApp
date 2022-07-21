package com.example.filmcatalogapp.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelFilmDetails(
    val id: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val imDbRating: String,
    val type: String,
    val runtimeStr: String,
    val releaseDate: String,
    val plot: String,
    val image: String
    ):Parcelable

