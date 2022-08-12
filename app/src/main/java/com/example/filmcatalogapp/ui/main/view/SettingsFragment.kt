package com.example.filmcatalogapp.ui.main.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.databinding.FragmentFilmDetailsBinding
import com.example.filmcatalogapp.ui.main.viewmodel.MainViewModel

const val RATING_VALUE_KEY = "RATING_VALUE_KEY"

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ratingEditText: EditText = view.findViewById(R.id.rating_edit_text)
        val saveButton: Button = view.findViewById(R.id.save_button)

        // получаю значение rating из sharedPreferences. Если файла нет, возвращается 100
        var ratingValue = activity?.getPreferences(Context.MODE_PRIVATE)?.getString(RATING_VALUE_KEY, "100")
        ratingEditText.setText(ratingValue)

        saveButton.setOnClickListener {
            activity?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putString(RATING_VALUE_KEY, ratingEditText.text.toString())
                ?.apply()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, HomeFragment())?.commit();
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}