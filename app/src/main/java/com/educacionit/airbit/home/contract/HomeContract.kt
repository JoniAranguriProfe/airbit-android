package com.educacionit.airbit.home.contract

import android.content.Context
import com.educacionit.airbit.base.contract.BaseContract
import com.educacionit.airbit.entities.Location
import com.educacionit.airbit.entities.Room
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

interface HomeContract {
    interface HomeView : BaseContract.BaseView {
        // For Presenter usage
        fun goToReservation(room: Room)
        fun configureMapsSettings()
        fun showRoomsInMap(rooms: List<Room>)

        // For internal usage
        fun configureMapZoomLevel(zoomLevel: Float)
        fun askForLocationPermissions(targetMapFeature: () -> Unit)
        fun askForNotificationPermissions()
        fun centerUserInMap(userLocation: LatLng)
    }

    interface HomePresenter : BaseContract.BasePresenter<HomeView> {
        // For View usage
        fun checkMapPreconditions(context: Context)
        fun checkNotificationsPermissions(context: Context)
        suspend fun getCurrentLocation(): LatLng
        fun getRoomsForPlace(location: Location)
        fun saveRoomAsFavourite(room: Room)
        fun tearDown(context: Context)
        fun startCheckingRooms(googleMap: GoogleMap)

        // For internal usage
        fun saveRoomsForOfflineMode(rooms: List<Room>) // Todo: Use 24 hs TTL.
    }

    interface HomeModel : BaseContract.BaseModel {
        suspend fun getCurrentLocation(): Location
        suspend fun getRoomsForPlace(location: Location): List<Room>
        fun saveRoomAsFavourite(room: Room)
        fun saveRoomsForOfflineMode(rooms: List<Room>)
        fun startCheckingRooms(googleMap: GoogleMap)
        fun tearDown(context: Context)
    }
}