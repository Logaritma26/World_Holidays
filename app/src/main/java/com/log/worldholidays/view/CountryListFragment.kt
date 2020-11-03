package com.log.worldholidays.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat.setBackground
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.log.worldholidays.R
import com.log.worldholidays.adapter.AdapterBinder
import com.log.worldholidays.adapter.CountryListAdapter
import com.log.worldholidays.viewmodel.CountryHolidaysViewModel
import com.log.worldholidays.viewmodel.CountryListViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.country_list_container.view.*
import kotlinx.android.synthetic.main.fragment_country_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CountryListFragment : Fragment(), AdapterBinder {

    private var POPULATION_SORT = 0
    private var AREA_SORT = 0
    private var IS_SEARCH_ACTIVE = false
    private var SELECTED_REGION = 0

    lateinit var navController: NavController

    private lateinit var viewModel: CountryListViewModel
    private lateinit var holidaysViewModel: CountryHolidaysViewModel

    private lateinit var adapter: CountryListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SELECTED_REGION = requireArguments().getInt("selected_region")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewModels_Set()
        navigation(view)
        setupRecycler()
        searchview()
        refreshLayout()
        observe_country_live_data()
        onSortClick()

        Handler(Looper.getMainLooper()).postDelayed({
            run {
                if (adapter.country_list.isEmpty()){
                    viewModel.getDataFromRoomDB(SELECTED_REGION)
                }
            }
        }, 400)

    }


    private fun ViewModels_Set() {
        holidaysViewModel = ViewModelProvider(this).get(CountryHolidaysViewModel::class.java)
        viewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)

        //
    }

    private fun navigation(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun refreshLayout() {
        swipeLayout.setOnRefreshListener {
            viewModel.getDataFromApi(SELECTED_REGION)
            swipeLayout.isRefreshing = false
        }
    }

    private fun setupRecycler() {
        adapter = CountryListAdapter(arrayListOf(), viewModel, this, holidaysViewModel)
        country_list_recyclerview.layoutManager = LinearLayoutManager(context)
        country_list_recyclerview.adapter = adapter
    }

    private fun searchview() {

        search_view.setOnSearchClickListener {
            IS_SEARCH_ACTIVE = true
            textView_search.visibility = View.GONE
        }

        search_view.setOnCloseListener(object : SearchView.OnCloseListener,
            androidx.appcompat.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                textView_search.visibility = View.VISIBLE
                IS_SEARCH_ACTIVE = false
                return false
            }

        })

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val newQ = "%$query%"
                    viewModel.getDataForQueryDB(newQ, SELECTED_REGION)
                    swipeLayout.visibility = View.VISIBLE
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    val newQ = "%$newText%"
                    viewModel.getDataForQueryDB(newQ, SELECTED_REGION)
                    swipeLayout.visibility = View.VISIBLE
                }
                return false
            }

        })

    }

    private fun observe_country_live_data() {

        viewModel.countries.observe(viewLifecycleOwner, { countries ->
            countries?.let {
                country_list_recyclerview.visibility = View.VISIBLE
                adapter.renew_data(countries)
                error_image.visibility = View.GONE
                error_text.visibility = View.GONE
            }
        })

        viewModel.country_error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    country_list_recyclerview.visibility = View.GONE
                    error_image.visibility = View.VISIBLE
                    error_text.visibility = View.VISIBLE
                }

            }
        })

        viewModel.country_loading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
            }
        })

    }

    override fun onCountryClick(view: View) {
        val code2 = view.code2.text.toString()

        CoroutineScope(Dispatchers.Main).launch {
            if (!holidaysViewModel.check_if_downloaded(code2)){
                val year: Int = Calendar.getInstance().get(Calendar.YEAR)
                holidaysViewModel.getHolidaysOfCountryApi(code2, year)
                holidaysViewModel.getWeekendOfCountryApi(code2, year)
            }


            val bundle = bundleOf()
            bundle.putString("code3", view.code3.text.toString())
            bundle.putInt("source", 1)

            navController.navigate(R.id.action_countryListFragment_to_countryDetails, bundle)




        }
        /*
        if (view.is_fav.text.toString().toInt() == 1){
            Toast.makeText(activity, "favorite", Toast.LENGTH_SHORT).show()
        }
        else if (view.is_fav.text.toString().toInt() == 0){
            Toast.makeText(activity, "not favorite", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(activity, "none", Toast.LENGTH_SHORT).show()
        }
*/

    }

    private fun onSortClick() {

        population_sort.setOnClickListener {
            AREA_SORT = 0
            area_sort_image.setBackgroundResource(R.drawable.compare_arrows)

            if (POPULATION_SORT == 0){
                POPULATION_SORT++
                population_sort_image.setBackgroundResource(R.drawable.arrow_asc)
                viewModel.getDataSortPopulation(SELECTED_REGION, POPULATION_SORT)
            } else if (POPULATION_SORT == 1) {
                POPULATION_SORT++
                population_sort_image.setBackgroundResource(R.drawable.arrow_desc)
                viewModel.getDataSortPopulation(SELECTED_REGION, POPULATION_SORT)
            } else {
                POPULATION_SORT = 0
                population_sort_image.setBackgroundResource(R.drawable.compare_arrows)
                viewModel.getDataFromRoomDB(SELECTED_REGION)
            }
        }

        area_sort.setOnClickListener {
            POPULATION_SORT = 0
            population_sort_image.setBackgroundResource(R.drawable.compare_arrows)

            if (AREA_SORT == 0){
                AREA_SORT++
                area_sort_image.setBackgroundResource(R.drawable.arrow_asc)
                viewModel.getDataSortArea(SELECTED_REGION, AREA_SORT)
            } else if (AREA_SORT == 1) {
                AREA_SORT++
                area_sort_image.setBackgroundResource(R.drawable.arrow_desc)
                viewModel.getDataSortArea(SELECTED_REGION, AREA_SORT)
            } else {
                AREA_SORT = 0
                area_sort_image.setBackgroundResource(R.drawable.compare_arrows)
                viewModel.getDataFromRoomDB(SELECTED_REGION)
            }
        }



    }


    override fun onStart() {
        super.onStart()
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
        if (!search_view.isIconified){
            textView_search.visibility = View.GONE
        }
    }
}