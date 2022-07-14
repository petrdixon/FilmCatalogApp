package com.example.filmcatalogapp.ui.main.model

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filmcatalogapp.ui.main.view.MainActivity

class GetInternetStatus(name: String = "GetInternetStatus") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        while(true) {
            val isInternet = getInternetStatus()
            sendBack(isInternet) // запускаю Broadcast
            Thread.sleep(1000)
        }
    }

    fun getInternetStatus(): Boolean {
        // Задание урок 6. Подпишитесь на событие изменения связи (CONNECTIVITY_ACTION) и уведомляйте об этом пользователя.
        //  CONNECTIVITY_ACTION deprecated, я использовал CONNECTIVITY_SERVICE и ConnectivityManager

        val connectivityManager = getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        Log.i("Internet", "No internet")
        return false
    }

    // отправка Broadcast
    private fun sendBack(result: Boolean) {
        val broadcastIntent = Intent("foo")
        broadcastIntent.putExtra("foo", result)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }


}