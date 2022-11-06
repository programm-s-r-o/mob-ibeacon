package cz.programm.mobalarm.services

import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.util.Log
import org.altbeacon.beacon.*
import java.util.UUID


class BeaconService(val context: Context) {
    private val beaconManager = BeaconManager.getInstanceForApplication(context)
    private val region = Region("all-beacons-region", null, null, null)
    lateinit var beaconChangeListener: (beaconId: String, distance: Double) -> Unit

    private val beaconParser = BeaconParser()
        .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25")
    private val beaconTransmitter = BeaconTransmitter(context, beaconParser)

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

    fun startTransmitting() {
        val beacon = Beacon.Builder()
            .setId1(UUID.randomUUID().toString())
            .setId2("1")
            .setId3("2")
            .setManufacturer(0x0118) // Radius Networks.  Change this for other beacon layouts
            .setTxPower(-59)
            .setDataFields(listOf(0L))
            .build()
        beaconTransmitter.startAdvertising(beacon, object : AdvertiseCallback() {
            override fun onStartFailure(errorCode: Int) {
                Log.e("BeaconService", "Advertisement start failed with code: $errorCode")
            }

            override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                Log.i("BeaconService", "Advertisement start succeeded.")
            }
        })

    }

    fun stopTransmitting() = beaconTransmitter.stopAdvertising()
}