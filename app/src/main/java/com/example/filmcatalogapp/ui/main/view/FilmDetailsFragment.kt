package com.example.filmcatalogapp.ui.main.view

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.databinding.FragmentFilmDetailsBinding
import com.example.filmcatalogapp.ui.main.model.ForConvertJsonToArray
import com.example.filmcatalogapp.ui.main.model.ItemsViewModel
import com.example.filmcatalogapp.ui.main.model.Repository
import com.example.filmcatalogapp.ui.main.viewmodel.MainViewModel


class FilmDetailsFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var dataOneFilmDetails: List<ForConvertJsonToArray>

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
        val repository = arguments?.getParcelable<Parcelable>(ARG_PARAM1) as ForConvertJsonToArray

        // делаю запрос подробной информации о выбранном фильме
        // получаю ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // подписываюсь на изменения LiveData
        val observer = Observer<Any> { renderData(it) } // renderData(it) - метод, который выполняется при изменении данных
        viewModel.getData(repository?.id).observe(viewLifecycleOwner, observer) // передаю ID фильма

        with(binding) {
            title.text = repository?.title
            fullTitle.text = repository?.fullTitle
            poster.setImageResource(R.drawable.poster_emma) // заглушка. загрузка картинки из API будет сделана на след уроках
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(data: Any) {

        // сюда приходит List<ForConvertJsonToArray> такого вида:
        // [ForConvertJsonToArray(title=The Shawshank Redemption, year=1994, imDbRating=9.2), ForConvertJsonToArray(title=Th
        dataOneFilmDetails = data as List<ForConvertJsonToArray>
        var readyData = dataOneFilmDetails[0]

        println("**** readyData ${readyData.plot}")

        with(binding) {
            type.text = readyData.type
            runtimeStr.text = readyData.runtimeStr
            releaseDate.text = readyData.releaseDate
            plot.text = readyData.plot
        }

    }

}


