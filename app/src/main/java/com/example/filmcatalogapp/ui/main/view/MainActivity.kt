package com.example.filmcatalogapp.ui.main.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
    // для получения контактов с телефона
    private val permissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            // что делаем когда permission получен
            getContact()
        } else {
            // что делаем если не получен
            Toast.makeText(baseContext, "Нет разрешения на получение котактов", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT).show()
                loadFragment(SettingsFragment()) // при нажатии Settings открываю фрагмент с настройками
                true
            }
            // получение и вывод списка контактов
            R.id.getContactsItem -> {
                permissionResult.launch(Manifest.permission.READ_CONTACTS)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    // получение контактов с телефона
    @SuppressLint("Range")
    private fun getContact() {
        // получили курсор
        contentResolver
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME
        )
        val contacts = mutableListOf<String>()
        // установили курсор на позицию 1, пробегаем циклом по контактам в телефоне, имя из контакта добавляем в список contacts
        cursor?.let {
            for (i in 0..cursor.count) {
                if (cursor.moveToPosition(i)) {
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    contacts.add(name)
                }
            }
        }

        // передача списка контактов в RatingFragment и показ этого фрагмента
        val bundle = Bundle()
        bundle.putStringArrayList(RatingFragment.ARG_PARAM1, contacts as ArrayList)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RatingFragment.newInstance(bundle))
            .addToBackStack("")
            .commitAllowingStateLoss()

    }

    override fun onDestroy() {
        // Отписываюсь от сообщений Broadcast
        baseContext?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(testReceiver)
        }
        super.onDestroy()
    }


}


