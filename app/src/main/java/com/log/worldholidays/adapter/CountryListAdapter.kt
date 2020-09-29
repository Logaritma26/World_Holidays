package com.log.worldholidays.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.log.worldholidays.R
import com.log.worldholidays.databinding.CountryListContainerBinding
import com.log.worldholidays.model.CountryDB
import com.log.worldholidays.viewmodel.CountrListViewModel
import kotlinx.android.synthetic.main.country_list_container.view.*

class CountryListAdapter(val country_list : ArrayList<CountryDB>,
                         val viewModel : CountrListViewModel,
                         val listener : AdapterBinder) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    inner class ViewHolder(var view: CountryListContainerBinding) : RecyclerView.ViewHolder(view.root) {

        /*init {
            view.root.setOnClickListener(this)
        }*/
/*

        override fun onClick(p0: View?) {
            if (p0 != null) {
                listener.onCountryClick(p0)
            }
        }
*/

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CountryListContainerBinding>(inflater, R.layout.country_list_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.model = country_list[position]
        holder.view.adapterBinder = listener

        if (country_list[position].favorite){
            holder.itemView.add_button.setImageResource(R.drawable.favorite_icon_pink)
        } else {
            holder.itemView.add_button.setImageResource(R.drawable.favorite_border)
        }

        holder.itemView.add_button.setOnClickListener{
            if (country_list[position].favorite){
                country_list[position].favorite = false
                viewModel.changeFav(country_list[position].uuid, false)
                holder.itemView.add_button.setImageResource(R.drawable.favorite_border)

            }else{
                country_list[position].favorite = true
                viewModel.changeFav(country_list[position].uuid, true)
                holder.itemView.add_button.setImageResource(R.drawable.favorite_icon_pink)
            }
        }

    }

    override fun getItemCount(): Int {
        return country_list.size
    }

    fun refresh_recycler(new_list : List<CountryDB>){
        country_list.clear()
        country_list.addAll(new_list)
        notifyDataSetChanged()
    }


}