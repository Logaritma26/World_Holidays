package com.log.worldholidays.view


import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.*
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.log.worldholidays.R
import com.log.worldholidays.adapter.BordersAdapter
import com.log.worldholidays.model.CountryDB
import com.log.worldholidays.viewmodel.CountryListViewModel
import com.log.worldholidays.viewmodel.CountryHolidaysViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlin.math.abs


class SearchActivity : Fragment() {

    private var SELECTED_REGION = 0

    private lateinit var viewModel: CountryListViewModel
    private lateinit var holidaysViewModel: CountryHolidaysViewModel

    lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.activity_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // things to run instantly // about data
        setup_viewModel()


        // things to generate settings/inits etc

        onclickRegions()
        navigation(view)
    }

    private fun navigation(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun setup_viewModel() {
        holidaysViewModel = ViewModelProvider(this).get(CountryHolidaysViewModel::class.java)
        viewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)
        viewModel.checkAll()

    }

    private fun onclickRegions() {

        region_europe.setOnClickListener {
            SELECTED_REGION = 1
            val bundle = bundleOf()
            bundle.putInt("selected_region", SELECTED_REGION)
            navController.navigate(R.id.action_searchActivity_to_countryListFragment, bundle)
        }

        region_asia.setOnClickListener {
            SELECTED_REGION = 2
            val bundle = bundleOf()
            bundle.putInt("selected_region", SELECTED_REGION)
            navController.navigate(R.id.action_searchActivity_to_countryListFragment, bundle)
        }

        region_africa.setOnClickListener {
            SELECTED_REGION = 3
            val bundle = bundleOf()
            bundle.putInt("selected_region", SELECTED_REGION)
            navController.navigate(R.id.action_searchActivity_to_countryListFragment, bundle)
        }

        region_america.setOnClickListener {
            SELECTED_REGION = 4
            val bundle = bundleOf()
            bundle.putInt("selected_region", SELECTED_REGION)
            navController.navigate(R.id.action_searchActivity_to_countryListFragment, bundle)
        }

        region_oceania.setOnClickListener {
            SELECTED_REGION = 5
            val bundle = bundleOf()
            bundle.putInt("selected_region", SELECTED_REGION)
            navController.navigate(R.id.action_searchActivity_to_countryListFragment, bundle)
        }

        region_all.setOnClickListener {
            SELECTED_REGION = 0
            val bundle = bundleOf()
            bundle.putInt("selected_region", SELECTED_REGION)
            navController.navigate(R.id.action_searchActivity_to_countryListFragment, bundle)
        }
    }

}