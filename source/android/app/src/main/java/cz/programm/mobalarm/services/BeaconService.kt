package cz.programm.mobalarm.services

import android.content.Context
import android.util.Log
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region

class BeaconService(val context: Context) {
    private val beaconManager = BeaconManager.getInstanceForApplication(context)
    private val region = Region("all-beacons-region", null, null, null)
    lateinit var beaconChangeListener: (beaconId: String, distance: Double) -> Unit

    fun startListeningBeacons() {
        beaconManager.beaconParsers.clear()
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19"))
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15"))
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20"))
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"))

        beaconManager.addRangeNotifier { beacons, region ->
            beacons.forEach {
                Log.d("BeaconService", "${it.id1} ; ${it.distance}")
                beaconChangeListener?.invoke(it.id1.toHexString(), it.distance)
            }
        }

        beaconManager.startRangingBeacons(region)
    }

    fun stopListeningBeacons() {
        beaconManager.stopRangingBeacons(region)
        beaconManager.removeAllRangeNotifiers()
    }
}