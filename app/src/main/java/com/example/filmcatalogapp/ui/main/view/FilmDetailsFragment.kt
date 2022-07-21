package com.example.filmcatalogapp.ui.main.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.databinding.FragmentFilmDetailsBinding
import com.example.filmcatalogapp.ui.main.model.DataFilms
import com.example.filmcatalogapp.ui.main.model.ForConvertJsonToArray
import com.example.filmcatalogapp.ui.main.model.ModelFilmDetails
import com.example.filmcatalogapp.ui.main.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


class FilmDetailsFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var dataOneFilmDetails: List<ModelFilmDetails>

    companion object {
        const val ARG_PARAM1 = "filmsBundle"
        fun newInstance(bundle: Bundle): FilmDetailsFragment {
            val fragment = FilmDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_film_details, container, false)
        _binding = FragmentFilmDetailsBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  получаю краткую инфу об 1 фильме из top250, переданную из HomeFragment через newInstance FilmDetailsFragment
        val repository = arguments?.getParcelable<Parcelable>(ARG_PARAM1) as DataFilms
        with(binding) {
            title.text = repository?.title
            fullTitle.text = repository?.fullTitle
            Picasso.get().load(repository.image).into(poster) // загрузка картинки с помощью Picasso
     }

        // делаю запрос подробной информации о выбранном фильме
        // получаю ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // подписываюсь на изменения LiveData
        val observer = Observer<Any> { renderData(it) } // renderData(it) - метод, который выполняется при изменении данных
        viewModel.getDataFilmDetailsRetrofit(repository?.id).observeForever(observer) // передаю ID фильма
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(data: Any) {
        // сюда приходит ModelFilmDetails такого вида: ModelFilmDetails(id=tt0068646, title=The Godfather,
        data as ModelFilmDetails
        with(binding) {
            type.text = data.type
            runtimeStr.text = data.runtimeStr
            releaseDate.text = data.releaseDate
            plot.text = data.plot
        }
    }
}




