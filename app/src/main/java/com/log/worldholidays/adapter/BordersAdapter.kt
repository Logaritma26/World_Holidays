package com.log.worldholidays.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.log.worldholidays.R
import com.log.worldholidays.util.glide_image
import kotlinx.android.synthetic.main.borders_viewpager_container.view.*

class BordersAdapter(val borders: ArrayList<String>,
                     val flags: ArrayList<String>,
                     val listener : DetailClick) :
    RecyclerView.Adapter<BordersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            if (p0 != null) {
                listener.onDetailsClick(p0, p0.alpha3.text.toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.borders_viewpager_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.alpha3.text = borders.get(position)
        glide_image(holder.itemView.border_country_flag, flags.get(position))

    }

    override fun getItemCount(): Int {
        return borders.size
    }

    fun refresh_borders(new_borders: ArrayList<String>, new_flags: ArrayList<String>) {
        borders.clear()
        flags.clear()
        borders.addAll(new_borders)
        flags.addAll(new_flags)
        notifyDataSetChanged()
    }

    interface DetailClick {
        fun onDetailsClick(view: View, code3: String)
    }

}