package cz.programm.mobalarm.services

import android.content.Context
import android.media.RingtoneManager


class RingService(val context: Context) {
    val ringTone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))


    fun startRinging() {
        ringTone.play()
    }

    fun stopRinging() {
        ringTone.stop()
    }
}