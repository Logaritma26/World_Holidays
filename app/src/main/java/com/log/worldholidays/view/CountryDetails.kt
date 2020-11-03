package com.log.worldholidays.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.log.worldholidays.R
import com.log.worldholidays.adapter.BordersAdapter
import com.log.worldholidays.util.glide_image
import com.log.worldholidays.viewmodel.CountryHolidaysViewModel
import com.log.worldholidays.viewmodel.CountryListViewModel
import kotlinx.android.synthetic.main.country_list_container.view.*
import kotlinx.android.synthetic.main.fragment_country_details.*
import kotlinx.android.synthetic.main.fragment_country_details.borders_viewpager
import kotlinx.android.synthetic.main.fragment_country_details.details_area
import kotlinx.android.synthetic.main.fragment_country_details.details_capital
import kotlinx.android.synthetic.main.fragment_country_details.details_population
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class CountryDetails : Fragment(), BordersAdapter.DetailClick {

    private var code3: String = ""
    private var source: Int = 0

    lateinit var navController: NavController

    private lateinit var viewModel: CountryListViewModel
    private lateinit var holidaysViewModel: CountryHolidaysViewModel

    private val adapter_borders = BordersAdapter(arrayListOf(), arrayListOf(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code3 = requireArguments().getString("code3").toString()
            source = requireArguments().getInt("source")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init_viewModels()
        setup_layout()
        onClick_goHolidays()
    }

    fun setup_layout() {
        CoroutineScope(Dispatchers.Main).launch {
            val country = viewModel.getCountry_with_code3(code3)

            glide_image(flag_image, country.flag)
            details_Name.text = country.name
            details_population.text = country.population
            details_capital.text = country.capital
            details_area.text = country.area

            init_recycler_borders()
        }
    }

    fun init_recycler_borders() {
        borders_viewpager.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,
            false)
        borders_viewpager.adapter = adapter_borders

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(borders_viewpager)

        viewModel.getCountry_with_code3(code3, detail_layout, adapter_borders)
    }

    fun init_viewModels() {

        holidaysViewModel = ViewModelProvider(this).get(CountryHolidaysViewModel::class.java)
        viewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)
    }

    fun onClick_goHolidays() {
        go_holiday_button.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                val country = viewModel.getCountry_with_code3(code3)

                val flag = country.flag
                val code2 = country.alpha2Code
                val bundle = bundleOf()
                bundle.putString("code2", code2)
                bundle.putString("flag", flag)
                navController.navigate(R.id.action_countryDetails_to_country_holiday_weekends_details, bundle)

            }

        }
    }
    override fun onDetailsClick(view: View, code3: String) {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.Main).async {
                val code2: String = viewModel.convert_code3_to_code2(code3)

                if (!holidaysViewModel.check_if_downloaded(code2)){
                    val year: Int = Calendar.getInstance().get(Calendar.YEAR)
                    holidaysViewModel.getHolidaysOfCountryApi(code2, year)
                    holidaysViewModel.getWeekendOfCountryApi(code2, year)
                }


            }.await()

            val bundle = bundleOf()
            bundle.putString("code3", code3)
            bundle.putInt("source", source)

            if (source == 1) {
                navController.navigate(R.id.action_countryDetails_self, bundle)
            } else if (source == 2) {
                navController.navigate(R.id.action_countryDetails_self2, bundle)
            } else {
                Toast.makeText(activity, "There is an error occured !", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onStart() {
        super.onStart()
        val view = activity?.currentFocus
        view?.let { v ->
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}