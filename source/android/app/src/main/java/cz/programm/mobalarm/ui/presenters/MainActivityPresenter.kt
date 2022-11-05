package cz.programm.mobalarm.ui.presenters

import cz.programm.mobalarm.services.BeaconService

class MainActivityPresenter(val beaconService: BeaconService) {
    var ranging = false


    fun toggleBeaconSearch() {
        if (ranging) {
            beaconService.stopListeningBeacons()
            ranging = false
        } else {
            beaconService.startListeningBeacons()
            ranging = true
        }
    }
}