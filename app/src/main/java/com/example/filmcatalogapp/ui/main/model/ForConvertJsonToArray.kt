package com.example.filmcatalogapp.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForConvertJsonToArray(
    val id: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val imDbRating: String,
    val crew: String,

    val type: String,
    val runtimeStr: String,
    val releaseDate: String,
    val plot: String,

):Parcelable




