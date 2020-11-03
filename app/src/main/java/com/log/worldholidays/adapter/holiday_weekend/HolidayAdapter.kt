package com.log.worldholidays.adapter.holiday_weekend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.log.worldholidays.R
import com.log.worldholidays.databinding.HolidayContainerBinding
import com.log.worldholidays.model.CountryDB
import com.log.worldholidays.model.HolidayDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class HolidayAdapter(var holidays: ArrayList<HolidayDB>) :
    RecyclerView.Adapter<HolidayAdapter.ViewHolder>() {

    inner class ViewHolder(var view: HolidayContainerBinding) : RecyclerView.ViewHolder(view.root),
        View.OnClickListener {
        init {
            view.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            p0?.let {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<HolidayContainerBinding>(inflater,
            R.layout.holiday_container,
            parent,
            false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.view.cardViewHolidays.setAnimation(AnimationUtils.loadAnimation(holder.view.cardViewHolidays.context, R.anim.fade_recycler))
        holder.view.model = holidays[position]
    }

    override fun getItemCount(): Int {
        return holidays.size
    }

    fun renew_data(new_list: ArrayList<HolidayDB>) {
        holidays.clear()
        holidays = new_list
        notifyDataSetChanged()
    }

}