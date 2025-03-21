package com.educacionit.airbit.home.model

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.educacionit.airbit.entities.Location
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract
import com.educacionit.airbit.home.model.services.CheckRoomsContract
import com.educacionit.airbit.home.model.services.CheckShowRoomsService
import com.educacionit.airbit.home.model.services.GetCurrentRoomsListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class HomeRepository(context: Context) : HomeContract.HomeModel, GetCurrentRoomsListener {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var currentLocation: Location? = null
    private var checkRoomsService: CheckRoomsContract? = null
    private var currentRooms: List<Room> = emptyList()
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as CheckShowRoomsService.CheckShowRoomsBinder
            checkRoomsService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            checkRoomsService = null
        }

    }

    init {
        initCheckRoomsService(context)
    }

    private fun initCheckRoomsService(context: Context) {
        val serviceIntent = Intent(context, CheckShowRoomsService::class.java)
        context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location {
        return currentLocation ?: run {
            fusedLocationClient.lastLocation.addOnCompleteListener { locationTask ->
                currentLocation = locationTask.result?.let {
                    Location(it.latitude.toFloat(), it.longitude.toFloat())
                }
            }
            delay(50)
            getCurrentLocation()
        }
    }

    override suspend fun getRoomsForPlace(location: Location): List<Room> {
        currentRooms = listOf(
            Room(
                id = 1,
                name = "Unlam hostel",
                location = Location(-34.670629f, -58.562965f),
                pricePerDay = 45.5f
            ),
            Room(
                id = 2,
                name = "Casa de Juan",
                location = Location(-34.668000f, -58.564000f),
                pricePerDay = 50.0f
            ),
            Room(
                id = 3,
                name = "Hospedaje María",
                location = Location(-34.669500f, -58.560000f),
                pricePerDay = 40.0f
            ),
            Room(
                id = 4,
                name = "Pensión Los Robles",
                location = Location(-34.672000f, -58.563000f),
                pricePerDay = 55.0f
            ),
            Room(
                id = 5,
                name = "Hotel El Sol",
                location = Location(-34.671000f, -58.565000f),
                pricePerDay = 60.0f
            ),
            Room(
                id = 6,
                name = "Residencia La Luna",
                location = Location(-34.669000f, -58.566000f),
                pricePerDay = 45.0f
            ),
            Room(
                id = 7,
                name = "Albergue La Estrella",
                location = Location(-34.670000f, -58.561000f),
                pricePerDay = 48.0f
            ),
            Room(
                id = 8,
                name = "Motel Las Flores",
                location = Location(-34.668500f, -58.562500f),
                pricePerDay = 42.0f
            ),
            Room(
                id = 9,
                name = "Hostal Las Palmas",
                location = Location(-34.671500f, -58.564500f),
                pricePerDay = 50.0f
            ),
            Room(
                id = 10,
                name = "Casa de Pedro",
                location = Location(-34.669500f, -58.563500f),
                pricePerDay = 47.0f
            ),
            Room(
                id = 11,
                name = "Hospedaje El Roble",
                location = Location(-34.670500f, -58.562000f),
                pricePerDay = 52.0f
            ),
            Room(
                id = 12,
                name = "Pensión Las Margaritas",
                location = Location(-34.668800f, -58.565800f),
                pricePerDay = 53.0f
            ),
            Room(
                id = 13,
                name = "Hotel El Paraíso",
                location = Location(-34.670200f, -58.561500f),
                pricePerDay = 49.0f
            ),
            Room(
                id = 14,
                name = "Residencia El Bosque",
                location = Location(-34.669300f, -58.566300f),
                pricePerDay = 51.0f
            ),
            Room(
                id = 15,
                name = "Albergue El Río",
                location = Location(-34.670800f, -58.564800f),
                pricePerDay = 46.0f
            ),
            Room(
                id = 16,
                name = "Motel La Cascada",
                location = Location(-34.668200f, -58.562200f),
                pricePerDay = 44.0f
            ),
            Room(
                id = 17,
                name = "Hostal El Jardín",
                location = Location(-34.671200f, -58.563700f),
                pricePerDay = 55.0f
            ),
            Room(
                id = 18,
                name = "Casa de Ana",
                location = Location(-34.669800f, -58.564200f),
                pricePerDay = 50.0f
            ),
            Room(
                id = 19,
                name = "Hospedaje Los Arcos",
                location = Location(-34.670400f, -58.562600f),
                pricePerDay = 52.0f
            ),
            Room(
                id = 20,
                name = "Pensión La Sierra",
                location = Location(-34.668700f, -58.563300f),
                pricePerDay = 48.0f
            )
        )
        return currentRooms
    }

    override fun saveRoomAsFavourite(room: Room) {
        // TODO: Implement this later
    }

    override fun saveRoomsForOfflineMode(rooms: List<Room>) {
        // TODO: Implement this later
    }

    override fun startCheckingRooms(googleMap: GoogleMap) {
        CoroutineScope(Dispatchers.Default).launch {
            while (checkRoomsService == null) {
                delay(200)
            }
            checkRoomsService?.apply {
                setCurrentRoomsListener(this@HomeRepository)
                checkRoomsShownToUser(googleMap)
            }
        }
    }

    override fun tearDown(context: Context) {
        checkRoomsService?.let {
            it.cancelCheckRooms()
            context.unbindService(serviceConnection)
        }
    }

    override fun getCurrentRooms() = currentRooms
}