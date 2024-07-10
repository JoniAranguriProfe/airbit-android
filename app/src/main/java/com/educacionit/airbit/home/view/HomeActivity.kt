package com.educacionit.airbit.home.view

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.educacionit.airbit.R
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract
import com.educacionit.airbit.home.model.HomeRepository
import com.educacionit.airbit.home.presenter.HomePresenterImpl
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.AdvancedMarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class HomeActivity : AppCompatActivity(), HomeContract.HomeView, OnMapReadyCallback {
    private lateinit var homePresenter: HomeContract.HomePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPresenter()
        configureViews()
    }

    private fun configureViews() {
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

    override fun askForLocationPermissions() {
        // TODO: Implement this later
    }

    override fun centerUserInMap(userLocation: LatLng) {
        // TODO: Implement this later
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        val initialPosition = LatLng(0.0, 0.0)

        val textView = TextView(this)
        textView.text = "Hello!!"
        textView.setBackgroundColor(Color.BLACK)
        textView.setTextColor(Color.YELLOW)

        val marker: Marker? = googleMap.addMarker(
            AdvancedMarkerOptions()
                .position(initialPosition)
                .iconView(textView)
        )


    }
}