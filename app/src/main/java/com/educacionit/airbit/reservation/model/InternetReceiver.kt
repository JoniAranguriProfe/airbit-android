package com.educacionit.airbit.reservation.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.educacionit.airbit.base.common.NotificationFactory

class InternetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = conn.activeNetworkInfo
        networkInfo ?: run {
            NotificationFactory.showNotification(
                context,
                "No hay internet",
                "Desea salir de la aplicación?"
            )
        }
    }

}