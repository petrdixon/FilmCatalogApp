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
import com.example.filmcatalogapp.ui.main.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    private lateinit var recyclerview: RecyclerView
    private var dataTopList: ArrayList<ItemsViewModel> = ArrayList<ItemsViewModel>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        recyclerview.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false) // this creates a horizontal layout Manager

        for (i in 1..20) { // в recyclerview добавляются 20 view с картинкой и текстом
            dataTopList.add(ItemsViewModel(R.drawable.plug_picture, "Item " + i))
        }
        val adapter = CustomAdapter(dataTopList) // передает ArrayList в Adapter
        recyclerview.adapter = adapter // устанавливает адаптер в recyclerview
    }

    // метод запускается, когда приходят обновленные данные. Тогда обновляется горизонтальный список
    private fun renderData(data: Any) {
        Toast.makeText(context, "data", Toast.LENGTH_LONG).show()
        dataTopList.clear()
        for (i in 1..20) {
            dataTopList.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, "NewItem " + i))
        }
        val adapter = CustomAdapter(dataTopList)
        recyclerview.adapter = adapter

    }

}


