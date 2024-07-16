package com.educacionit.airbit.home.model.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.educacionit.airbit.base.common.NotificationFactory
import com.educacionit.airbit.entities.Room
import com.google.android.gms.maps.GoogleMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckShowRoomsService : Service(), CheckRoomsContract {
    private val binder = CheckShowRoomsBinder()
    private lateinit var getCurrentRoomsListener: GetCurrentRoomsListener
    private var checkRoomsJob: Job? = null

    override fun setCurrentRoomsListener(getCurrentRoomsListener: GetCurrentRoomsListener) {
        this.getCurrentRoomsListener = getCurrentRoomsListener
    }

    override fun checkRoomsShownToUser(googleMap: GoogleMap) {
        println("AIRBIT: Executing checkRoomsShownToUser")
        checkRoomsJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(500)
                val currentBounds = googleMap.projection.visibleRegion.latLngBounds
                withContext(Dispatchers.Default) {
                    var roomsVisible = false
                    getCurrentRoomsListener.getCurrentRooms().forEach {
                        if (currentBounds.contains(it.location)) {
                            roomsVisible = true
                        }
                    }
                    // Todo: Add a finish condition to avoid repeated notifications
                    if (!roomsVisible) {
                        createAndShowNotification()
                    }
                }
            }

        }
    }

    private fun createAndShowNotification() {
        NotificationFactory.showNotification(
            baseContext,
            "Te fuiste muy lejos",
            "Desea buscar alojamientos por esta zona?"
        )
    }

    override fun cancelCheckRooms() {
        checkRoomsJob?.cancel()
    }

    inner class CheckShowRoomsBinder : Binder() {
        fun getService(): CheckShowRoomsService = this@CheckShowRoomsService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}

interface GetCurrentRoomsListener {
    fun getCurrentRooms(): List<Room>
}

interface CheckRoomsContract {
    fun setCurrentRoomsListener(getCurrentRoomsListener: GetCurrentRoomsListener)
    fun checkRoomsShownToUser(googleMap: GoogleMap)
    fun cancelCheckRooms()
}