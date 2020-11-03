package com.log.worldholidays.view

import android.os.Bundle
import android.os.SystemClock
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import kotlinx.android.synthetic.main.country_list_container.view.*
import kotlinx.android.synthetic.main.fragment_favorite_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


lateinit var navController: NavController
private lateinit var viewModel_holiday: CountryHolidaysViewModel

private lateinit var holiday_adapter: HolidayAdapter
private lateinit var weekend_adapter: WeekendAdapter


lateinit var code2: String
lateinit var flag_url: String
private var Country: CountryDB? = null

private var DELETE: Boolean = false
private var mLastClickTime: Long = 0


class FavoriteDetail : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        code2 = requireArguments().getString("code2").toString()
        flag_url = requireArguments().getString("flag").toString()
        viewModel_holiday = ViewModelProvider(this).get(CountryHolidaysViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_favorite_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DELETE = false
        navController = Navigation.findNavController(view)

        CountrySettings()
        onBackPressed()
        fav_button()
        glide_image(background_flag, flag_url)
        setupRecyclerHolidays()
        setupRecyclerWeekends()
        onClick_more()
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //Handle back event from any fragment

                    if (DELETE) {
                        viewModel_holiday.changeFav(!DELETE, code2)
                    }
                    navController.popBackStack()

                }
            })
    }

    fun fav_button(){

        favorite_check_button.setOnClickListener {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 700){
                return@setOnClickListener;
            }

            mLastClickTime = SystemClock.elapsedRealtime()
            if (DELETE){
                DELETE = false
                favorite_check_button.setBackgroundResource(R.drawable.favorite_icon_pink)
            } else {
                DELETE = true
                favorite_check_button.setBackgroundResource(R.drawable.favorite_border)
            }
        }

    }

    suspend fun getCountry() = CoroutineScope(Dispatchers.Main).async {
        val country = viewModel_holiday.getCountryWithCode2(code2)
        return@async country
    }.await()

    fun CountrySettings(){

        CoroutineScope(Dispatchers.Main).launch {
            Country = getCountry()
            country_name.text = Country?.name.toString()
        }


    }

    fun onClick_more(){
        more_button.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                val code3 = viewModel_holiday.convert_code2_to_code3(code2)
                val bundle = bundleOf()
                bundle.putString("code3", code3)
                bundle.putInt("source", 2)
                navController.navigate(R.id.action_favoriteDetail_to_countryDetails, bundle)
            }

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

            if (checkHolidayAvailable()){
                val list = viewModel_holiday.getHolidaysWithCode(code2)
                val newList = arrayListOf<HolidayDB>()
                newList.addAll(list)
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

        for (i in 0..newList.size-1){
            if (newList.get(i).equals(code2)){
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

            if (checkWeekendAvailable()){
                val list = viewModel_holiday.getWeekendsWithCode(code2)
                val newList = arrayListOf<WeekendDB>()
                newList.addAll(list)
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

        for (i in 0..newList.size-1){
            if (newList.get(i).equals(code2)){
                boolean = true
                break
            }
        }
        return@async boolean
    }.await()


}