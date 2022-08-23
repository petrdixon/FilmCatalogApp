package com.example.filmcatalogapp.ui.main.view

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.filmcatalogapp.R
import com.google.android.gms.maps.model.LatLng
import java.io.IOException


class FavoritesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.button)
        val townText: EditText = view.findViewById(R.id.town_text)

        button.setOnClickListener {
            initSearchByAddress(townText.text.toString())
            townText.setText("")
        }
    }

    private fun initSearchByAddress(town: String) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocationName(town, 1)
                if (addresses.size > 0) {
                    goToAddress(addresses, requireView(), town)

                    println("********* $addresses")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

    }

    private fun goToAddress(
        addresses: MutableList<Address>,
        view: View,
        searchText: String
    ) {
        val location = LatLng(
            addresses[0].latitude,
            addresses[0].longitude
        )
    }

}