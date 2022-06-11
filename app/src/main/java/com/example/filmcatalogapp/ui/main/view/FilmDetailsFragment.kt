package com.example.filmcatalogapp.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.databinding.FragmentFilmDetailsBinding
import com.example.filmcatalogapp.ui.main.model.Repository


class FilmDetailsFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = _binding!!

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
        val repository = arguments?.getParcelable<Repository>(ARG_PARAM1)

        with(binding) {
            filmName.text = repository?.nameFilm
            poster.setImageResource(repository?.poster!!)
            shortSummary.text = repository.shortSummary
            fullSummary.text = repository.fullSummary
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


