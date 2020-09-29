package com.log.worldholidays.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.log.worldholidays.R
import com.log.worldholidays.fragmetns.home.Homepage
import com.log.worldholidays.fragmetns.settings.Settings
import com.log.worldholidays.util.IOnBackPressed


class MainActivity : AppCompatActivity() {

    fun fill(): ArrayList<String> {

        var array: ArrayList<String> = ArrayList()

        array.add("AD")
        array.add("AL")
        array.add("AR")
        array.add("AT")
        array.add("AU")
        array.add("AX")
        array.add("BB")
        array.add("BE")
        array.add("BG")
        array.add("BJ")
        array.add("BO")
        array.add("BR")
        array.add("BS")
        array.add("BW")
        array.add("BY")
        array.add("BZ")
        array.add("CA")
        array.add("CH")
        array.add("CL")
        array.add("CN")
        array.add("CO")
        array.add("CR")
        array.add("CU")
        array.add("CY")
        array.add("CZ")
        array.add("DE")
        array.add("DK")
        array.add("DO")
        array.add("EC")
        array.add("EE")
        array.add("EG")
        array.add("ES")
        array.add("FI")
        array.add("FO")
        array.add("FR")
        array.add("GA")
        array.add("GB")
        array.add("GD")
        array.add("GL")
        array.add("GM")
        array.add("GR")
        array.add("GT")
        array.add("GY")
        array.add("HN")
        array.add("HR")
        array.add("HT")
        array.add("HU")
        array.add("ID")
        array.add("IE")
        array.add("IM")
        array.add("IS")
        array.add("IT")
        array.add("JE")
        array.add("JM")
        array.add("JP")
        array.add("LI")
        array.add("LS")
        array.add("LT")
        array.add("LU")
        array.add("LV")
        array.add("MA")
        array.add("MC")
        array.add("MD")
        array.add("MG")
        array.add("MK")
        array.add("MN")
        array.add("MT")
        array.add("MX")
        array.add("MZ")
        array.add("NA")
        array.add("NE")
        array.add("NI")
        array.add("NL")
        array.add("NO")
        array.add("NZ")
        array.add("PA")
        array.add("PE")
        array.add("PL")
        array.add("PR")
        array.add("PT")
        array.add("PY")
        array.add("RO")
        array.add("RS")
        array.add("RU")
        array.add("SE")
        array.add("SI")
        array.add("SJ")
        array.add("SK")
        array.add("SM")
        array.add("SR")
        array.add("SV")
        array.add("TN")
        array.add("TR")
        array.add("UA")
        array.add("US")
        array.add("UY")
        array.add("VA")
        array.add("VE")
        array.add("VN")
        array.add("ZA")
        array.add("ZW")

        //DataSingleton.COUNTRY_CODES = array

        for (i in 0 until COUNTRY_CODES.size) {
            MAP.put(COUNTRY_CODES[i], "empty")
        }

        return array

    }

    companion object {
        val COUNTRY_CODES = ArrayList<String>()  // country codes
        val MAP = mutableMapOf<String, String>()  // key : codes   value : country names
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //fill(COUNTRY_CODES)


        //get_country_names()

        bottom_navigationView_stuff()


    }
/*

    private fun get_country_names() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val holder = retrofit.create(CountryApi::class.java)

        for (i in 0 until COUNTRY_CODES.size){
            val code : String = COUNTRY_CODES[i]

            val url : String = "/rest/v2/alpha/$code?fields=name"
            holder.get_text(url)?.enqueue(object : Callback<ModelCountry?> {
                override fun onResponse(
                    call: Call<ModelCountry?>?,
                    response: Response<ModelCountry?>?,
                ) {
                    val info: ModelCountry? = response?.body()
                    info?.get_name()?.let {
                        MAP[COUNTRY_CODES[i]] = it
                    }
                }

                override fun onFailure(call: Call<ModelCountry?>?, t: Throwable?) {
                    Log.d("get country names", "onFailure: " + (t?.message ?: String))
                }
            })

        }

    }

*/

    //   Bottom Navigation View
    private fun bottom_navigationView_stuff() {

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bar_bottom)
        val navController = findNavController(R.id.nav_host_fragment_container)

        bottomNavigationView.setupWithNavController(navController)

        /*var bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bar_bottom)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)

        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_container, Homepage())
            .commit()
        bottomNavigationView.setSelectedItemId(R.id.homepage)*/

    }

    /*private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selected_fragment: Fragment? = null
            when (item.getItemId()) {
                R.id.homepage -> selected_fragment = Homepage()
                R.id.search -> selected_fragment = SearchActivity()
                R.id.settings -> selected_fragment = Settings()
            }
            if (selected_fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container,
                    selected_fragment).commit()
            }
            true
        }
*/



/*
    override fun onBackPressed() {

        val fragment = this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)

        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            if (it) {
                Toast.makeText(this, "asdfasdfaf", Toast.LENGTH_SHORT).show()
                super.onBackPressed()
            }
        }
    }*/

}