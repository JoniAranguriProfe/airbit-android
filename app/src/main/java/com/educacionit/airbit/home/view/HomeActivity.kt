package com.educacionit.airbit.home.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.educacionit.airbit.R
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract
import com.educacionit.airbit.home.model.HomeRepository
import com.educacionit.airbit.home.presenter.HomePresenterImpl
import com.educacionit.airbit.reservation.view.ReservationActivity
import com.google.android.gms.maps.model.LatLng

class HomeActivity : AppCompatActivity(), HomeContract.HomeView {
    private lateinit var homePresenter: HomeContract.HomePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPresenter()

        val intent = Intent(this, ReservationActivity::class.java)
        startActivity(intent)
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
}