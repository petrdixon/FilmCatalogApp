package com.example.filmcatalogapp.ui.main.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.filmcatalogapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

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
                Toast.makeText(this@MainActivity, "ClickMenuFirstItem", Toast.LENGTH_SHORT)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

// Подключить фрагмент с деталями. Про добавление фрагмента с деталями в уроке с 2:13
// надо посмотреть как заменять фрагмент правильно

// добавить и выводить тестовые данные

// при нажатии на иконку поиска ничего не происходит.

// несколько горизонтальных списков - нужно завернсть в скроллВью

