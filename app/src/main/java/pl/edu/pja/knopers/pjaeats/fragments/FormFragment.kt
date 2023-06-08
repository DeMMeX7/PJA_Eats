package pl.edu.pja.knopers.pjaeats.fragments

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import pl.edu.pja.knopers.pjaeats.Destination
import pl.edu.pja.knopers.pjaeats.MainActivity
import pl.edu.pja.knopers.pjaeats.MyReceiver
import pl.edu.pja.knopers.pjaeats.adapters.DishImagesAdapter
import pl.edu.pja.knopers.pjaeats.data.DbRepository
import pl.edu.pja.knopers.pjaeats.data.MemoryRepository
import pl.edu.pja.knopers.pjaeats.data.Repository
import pl.edu.pja.knopers.pjaeats.databinding.FragmentFormBinding
import pl.edu.pja.knopers.pjaeats.model.Dish
import pl.edu.pja.knopers.pjaeats.navigate
import kotlin.concurrent.thread

class FormFragment : Fragment(), MapEventsReceiver {

    private val repository: Repository by lazy { DbRepository(requireContext().applicationContext) }
    lateinit var marker: Marker
    private lateinit var binding: FragmentFormBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFormBinding.inflate(inflater, container, false)
            .also { binding ->
                this.binding = binding
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dishImagesAdapter = DishImagesAdapter()
        binding.images.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = dishImagesAdapter
        }

        val dishId = arguments?.getLong("id") ?: 0
        if (dishId > 0) {
            thread {
                val dish = repository.getDish(dishId)
                binding.root.post {
                    binding.nameField.setText(dish.name)
                    dishImagesAdapter.selected = dish.image
                    binding.descriptionField.setText(dish.description)
                }
            }
        }

        binding.saveButton.setOnClickListener {
            val dish = Dish(
                dishId,
                binding.nameField.text.toString(),
                dishImagesAdapter.selected,
                binding.descriptionField.text.toString(),
                23.000,
                23.000
            )
            thread {
                repository.add(dish)
                binding.root.post {
                    activity?.runOnUiThread {
                        navigate(Destination.List)
                    }
                }
            }
        }
    }

    override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
        p?.let {
            marker.position = it

            Polygon(binding.map).apply {
                val radius = 500.0
                points = Array(360) { p.destinationPoint(radius, it.toDouble()) }.toList()
            }.let { binding.map.overlays.add(it) }

            createGofence(it)
        }
        return true
    }

    override fun longPressHelper(p: GeoPoint?): Boolean {
        return false
    }
    private fun createGofence(p: GeoPoint) {
        val geofence = Geofence.Builder()
            .setCircularRegion(p.latitude, p.longitude, 500f)
            .setRequestId("quarantine")
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()

        val req = GeofencingRequest.Builder()
            .addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()

        val intent = Intent(requireContext(), MyReceiver::class.java)
        val pi = PendingIntent.getBroadcast(
            context,
            12,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
        )

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getGeofencingClient(activity!!)
                .addGeofences(req, pi )
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Created", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{
                    Toast.makeText(requireContext(), "it", Toast.LENGTH_LONG).show()
                }
        }

    }
}