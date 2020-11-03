package com.log.worldholidays.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.*
import com.log.worldholidays.R
import com.log.worldholidays.adapter.holiday_weekend.HolidayWeekendAdapter
import com.log.worldholidays.viewmodel.CountryHolidaysViewModel
import kotlinx.android.synthetic.main.fragment_homepage.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class Homepage : Fragment(), HolidayWeekendAdapter.FavoriteClick {

    private lateinit var viewModel: CountryHolidaysViewModel

    private lateinit var adapter: HolidayWeekendAdapter

    lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation_viewModel(view)




        setRecyclerView()
        observer_live_holidays()
    }

    private fun setRecyclerView() {

        adapter = HolidayWeekendAdapter(arrayListOf(), arrayListOf(), this)

        recyclerView_holidays_weekends.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView_holidays_weekends.adapter = adapter

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView_holidays_weekends)

        getLiveDataForRecycler()
    }

    fun getLiveDataForRecycler(){

        CoroutineScope(Main).launch {
            val list = viewModel.getFavs()
            val favList = arrayListOf<String>()
            favList.addAll(list)

            val flagList: ArrayList<String> = arrayListOf()
            for (i in 0..favList.size-1){
                val flagUrl = viewModel.getFlagWithCode2(favList[i])
                flagList.add(flagUrl)
            }

            adapter.refresh_data(favList, flagList)
        }

    }

    fun setupNavigation_viewModel(view: View) {
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this).get(CountryHolidaysViewModel::class.java)
    }

    fun observer_live_holidays(){
        viewModel.holidays.observe(viewLifecycleOwner, {



        })
    }

    override fun onFavoriteClick(code2: String, flag: String) {

        if (code2.isNotEmpty()){
            val bundle = bundleOf()
            bundle.putString("code2", code2)
            bundle.putString("flag", flag)
            navController.navigate(R.id.action_homepage_to_favoriteDetail, bundle)
        }

    }

    override fun onStart() {
        super.onStart()
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}