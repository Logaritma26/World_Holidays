package com.log.worldholidays.adjust

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.log.worldholidays.MainActivity.Companion.MAP
import com.log.worldholidays.R
import kotlinx.android.synthetic.main.country_list_container.view.*

class CountryListAdapter(var context : Context, val onClick: RecyclerOnClick) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.country_list_container, parent, false)
        return ViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setIsRecyclable(false)

        val code : String = MAP.keys.elementAt(position)

        if (MAP[code].equals("empty")){
            holder.name_of_country.text = "empty"
        } else{
            holder.name_of_country.text = MAP[code]
        }

        Glide.with(context)
            .load("https://www.countryflags.io/$code/shiny/64.png")
            .centerCrop()
            .into(holder.image_flag)



    }

    override fun getItemCount(): Int {
        return MAP.size
    }

    class ViewHolder(itemView: View, onClick : RecyclerOnClick) : RecyclerView.ViewHolder(itemView) {
        val image_flag : ImageView = itemView.image_flag
        val name_of_country : TextView = itemView.country_name
        val add_button : ImageButton = itemView.add_button

        init {
            itemView.setOnClickListener{
                val position : Int = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    onClick.OnClick(add_button, position)
                }
            }
        }

    }


    public interface RecyclerOnClick{
        fun OnClick(view : View, position: Int)
        fun OnClick(view : View)
    }


}