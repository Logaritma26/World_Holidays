package com.log.worldholidays.adapter.holiday_weekend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.log.worldholidays.R
import com.log.worldholidays.databinding.HolidayContainerBinding
import com.log.worldholidays.databinding.WeekendContainerBinding
import com.log.worldholidays.model.HolidayDB
import com.log.worldholidays.model.WeekendDB

class WeekendAdapter(var weekends: ArrayList<WeekendDB>) :
    RecyclerView.Adapter<WeekendAdapter.ViewHolder>() {


    inner class ViewHolder(var view: WeekendContainerBinding) : RecyclerView.ViewHolder(view.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            p0?.let {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<WeekendContainerBinding>(inflater,
            R.layout.weekend_container,
            parent,
            false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.view.cardViewWeekend.setAnimation(AnimationUtils.loadAnimation(holder.view.cardViewWeekend.context, R.anim.fade_recycler))
        holder.view.model = weekends[position]
    }

    override fun getItemCount(): Int {
        return weekends.size
    }

    fun renew_data(new_list: ArrayList<WeekendDB>) {
        weekends.clear()
        weekends = new_list
        notifyDataSetChanged()
    }


}