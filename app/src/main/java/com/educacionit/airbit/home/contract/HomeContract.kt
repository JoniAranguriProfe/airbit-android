package com.educacionit.airbit.home.contract

import com.educacionit.airbit.base.contract.BaseContract
import com.educacionit.airbit.entities.Location
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.model.entities.RoomsResponse
import com.google.android.gms.maps.model.LatLng

interface HomeContract {
    interface HomeView : BaseContract.BaseView {
        // For Presenter usage
        fun goToReservation(room: Room)

        // For internal usage
        fun configureMapZoomLevel(zoomLevel: Float)
        fun askForLocationPermissions()
        fun centerUserInMap(userLocation: LatLng)
    }

    interface HomePresenter : BaseContract.BasePresenter<HomeView> {
        // For View usage
        fun getCurrentLocation(): LatLng
        fun getRoomsForPlace(location: Location): List<Room>
        fun saveRoomAsFavourite(room: Room)

        // For internal usage
        fun saveRoomsForOfflineMode(rooms: List<Room>) // Todo: Use 24 hs TTL.
    }

    interface HomeModel : BaseContract.BaseModel {
        fun getCurrentLocation(): Location
        fun getRoomsForPlace(): List<Room>
        fun saveRoomAsFavourite(room: Room)
        fun saveRoomsForOfflineMode(rooms: List<Room>)
    }
}