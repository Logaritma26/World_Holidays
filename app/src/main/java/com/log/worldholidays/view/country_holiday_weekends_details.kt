package com.log.worldholidays.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.log.worldholidays.R
import com.log.worldholidays.adapter.holiday_weekend.HolidayAdapter
import com.log.worldholidays.adapter.holiday_weekend.WeekendAdapter
import com.log.worldholidays.model.CountryDB
import com.log.worldholidays.model.HolidayDB
import com.log.worldholidays.model.WeekendDB
import com.log.worldholidays.util.glide_image
import com.log.worldholidays.viewmodel.CountryHolidaysViewModel
import kotlinx.android.synthetic.main.fragment_favorite_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class country_holiday_weekends_details : Fragment() {

    private lateinit var viewModel_holiday: CountryHolidaysViewModel

    private lateinit var holiday_adapter: HolidayAdapter
    private lateinit var weekend_adapter: WeekendAdapter


    lateinit var code2: String
    lateinit var flag_url: String
    private var Country: CountryDB? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code2 = it.getString("code2").toString()
            flag_url = it.getString("flag").toString()
        }
        viewModel_holiday = ViewModelProvider(this).get(CountryHolidaysViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_holiday_weekends_details,
            container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CountrySettings()
        glide_image(background_flag, flag_url)

        Handler(Looper.getMainLooper()).postDelayed({
            run {
                setupRecyclerHolidays()
                setupRecyclerWeekends()
            }
        }, 700)

    }

    suspend fun getCountry() = CoroutineScope(Dispatchers.Main).async {
        val country = viewModel_holiday.getCountryWithCode2(code2)
        return@async country
    }.await()

    fun CountrySettings() {
        CoroutineScope(Dispatchers.Main).launch {
            Country = getCountry()
            country_name.text = Country?.name.toString()
        }
    }


    fun setupRecyclerHolidays() {

        holiday_adapter = HolidayAdapter(arrayListOf())

        holidays_recyclerview.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        holidays_recyclerview.adapter = holiday_adapter

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(holidays_recyclerview)

        CoroutineScope(Dispatchers.Main).launch {

            if (checkHolidayAvailable()) {
                val list = viewModel_holiday.getHolidaysWithCode(code2)
                val newList = arrayListOf<HolidayDB>()
                newList.addAll(list)
                text_nonData.visibility = View.GONE
                holidays_recyclerview.visibility = View.VISIBLE
                holiday_adapter.renew_data(newList)

            } else {
                text_nonData.visibility = View.VISIBLE
                holidays_recyclerview.visibility = View.GONE
            }
        }
    }



    suspend fun checkHolidayAvailable() = CoroutineScope(Dispatchers.Main).async {
        var boolean: Boolean = false
        val list = viewModel_holiday.getDistinctCountries()
        val newList = arrayListOf<String>()
        newList.addAll(list)

        for (i in 0..newList.size - 1) {
            if (newList.get(i).equals(code2)) {
                boolean = true
                break
            }
        }
        return@async boolean
    }.await()


    fun setupRecyclerWeekends() {

        weekend_adapter = WeekendAdapter(arrayListOf())

        weekends_recyclerview.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        weekends_recyclerview.adapter = weekend_adapter

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(weekends_recyclerview)

        CoroutineScope(Dispatchers.Main).launch {

            if (checkWeekendAvailable()) {
                val list = viewModel_holiday.getWeekendsWithCode(code2)
                val newList = arrayListOf<WeekendDB>()
                newList.addAll(list)
                text_nonData_weekends.visibility = View.GONE
                weekends_recyclerview.visibility = View.VISIBLE
                weekend_adapter.renew_data(newList)
            } else {
                text_nonData_weekends.visibility = View.VISIBLE
                weekends_recyclerview.visibility = View.GONE
            }
        }
    }

    suspend fun checkWeekendAvailable() = CoroutineScope(Dispatchers.Main).async {
        var boolean: Boolean = false
        val list = viewModel_holiday.getDistinctCountries_Weekend()
        val newList = arrayListOf<String>()
        newList.addAll(list)

        for (i in 0..newList.size - 1) {
            if (newList.get(i).equals(code2)) {
                boolean = true
                break
            }
        }
        return@async boolean
    }.await()
}