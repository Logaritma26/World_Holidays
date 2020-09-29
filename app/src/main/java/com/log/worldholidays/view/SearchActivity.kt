package com.log.worldholidays.view


import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.*
import android.view.animation.AnticipateInterpolator
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.log.worldholidays.R
import com.log.worldholidays.adapter.AdapterBinder
import com.log.worldholidays.adapter.CountryListAdapter
import com.log.worldholidays.viewmodel.CountrListViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.country_list_container.view.*
import kotlin.math.abs


class SearchActivity : Fragment(), AdapterBinder, GestureDetector.OnGestureListener {


    lateinit var navController: NavController
    private lateinit var viewModel: CountrListViewModel
    private lateinit var adapter: CountryListAdapter
    private var SELECTED_REGION = 0
    lateinit var gestureDetector: GestureDetector

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
        setupRecyclerNviewModel()
        observe_country_live_data()


        // things to generate settings/inits etc
        refreshLayout()
        onclickRegions()
        searchview()
        navigation(view)
        onBackPressed()
        gesturePopup()


    }

    private fun gesturePopup() {

        gestureDetector = GestureDetector(activity, this)

        var x1: Float = 0.0f
        var x2: Float = 0.0f
        var y1: Float = 0.0f
        var y2: Float = 0.0f

        val MIN_DISTANCE = 200

        coordinator_details.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                gestureDetector.onTouchEvent(p1)

                if (p1 != null) {
                    when (p1.action) {

                        0 -> {
                            x1 = p1.rawX
                            y1 = p1.rawY
                        }

                        1 -> {
                            x2 = p1.rawX
                            y2 = p1.rawY

                            val valueX = x2 - x1
                            val valueY = y2 - y1


                            if (abs(valueX) > MIN_DISTANCE && abs(valueX) > abs(valueY)) {
                                if (x2 > x1) {
                                    Toast.makeText(activity, "swipe right", Toast.LENGTH_SHORT).show()
                                    /// swipe right
                                } else {
                                    Toast.makeText(activity, "swipe left", Toast.LENGTH_SHORT).show()
                                    /// swipe left
                                }
                            }

                            if (abs(valueY) > MIN_DISTANCE && abs(valueY) > abs(valueX)) {
                                if (y2 > y1) {
                                    hidePopup()
                                } else {
                                    /// swipe up
                                }
                            }

                        }

                    }
                }

                return true
            }

        })


    }

    private fun hidePopup() {
        val constraintLayout: ConstraintLayout = popup_deatils
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintSet.connect(R.id.inDetails,
            ConstraintSet.TOP,
            R.id.guideline_bottom_full,
            ConstraintSet.TOP,
            0)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateInterpolator(1.0f)
        transition.duration = 700

        TransitionManager.beginDelayedTransition(constraintLayout, transition)
        constraintSet.applyTo(constraintLayout)

        constraintLayout_searchView.visibility = View.VISIBLE
        swipeLayout.visibility = View.VISIBLE
    }

    private fun navigation(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun searchview() {
        search_view.setOnSearchClickListener {
            textView_search.visibility = View.GONE

        }

        search_view.setOnCloseListener(object : SearchView.OnCloseListener,
            androidx.appcompat.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                textView_search.visibility = View.VISIBLE
                regions_cards_layout.visibility = View.VISIBLE
                swipeLayout.visibility = View.GONE
                return false
            }

        })

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val newQ = "%$query%"
                    viewModel.getDataForQueryDB(newQ)
                    regions_cards_layout.visibility = View.GONE
                    swipeLayout.visibility = View.VISIBLE
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    val newQ = "%$newText%"
                    viewModel.getDataForQueryDB(newQ)
                    regions_cards_layout.visibility = View.GONE
                    swipeLayout.visibility = View.VISIBLE
                }
                return false
            }

        })

    }

    private fun setupRecyclerNviewModel() {
        viewModel = ViewModelProvider(this).get(CountrListViewModel::class.java)
        viewModel.checkAll()
        adapter = CountryListAdapter(arrayListOf(), viewModel, this)
        country_list_recyclerview.layoutManager = LinearLayoutManager(context)
        country_list_recyclerview.adapter = adapter
    }

    private fun onclickRegions() {

        region_europe.setOnClickListener() {
            SELECTED_REGION = 1
            viewModel.getDataFromRoomDB(SELECTED_REGION)
            regions_cards_layout.visibility = View.GONE
            swipeLayout.visibility = View.VISIBLE
        }

        region_asia.setOnClickListener() {
            SELECTED_REGION = 2
            viewModel.getDataFromRoomDB(SELECTED_REGION)
            regions_cards_layout.visibility = View.GONE
            swipeLayout.visibility = View.VISIBLE
        }

        region_africa.setOnClickListener() {
            SELECTED_REGION = 3
            viewModel.getDataFromRoomDB(SELECTED_REGION)
            regions_cards_layout.visibility = View.GONE
            swipeLayout.visibility = View.VISIBLE
        }

        region_america.setOnClickListener() {
            SELECTED_REGION = 4
            viewModel.getDataFromRoomDB(SELECTED_REGION)
            regions_cards_layout.visibility = View.GONE
            swipeLayout.visibility = View.VISIBLE
        }

        region_oceania.setOnClickListener() {
            SELECTED_REGION = 5
            viewModel.getDataFromRoomDB(SELECTED_REGION)
            regions_cards_layout.visibility = View.GONE
            swipeLayout.visibility = View.VISIBLE
        }

    }

    private fun refreshLayout() {

        swipeLayout.setOnRefreshListener {
            viewModel.getDataFromApi(SELECTED_REGION)
            swipeLayout.isRefreshing = false
        }

    }

    private fun observe_country_live_data() {

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                country_list_recyclerview.visibility = View.VISIBLE
                adapter.refresh_recycler(countries)
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

    private fun onBackPressed() {

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //Handle back event from any fragment

                    if (SELECTED_REGION != 6) {
                        SELECTED_REGION = 6
                        regions_cards_layout.visibility = View.VISIBLE
                        swipeLayout.visibility = View.GONE
                    }

                }
            })

    }

    override fun onCountryClick(view: View) {

        val name: String = view.country_name.text.toString()

        viewModel.singleGetDB(name, inDetails)

        val constraintLayout: ConstraintLayout = popup_deatils
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintSet.connect(R.id.inDetails,
            ConstraintSet.TOP,
            R.id.guideline_top,
            ConstraintSet.TOP,
            0)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateInterpolator(1.0f)
        transition.duration = 800

        TransitionManager.beginDelayedTransition(constraintLayout, transition)
        constraintSet.applyTo(constraintLayout)

        constraintLayout_searchView.visibility = View.GONE
        regions_cards_layout.visibility = View.GONE
        swipeLayout.visibility = View.GONE
    }


    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }


}