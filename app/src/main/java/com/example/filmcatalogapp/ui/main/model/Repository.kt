package com.example.filmcatalogapp.ui.main.model

import android.os.Parcelable
import com.example.filmcatalogapp.R
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Repository(
    val nameFilm: String = "default nameFilm",
    val poster: Int = R.drawable.ic_baseline_folder_24,
    val shortSummary: String = "default shortSummary",
    val fullSummary: String = "default fullSummary",
): Parcelable {

    fun getAllFilms(): ArrayList<Repository> {
        return arrayListOf(
            Repository(
                "Эмма", R.drawable.poster_emma,
                "shortSummary 1", "Англия, XIX век. 21-летняя провинциалка Эмма Вудхаус красива, богата, остроумна и считает, что прекрасно разбирается в людях. Девушка решила, что никогда не выйдет замуж и не оставит отца одного. Когда её подруга в связи с собственным замужеством переезжает в дом супруга, Эмма находит себе новую компаньонку — сироту Гарриет Смит — и теперь, используя все свои хитрости, пытается устроить девушке личную жизнь."
                        ),
            Repository(
                "Новый фильм", R.drawable.entry,
                "shortSummary 2", "Коржик, Карамелька и Компот вместе с родителями они отправляются отдыхать на морской курорт, где их ждут яркие события, полные весёлой суматохи и встреч с новыми друзьями."
            ),
            Repository(
                "Las Vegas Parano", R.drawable.las_vegas,
                "shortSummary 3", "Два приятеля едут в Лас-Вегас. Одного из них зовут Рауль Дьюк, он спортивный обозреватель, и в Вегас едет, чтобы осветить знаменитую «Минт 400». Второго зовут Доктор Гонзо. Или что-то вроде того. А ещё Доктор Гонзо адвокат, но какая разница? Да и вокруг творится нечто невообразимое."
               ),

        )
    }
}