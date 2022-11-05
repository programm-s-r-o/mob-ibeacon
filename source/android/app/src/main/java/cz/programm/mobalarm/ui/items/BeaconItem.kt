package cz.programm.mobalarm.ui.items

import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cz.programm.mobalarm.R
import cz.programm.mobalarm.databinding.BeaconItemBinding
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

class BeaconItem(val beaconId: String, var distance: Double, var date: Calendar) : AbstractFlexibleItem<BeaconItem.BeaconViewHolder>() {


    override fun equals(other: Any?): Boolean = if (other is BeaconItem) {
        beaconId == other.beaconId
    } else {
        false
    }

    override fun getLayoutRes(): Int = R.layout.beacon_item

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): BeaconViewHolder = BeaconViewHolder(view, adapter)

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?, holder: BeaconViewHolder?, position: Int, payloads: MutableList<Any>?) {
        holder!!.binding.beaconName.text = beaconId
        holder!!.binding.beaconDistance.text = "${timeDifference}s - ${roundedDistance}m"
    }

    val timeDifference: Long
        get() = (GregorianCalendar.getInstance().timeInMillis - date.timeInMillis) / (1000)
    val roundedDistance: Double
        get() = Math.round(distance * 100).toDouble() / 100

    class BeaconViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {
        var binding: BeaconItemBinding private set

        init {
            binding = DataBindingUtil.bind(view)!!
        }
    }
}