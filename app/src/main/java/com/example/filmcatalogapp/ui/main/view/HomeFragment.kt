package com.example.filmcatalogapp.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.ui.main.model.ForConvertJsonToArray
import com.example.filmcatalogapp.ui.main.model.ItemsViewModel
import com.example.filmcatalogapp.ui.main.model.Repository
import com.example.filmcatalogapp.ui.main.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var recyclerview: RecyclerView
    private var dataTopList: ArrayList<ItemsViewModel> = ArrayList<ItemsViewModel>()
    private lateinit var viewModel: MainViewModel

    private lateinit var dataFromRepository: List<ForConvertJsonToArray>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // получаю ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // подписываюсь на изменения LiveData
        val observer = Observer<Any> { renderData(it) } // renderData(it) - метод, который выполняется при изменении данных
        viewModel.getData("top250").observe(viewLifecycleOwner, observer)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // добавление верхнего горизонтального списка
        recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview) // getting the recyclerview by its id
        recyclerview.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        ) // this creates a horizontal layout Manager


        // Задание - Напишите дополнительные extension-функции для Snackbar без action
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
        Toast.makeText(context, "data", Toast.LENGTH_LONG).show()

        // сюда приходит List<ForConvertJsonToArray> такого вида:
        // [ForConvertJsonToArray(title=The Shawshank Redemption, year=1994, imDbRating=9.2), ForConvertJsonToArray(title=Th
        dataFromRepository = data as List<ForConvertJsonToArray>

        // список для RecyclerView, соответствует ItemsViewModel
        dataTopList.clear()
        for (i in data) {
            dataTopList.add(ItemsViewModel( R.drawable.poster_emma, i.title, i.year, i.imDbRating))
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
                    //  Repository (dataF*** repositoryromRepository) и передаю в другой фрагмент
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


