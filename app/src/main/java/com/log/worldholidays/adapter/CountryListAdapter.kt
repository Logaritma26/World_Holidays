package com.log.worldholidays.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.log.worldholidays.R
import com.log.worldholidays.databinding.CountryListContainerBinding
import com.log.worldholidays.model.CountryDB
import com.log.worldholidays.viewmodel.CountryListViewModel
import com.log.worldholidays.viewmodel.CountryHolidaysViewModel
import kotlinx.android.synthetic.main.country_list_container.view.*

class CountryListAdapter(val country_list : ArrayList<CountryDB>,
                         val viewModel : CountryListViewModel,
                         val listener : AdapterBinder,
                         val holidaysViewModel: CountryHolidaysViewModel) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {


    inner class ViewHolder(var view: CountryListContainerBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CountryListContainerBinding>(inflater, R.layout.country_list_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.model = country_list[position]
        holder.view.adapterBinder = listener

        holder.view.cardviewCountryList.setAnimation(AnimationUtils.loadAnimation(holder.view.cardviewCountryList.context, R.anim.fade_recycler_fast))

        if (country_list[position].favorite){
            holder.itemView.add_button.setImageResource(R.drawable.favorite_icon_pink)
            holder.itemView.is_fav.text = "1"
        } else {
            holder.itemView.add_button.setImageResource(R.drawable.favorite_border)
            holder.itemView.is_fav.text = "0"
        }

        holder.itemView.add_button.setOnClickListener{
            if (country_list[position].favorite){
                country_list[position].favorite = false
                holder.itemView.is_fav.text = "0"
                viewModel.changeFav(country_list[position].uuid, false, holidaysViewModel, country_list[position].alpha2Code)
                holder.itemView.add_button.setImageResource(R.drawable.favorite_border)

            }else{
                country_list[position].favorite = true
                holder.itemView.is_fav.text = "1"
                viewModel.changeFav(country_list[position].uuid, true, holidaysViewModel, country_list[position].alpha2Code)
                holder.itemView.add_button.setImageResource(R.drawable.favorite_icon_pink)
            }
        }

    }

    override fun getItemCount(): Int {
        return country_list.size
    }

    fun renew_data(new_list : List<CountryDB>){
        country_list.clear()
        country_list.addAll(new_list)
        notifyDataSetChanged()
    }


}