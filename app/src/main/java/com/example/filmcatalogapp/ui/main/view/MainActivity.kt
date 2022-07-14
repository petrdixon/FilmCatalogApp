package com.example.filmcatalogapp.ui.main.view

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filmcatalogapp.R
import com.example.filmcatalogapp.ui.main.model.GetInternetStatus
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    //Создаём свой BroadcastReceiver (получатель широковещательного сообщения). Для получения статуса интернета
    val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //Достаём данные из интента
            intent.getBooleanExtra("foo", false)?.let {
                if (!it) Toast.makeText(baseContext, "No internet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // запускаю сервис запроса статуса интернета
        baseContext?.let {
            it.startService(Intent(it, GetInternetStatus::class.java).apply {
                putExtra("foo", "any")
            })
        }

        // подписываюсь на события Broadcast
        baseContext?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    testReceiver,
                    IntentFilter("foo")
                )
        }


        // при запуске приложения устанавливаем HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }

        // ActionBar
        val actionBar = supportActionBar // установка заголовка приложения в ActionBar
//        actionBar!!.setBackgroundDrawable(resources.getDrawable(R.color.design_default_color_background))
        actionBar!!.title = "Cinema Finder"

        // нижние кнопки
        val botNavView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        botNavView.setOnItemSelectedListener { item ->
            var fragment: Fragment
            when (item.itemId) {
                R.id.home -> {
                    fragment = HomeFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.favorites -> {
                    fragment = FavoritesFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.ratings -> {
                    fragment = RatingFragment()
                    loadFragment(fragment)
                    true
                }
                else -> false
            }
        }
    }

    // метод для отображения нужного фрагмента при тапе по кнопкам нижнего меню
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    // создание меню
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.search_and_3dots_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName)) // the SearchView starts an activity with the ACTION_SEARCH intent when a user submits a query.
        }
        return true
    }

    // обработка нажатий меню "три точки"
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.firstItem -> {
                Toast.makeText(this@MainActivity, "ClickMenuFirstItem", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        // Отписываюсь от сообщений Broadcast
        baseContext?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(testReceiver)
        }
        super.onDestroy()
    }
}


