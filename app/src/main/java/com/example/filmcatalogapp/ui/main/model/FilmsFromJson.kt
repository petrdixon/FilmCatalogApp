package com.example.filmcatalogapp.ui.main.model

import android.os.Parcelable
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


@Parcelize
data class FilmsFromJson(
    val filmID: String
) : Parcelable {

    private val client = OkHttpClient()
    var arrayWithFilms = JSONArray()
    var model: List<ForConvertJsonToArray> = listOf(
        ForConvertJsonToArray
            ("", "", "", "", "", "", "", "", "", "")
    )
    var url = ""

    fun getAllFilms(): List<ForConvertJsonToArray> {

//        0 - getData вызывают из HomeFragment и нужно получить top250
//        другое - getData вызывают из FilmDetailsFragment и нужно получить инфу об одном фильме
        url = when (filmID) {
            "top250" -> "https://imdb-api.com/en/API/Top250Movies/k_frk4eh40"
            else -> "https://imdb-api.com/en/API/Title/k_frk4eh40/$filmID"
        }

        val t1 = Thread {
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val responseData = response.body?.string() // из вернувшегося JSON получил body. Переменная String

                try {
                    val json = JSONObject(responseData)
//                    arrayWithFilms = json.getJSONArray("items") // получил массив со списком параметров фильмов
//                    println("**** in FilmsFromJson " + arrayWithFilms)

                    // преобразование ответа от сервера (JSON) в модель данных (ForConvertJsonToArray)
//                val gson = GsonBuilder().create()
//                model = gson.fromJson(arrayWithFilms.toString(), Array<ForConvertJsonToArray>::class.java).toList()

                    if (filmID == "top250") {
                        arrayWithFilms = json.getJSONArray("items")
                        // преобразование ответа от сервера (JSON) в модель данных (ForConvertJsonToArray)
                        val gson = GsonBuilder().create()
                        model = gson.fromJson(arrayWithFilms.toString(), Array<ForConvertJsonToArray>::class.java).toList()
                        println("*** arrayWithFilms1 $arrayWithFilms")
                    } else {
                        arrayWithFilms.put(json)
                        val gson = GsonBuilder().create()
                        model = gson.fromJson(arrayWithFilms.toString(), Array<ForConvertJsonToArray>::class.java).toList()
                    }
//                        this@MainActivity.fetchComplete() // для закрытия канала получения данных?

                } catch (e: JSONException) {
                    e.printStackTrace()
                    println("***** есть ошибка " + e.printStackTrace())
                }
            }
        }
        t1.start()
        t1.join()
        return model
    }
}








