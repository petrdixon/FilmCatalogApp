package com.example.filmcatalogapp.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTop250(
    val items: List<DataFilms>
):Parcelable

@Parcelize
data class DataFilms(
    val id: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val imDbRating: String,
    val crew: String,
    val image: String
    ):Parcelable

