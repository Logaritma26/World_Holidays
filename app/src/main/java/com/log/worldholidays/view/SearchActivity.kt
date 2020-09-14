package com.log.worldholidays.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.log.worldholidays.R
import com.log.worldholidays.viewmodel.CountrListViewModel

class SearchActivity : Fragment() {

    private lateinit var viewModel : CountrListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.activity_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CountrListViewModel::class.java)

        observe_country_live_data()
    }

    private fun observe_country_live_data(){

        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->

            countries?.let {
            }

        })

        viewModel.country_error.observe(viewLifecycleOwner, Observer { error->
            error?.let {
            }
        })

        viewModel.country_loading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
            }
        })

    }
}