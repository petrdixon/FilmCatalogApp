package com.example.filmcatalogapp.ui.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.ui.main.model.ItemsViewModel
import com.squareup.picasso.Picasso

// адаптер для RecyclerView

class CustomAdapter(private val mList: List<ItemsViewModel>, private var onItemViewClickListener:
HomeFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        fun bind(mlist: ItemsViewModel) { // без создания метода bind не удается установить clickListener
            // заполнение карточек картинками и текстом

            val imageView: ImageView = itemView.findViewById(R.id.imageview)
            Picasso.get().load(mlist.image).into(imageView) // загрузка картинки с помощью Picasso
            val titleTextView: TextView = itemView.findViewById(R.id.title)
            titleTextView.text = mlist.title
            val yearTextView: TextView = itemView.findViewById(R.id.year)
            yearTextView.text = mlist.year
            val ratingTextView: TextView = itemView.findViewById(R.id.rating)
            ratingTextView.text = mlist.rating


            itemView.findViewById<LinearLayout>(R.id.linear_view)
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemClick(mlist) // обращение к интерфейсу onItemViewClickListener в HomeFragment
            }
        }
    }
}
