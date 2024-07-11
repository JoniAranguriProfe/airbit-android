package com.educacionit.airbit.home.presenter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.educacionit.airbit.entities.Location
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePresenterImpl(
    val homeModel: HomeContract.HomeModel,
    override val view: HomeContract.HomeView
) : HomeContract.HomePresenter {
    override fun checkMapPreconditions(context: Context) {
        val targetMapFeature: () -> Unit = {
            CoroutineScope(Dispatchers.Main).launch {
                view.configureMapsSettings()
                view.centerUserInMap(getCurrentLocation())
            }
        }
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                targetMapFeature.invoke()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                view.showErrorMessage("Necesitamos el permiso de ubicacion")
            }

            else -> view.askForLocationPermissions(targetMapFeature)
        }
    }

    override suspend fun getCurrentLocation(): LatLng = withContext(Dispatchers.IO) {
        homeModel.getCurrentLocation().toLatLng()
    }

    override fun getRoomsForPlace(location: Location): List<Room> {
        // TODO: Implement this later
        return homeModel.getRoomsForPlace(location)
    }

    override fun saveRoomAsFavourite(room: Room) {
        // TODO: Implement this later
    }

    override fun saveRoomsForOfflineMode(rooms: List<Room>) {
        // TODO: Implement this later
    }

}