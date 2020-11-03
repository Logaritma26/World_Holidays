package com.log.worldholidays.adapter.holiday_weekend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.log.worldholidays.R
import com.log.worldholidays.util.glide_image
import kotlinx.android.synthetic.main.borders_viewpager_container.view.alpha3
import kotlinx.android.synthetic.main.holiday_weekend_recycler_container.view.*

class HolidayWeekendAdapter (val district_countries: ArrayList<String>,
                             val flags: ArrayList<String>,
                            val listener: FavoriteClick)
    : RecyclerView.Adapter<HolidayWeekendAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)

        }
        override fun onClick(p0: View?) {
            p0?.let {

                listener.onFavoriteClick(district_countries[this.adapterPosition], flags[this.adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holiday_weekend_recycler_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        glide_image(holder.itemView.country_flag, flags.get(position))

    }

    override fun getItemCount(): Int {
        return district_countries.size
    }


    fun refresh_data(new_distinct: ArrayList<String>, new_flags: ArrayList<String>) {
        district_countries.clear()
        flags.clear()
        district_countries.addAll(new_distinct)
        flags.addAll(new_flags)
        notifyDataSetChanged()
    }

    interface FavoriteClick {
        fun onFavoriteClick(code2: String, flag: String)
    }

}