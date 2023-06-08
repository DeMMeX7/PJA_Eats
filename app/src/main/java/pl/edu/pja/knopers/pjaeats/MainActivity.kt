package pl.edu.pja.knopers.pjaeats

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import pl.edu.pja.knopers.pjaeats.fragments.FormFragment
import pl.edu.pja.knopers.pjaeats.fragments.ListFragment
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import pl.edu.pja.knopers.pjaeats.databinding.FragmentFormBinding


class MainActivity : AppCompatActivity(), Navigable {

    private lateinit var listFragment: ListFragment


    lateinit var marker: Marker
    val binding by lazy { FragmentFormBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listFragment = ListFragment()
        navigate(Destination.List)
        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(
                NotificationChannel("quarantine_notifications",
                    "Quarantine Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )


        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

        }.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS,

                )
        )


        Configuration.getInstance().userAgentValue = packageName

//        binding.map.apply {
//            setTileSource(TileSourceFactory.MAPNIK)
//            controller.animateTo(GeoPoint(52.2236096,20.9934412), 16.0, 2000)
//            overlays.add(MapEventsOverlay(this@MainActivity))
//            marker = Marker(this)
//            overlays.add(marker)
//            overlays.add(MyLocationNewOverlay(this).apply { enableMyLocation() })
//        }
    }

//    override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
//        p?.let {
//            marker.position = it
//
//            Polygon(binding.map).apply {
//                val radius = 500.0
//                points = Array(360) { p.destinationPoint(radius, it.toDouble()) }.toList()
//            }.let { binding.map.overlays.add(it) }
//
//            createGofence(it)
//        }
//        return true
//    }

//    private fun createGofence(p: GeoPoint) {
//        val geofence = Geofence.Builder()
//            .setCircularRegion(p.latitude, p.longitude, 500f)
//            .setRequestId("quarantine")
//            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
//            .setExpirationDuration(Geofence.NEVER_EXPIRE)
//            .build()
//
//        val req = GeofencingRequest.Builder()
//            .addGeofence(geofence)
//            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
//            .build()
//
//        val intent = Intent(this, MyReceiver::class.java)
//        val pi = PendingIntent.getBroadcast(
//            applicationContext,
//            12,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
//        )
//
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            LocationServices.getGeofencingClient(this)
//                .addGeofences(req, pi )
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Created", Toast.LENGTH_LONG).show()
//                }
//                .addOnFailureListener{
//                    Toast.makeText(this, "it", Toast.LENGTH_LONG).show()
//                }
//        }

//    }
//
//    override fun longPressHelper(p: GeoPoint?): Boolean {
//        return false
//    }


    override fun navigate(destination: Destination) {
        when (destination) {
            Destination.List -> supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainerView,
                    listFragment,
                    ListFragment::class.java.simpleName
                )
                .commit()

            Destination.Add -> supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainerView,
                    FormFragment::class.java,
                    null,
                    FormFragment::class.java.simpleName
                )
                .commit()

            is Destination.Edit -> supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainerView,
                    FormFragment::class.java,
                    Bundle().apply {
                        putLong("id", destination.dish.id)
                    },
                    FormFragment::class.java.simpleName
                )
                .commit()
        }
    }

}

