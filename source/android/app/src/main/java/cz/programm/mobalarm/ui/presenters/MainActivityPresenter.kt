package cz.programm.mobalarm.ui.presenters

import android.icu.util.GregorianCalendar
import cz.programm.mobalarm.services.BeaconService
import cz.programm.mobalarm.ui.activities.MainActivity
import cz.programm.mobalarm.ui.items.BeaconItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import java.util.*

class MainActivityPresenter(val beaconService: BeaconService) {
    var ranging = false
    val adapter = FlexibleAdapter<BeaconItem>(mutableListOf())
    var activity: MainActivity? = null
    private val refreshTimer: Timer = kotlin.concurrent.timer(period = 10000) {
        activity?.runOnUiThread {
            adapter.notifyDataSetChanged()
            if (adapter.currentItems.any { it.timeDifference > 30 }) {
                activity?.showMobNotification()
                stopTimer()
            }
        }
    }

    init {
        beaconService.beaconChangeListener = ::onBeacon;
    }

    private fun stopTimer() = refreshTimer.cancel()

    fun attach(activity: MainActivity) {
        this.activity = activity
    }

    fun toggleBeaconSearch() {
        if (ranging) {
            beaconService.stopListeningBeacons()
            ranging = false
        } else {
            beaconService.startListeningBeacons()
            ranging = true
        }
    }


    fun onBeacon(beaconId: String, distance: Double) {
        val item = adapter.currentItems.firstOrNull { it.beaconId == beaconId }
        if (item != null) {
            item.distance = distance
            item.date = GregorianCalendar.getInstance()
        } else {
            adapter.addItem(BeaconItem(beaconId, distance, GregorianCalendar.getInstance()))
        }

        adapter.notifyDataSetChanged()
    }
}
