package com.educacionit.airbit.home.model

import android.content.Context
import com.educacionit.airbit.entities.Location
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract

class HomeRepository(context: Context) : HomeContract.HomeModel {
    override fun getCurrentLocation(): Location {
        // TODO: Implement this later
        return Location(latitude = 1f, longitude = 1f)
    }

    override fun getRoomsForPlace(): List<Room> {
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