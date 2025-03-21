package com.educacionit.airbit.base.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.educacionit.airbit.R
import com.educacionit.airbit.entities.Room
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip

object MapsManager {
    fun centerUserInMap(googleMap: GoogleMap, userLocation: LatLng) {
        val cameraPosition = CameraPosition.Builder()
            .target(userLocation) // Sets the center of the map to Mountain View
            .zoom(15.4f)             // Sets the zoom
            .bearing(90f)         // Sets the orientation of the camera to east
            .tilt(30f)            // Sets the tilt of the camera to 30 degrees
            .build()              // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun showRoomsInMap(
        context: Context,
        googleMap: GoogleMap?,
        rooms: List<Room>,
        roomClickEvent: (Room) -> Unit
    ) {
        val roomsById = rooms.associateBy { it.id }
        rooms.forEach { currentRoom ->
            val markerOptions = MarkerOptions()
                .position(currentRoom.location.toLatLng())
                .icon(getCustomMarkerView(context, currentRoom))

            googleMap?.let {
                val currentMarker = it.addMarker(markerOptions)
                currentMarker?.tag = currentRoom.id

                it.setOnMarkerClickListener { currentMarker ->
                    val roomId = currentMarker.tag as Int
                    val selectedRoom = roomsById[roomId]
                    selectedRoom?.let { safeRoom ->
                        roomClickEvent(safeRoom)
                    }
                    true
                }
            }
        }
    }

    private fun getCustomMarkerView(context: Context, room: Room): BitmapDescriptor {
        val markerView =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.price_marker, null
            )
        markerView.findViewById<TextView>(R.id.price_title).text = "$ ${room.pricePerDay}"

        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
            .defaultDisplay.getMetrics(displayMetrics)
        markerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        markerView.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        markerView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        markerView.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            markerView.measuredWidth,
            markerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        markerView.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}