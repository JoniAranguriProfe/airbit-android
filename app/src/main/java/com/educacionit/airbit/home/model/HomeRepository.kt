package com.educacionit.airbit.home.model

import android.annotation.SuppressLint
import android.content.Context
import com.educacionit.airbit.entities.Location
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay

@SuppressLint("MissingPermission")
class HomeRepository(context: Context) : HomeContract.HomeModel {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var currentLocation: Location? = null

    init {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            currentLocation = with(it.result) {
                Location(latitude.toFloat(), longitude.toFloat())
            }
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location {
        return currentLocation ?: run {
            delay(50)
            getCurrentLocation()
        }
    }

    override fun getRoomsForPlace(location: Location): List<Room> {
        // TODO: Implement this later
        return emptyList()
    }

    override fun saveRoomAsFavourite(room: Room) {
        // TODO: Implement this later
    }

    override fun saveRoomsForOfflineMode(rooms: List<Room>) {
        // TODO: Implement this later
    }
}