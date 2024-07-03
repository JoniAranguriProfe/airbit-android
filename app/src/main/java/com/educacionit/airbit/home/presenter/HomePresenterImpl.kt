package com.educacionit.airbit.home.presenter

import com.educacionit.airbit.entities.Location
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract
import com.google.android.gms.maps.model.LatLng

class HomePresenterImpl(
    val homeModel: HomeContract.HomeModel,
    override val view: HomeContract.HomeView
) : HomeContract.HomePresenter {
    override fun getCurrentLocation(): LatLng {
        // TODO: Implement this later
        val currentLocation = homeModel.getCurrentLocation()
        return currentLocation.toLatLng()
    }

    override fun getRoomsForPlace(location: Location): List<Room> {
        // TODO: Implement this later
        return homeModel.getRoomsForPlace()
    }

    override fun saveRoomAsFavourite(room: Room) {
        // TODO: Implement this later
    }

    override fun saveRoomsForOfflineMode(rooms: List<Room>) {
        // TODO: Implement this later
    }

}