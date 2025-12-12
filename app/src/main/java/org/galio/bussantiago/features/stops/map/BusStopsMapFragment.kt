package org.galio.bussantiago.features.stops.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.galio.bussantiago.common.getParcelableArgument
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.navigation.Navigator
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusStopsMapFragment : SupportMapFragment(), OnMapReadyCallback {

  private val viewModel: BusStopsMapViewModel by viewModel()
  private val navigator: Navigator by lazy { Navigator(this) }

  private var busStopsArgs: BusStopsArgs? = null
  private var mGoogleMap: GoogleMap? = null
  private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

  companion object {
    private const val BUS_STOPS_ARGS_KEY = "bus_stops_args_key"
    fun newInstance(busStopsArgs: BusStopsArgs): BusStopsMapFragment {
      val fragment = BusStopsMapFragment()
      val bundle = Bundle()
      bundle.putParcelable(BUS_STOPS_ARGS_KEY, busStopsArgs)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun onCreate(bundle: Bundle?) {
    super.onCreate(bundle)

    requestPermissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
          enableMyLocation()
        }
      }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    busStopsArgs = getParcelableArgument<BusStopsArgs>(BUS_STOPS_ARGS_KEY)

    getMapAsync(this)

    viewModel.lineMapModel.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onError = { handleException(it) },
        onSuccess = { setUpMap(it) }
      )
    }

    viewModel.navigationEvent.observe(viewLifecycleOwner) { navScreen ->
      navigator.navigate(navScreen)
    }
  }

  override fun onMapReady(googleMap: GoogleMap) {
    mGoogleMap = googleMap
    enableMyLocation()
    mGoogleMap?.setOnInfoWindowClickListener { marker ->
      viewModel.onInfoWindowClick(marker.title, marker.snippet)
    }

    busStopsArgs?.let {
      viewModel.load(it)
    } ?: Log.w("BusStopsMapFragment", "Argument BusStopsArgs was not sent correctly.")
  }

  private fun setUpMap(lineMapModel: LineMapModel) {
    val polylineOptions = PolylineOptions()

    val busStopMapModels = lineMapModel.busStopMapModels

    busStopMapModels.forEachIndexed { index, busStopMapModel ->
      var hue = BitmapDescriptorFactory.HUE_RED
      if (index == 0 || index == busStopMapModels.lastIndex) {
        hue = BitmapDescriptorFactory.HUE_BLUE
      }

      val latLng = LatLng(
        busStopMapModel.coordinates.latitude,
        busStopMapModel.coordinates.longitude
      )

      polylineOptions.add(latLng)

      mGoogleMap?.addMarker(
        MarkerOptions()
          .position(latLng)
          .title(busStopMapModel.code)
          .snippet(busStopMapModel.name)
      )?.setIcon(BitmapDescriptorFactory.defaultMarker(hue))
    }

    polylineOptions.color(lineMapModel.lineStyle.toColorInt())

    mGoogleMap?.addPolyline(polylineOptions)

    busStopMapModels.firstOrNull()?.let { firstBusStopMapModel ->
      val firstLatLng = LatLng(
        firstBusStopMapModel.coordinates.latitude,
        firstBusStopMapModel.coordinates.longitude
      )
      mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 13f))
    }
  }

  private fun enableMyLocation() {
    if (ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      mGoogleMap?.isMyLocationEnabled = true
    } else {
      requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
  }
}
