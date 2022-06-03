package com.example.filmcatalogapp.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.ui.main.model.ItemsViewModel
import com.example.filmcatalogapp.ui.main.model.Repository
import com.example.filmcatalogapp.ui.main.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var recyclerview: RecyclerView
    private var dataTopList: ArrayList<ItemsViewModel> = ArrayList<ItemsViewModel>()
    private lateinit var viewModel: MainViewModel

    private var dataFromRepository: ArrayList<Repository> = ArrayList<Repository>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // подписываюсь на изменения LiveData
        val observer = Observer<Any> { renderData(it) } // renderData(it) - метод, который выполняется при изменении данных
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // добавление верхнего горизонтального списка
        recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview) // getting the recyclerview by its id
        recyclerview.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        ) // this creates a horizontal layout Manager
    }

    // метод запускается, когда приходят обновленные данные. Тогда обновляется горизонтальный список
    private fun renderData(data : Any) {
        Toast.makeText(context, "data", Toast.LENGTH_LONG).show()

        dataTopList.clear()
        dataFromRepository.clear()
        dataFromRepository = data as ArrayList<Repository>

        for (i in dataFromRepository) {
            dataTopList.add(ItemsViewModel(i.poster, i.nameFilm))
        }

        // в HomeFragment получаю и обрабатываю клики RecyclerView
        val adapter = CustomAdapter(dataTopList, object : OnItemViewClickListener{
            // в CustomAdapter dataTopList становится mList

            override fun onItemClick(mlist: ItemsViewModel) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()

                    // в списке с элементами из 2 переменных, приготовленном для RecyclerView (dataTopList),
                    //  нахожу нажатый элемент, получаю его индекс. По индексу беру из списка
                    //  Repository (dataFromRepository) и передаю в другой фрагмент
                    bundle.putParcelable(FilmDetailsFragment.ARG_PARAM1, dataFromRepository[dataTopList.indexOf(mlist)])
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


