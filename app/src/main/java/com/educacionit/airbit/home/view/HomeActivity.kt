package com.educacionit.airbit.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.educacionit.airbit.R
import com.educacionit.airbit.base.common.MapsManager
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract
import com.educacionit.airbit.home.model.HomeRepository
import com.educacionit.airbit.home.presenter.HomePresenterImpl
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), HomeContract.HomeView, OnMapReadyCallback {
    private lateinit var homePresenter: HomeContract.HomePresenter
    private var googleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPresenter()
        configureMaps()
        homePresenter.checkNotificationsPermissions(this)
    }

    private fun configureMaps() {
        homePresenter.checkMapPreconditions(this)
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.main_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun initPresenter() {
        val homeModel: HomeContract.HomeModel = HomeRepository(context = this)
        homePresenter = HomePresenterImpl(homeModel = homeModel, view = this)
    }

    override fun goToReservation(room: Room) {
        // TODO: Implement this later
    }

    override fun getViewContext() = this


    override fun showNoInternetMessage() {
        // Todo: Implement logic to close the app
        showErrorMessage("Sin internet, desea salir?")
    }

    override fun configureMapZoomLevel(zoomLevel: Float) {
        // TODO: Implement this later
    }

    override fun askForLocationPermissions(targetMapFeature: () -> Unit) {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                showErrorMessage("Acaba de denegar el permiso de ubicacion")
                return@registerForActivityResult
            }
            targetMapFeature.invoke()
        }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun askForNotificationPermissions() {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                showErrorMessage("Acaba de denegar el permiso de notificaciones")
                return@registerForActivityResult
            }
        }.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun centerUserInMap(userLocation: LatLng) {
        lifecycleScope.launch {
            while (googleMap == null) {
                delay(200)
            }
            googleMap?.let {
                MapsManager.centerUserInMap(it, userLocation)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun configureMapsSettings() {
        lifecycleScope.launch {
            while (googleMap == null) {
                delay(200)
            }
            googleMap?.isMyLocationEnabled = true
        }
    }

    override fun showRoomsInMap(rooms: List<Room>) {
        MapsManager.showRoomsInMap(this, googleMap, rooms)
    }

    override fun onMapReady(updatedGoogleMap: GoogleMap) {
        googleMap = updatedGoogleMap
        googleMap?.let { homePresenter.startCheckingRooms(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.tearDown(this)
    }
}