package com.example.filmcatalogapp.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.ui.main.model.*
import com.example.filmcatalogapp.ui.main.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var recyclerview: RecyclerView
    private var dataTopList: ArrayList<ItemsViewModel> = ArrayList<ItemsViewModel>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // подписка на изменения LiveData в MainViewModel. Данные приходят из Retrofit
        val viewModel2 = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<Any> { renderData(it) }
        viewModel2.getDataTop250Retrofit().observe(viewLifecycleOwner, observer)

        // добавление верхнего горизонтального списка
        recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview) // getting the recyclerview by its id
        recyclerview.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        ) // this creates a horizontal layout Manager

        // Домашнее задание - Напишите дополнительные extension-функции для Snackbar без action
        fun Snackbar.own(messange: String): Unit =
            Snackbar.make(view, messange, Snackbar.LENGTH_LONG).setAction("own action", null).show();

        val button: Button = view.findViewById(R.id.show_snack_bar)
        button.setOnClickListener {
            val startSnackBar: Snackbar = Snackbar.make(view, "gg", 10000)
            startSnackBar.own("final message")
        }
    }

    // метод запускается, когда приходят обновленные данные. Тогда обновляется горизонтальный список
    private fun renderData(data: Any) {

        // получаю значение rating из sharedPreferences. Если файла нет, возвращается 100
        var ratingValue = activity?.getPreferences(Context.MODE_PRIVATE)?.getString(RATING_VALUE_KEY, "100")

        // список для RecyclerView, соответствует ItemsViewModel
        dataTopList.clear()
        for (i in data as List<DataFilms>) {
            if (i.imDbRating.toFloat() >= ratingValue!!.toFloat()) // фильтр - показываем фильмы больше настроенного рейтинга
                dataTopList.add(ItemsViewModel(i.image, i.title, i.year, i.imDbRating))
        }

        // в HomeFragment получаю и обрабатываю клики RecyclerView
        val adapter = CustomAdapter(dataTopList, object : OnItemViewClickListener {
            // в CustomAdapter dataTopList становится mList

            override fun onItemClick(mlist: ItemsViewModel) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()

                    // в списке с элементами из 2 переменных, приготовленном для RecyclerView (dataTopList),
                    //  нахожу нажатый элемент, получаю его индекс. По индексу беру из списка
                    //  и передаю в другой фрагмент
                    bundle.putParcelable(FilmDetailsFragment.ARG_PARAM1, data[dataTopList.indexOf(mlist)])
                    manager.beginTransaction()
                        .replace(R.id.container, FilmDetailsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })
        recyclerview.adapter = adapter
    }

    interface OnItemViewClickListener { // интерфейс, чтобы передавать данные между адаптером списка и фрагментом
        fun onItemClick(mlist: ItemsViewModel)
    }
}


