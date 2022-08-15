package com.example.filmcatalogapp.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.filmcatalogapp.R
import kotlinx.android.synthetic.main.fragment_settings.view.*


class RatingFragment : Fragment() {

    companion object {
        const val ARG_PARAM1 = "param1"

        fun newInstance(bundle: Bundle): RatingFragment {
            val fragment = RatingFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
    private var bundle: Bundle? = null
    var contacts: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contacts = it.getStringArrayList(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listContacts: ListView = view.findViewById(R.id.list_contacts)

        // отображаем список контактов в ListView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, contacts!!.toList())
        listContacts.adapter = adapter


    }

}