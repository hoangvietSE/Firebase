package com.soict.hoangviet.firebase.map.listener

import com.google.android.gms.maps.model.LatLng

interface FetchPlaceListener {
    fun onSuccessFetchPlaces(mLatLng: LatLng)
    fun onErrorFetchPlaces()
}