package pl.edu.pja.knopers.pjaeats

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.GeofencingEvent

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        GeofencingEvent.fromIntent(intent)?.let {
            //    if (it.triggeringGeofences.first().requestId)

            context.getSystemService(NotificationManager::class.java)
                .apply {
                    val noti = NotificationCompat.Builder(context, "quarantine_notifications" )
                        .setContentTitle("Get Back to the quarantine area")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .build()
                    notify(1, noti)
                }
        }

    }
}