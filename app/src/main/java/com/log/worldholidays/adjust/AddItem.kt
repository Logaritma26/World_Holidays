package com.log.worldholidays.adjust

import android.annotation.SuppressLint
import android.media.Image
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.log.worldholidays.R

private lateinit var recyclerView: RecyclerView

class AddItem : AppCompatActivity(), CountryListAdapter.RecyclerOnClick {
    @SuppressLint("ClickableViewAccessibility")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)



        recyclerView = findViewById(R.id.country_list)

        recyclerView.adapter = CountryListAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)




    }

    override fun OnClick(view: View, position: Int) {
        if (view.visibility == View.VISIBLE){
            view.visibility = View.GONE
        } else if (view.visibility == View.GONE){
            view.visibility = View.VISIBLE
        }

    }

    override fun OnClick(view: View) {
        TODO("Not yet implemented")
    }


}


