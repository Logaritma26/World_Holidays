package com.log.worldholidays

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.log.worldholidays.adjust.AddItem
import com.log.worldholidays.adjust.CountryNames
import com.log.worldholidays.adjust.JsonPlaceHolder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    fun fill(array: ArrayList<String>) {

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

        for (i in 0 until COUNTRY_CODES.size){
            MAP.put(COUNTRY_CODES[i], "empty")
        }
    }

    companion object {
        val COUNTRY_CODES = ArrayList<String>()  // country codes
        val MAP = mutableMapOf<String, String>()  // key : codes   value : country names
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fill(COUNTRY_CODES)

        floating_action_button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddItem::class.java)
            startActivity(intent)
        }


        get_country_names()


    }

    private fun get_country_names() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val holder = retrofit.create(JsonPlaceHolder::class.java)

        for (i in 0 until COUNTRY_CODES.size){
            val code : String = COUNTRY_CODES[i]

            val url : String = "/rest/v2/alpha/$code?fields=name"
            holder.get_text(url)?.enqueue(object : Callback<CountryNames?> {
                override fun onResponse(
                    call: Call<CountryNames?>?,
                    response: Response<CountryNames?>?
                ) {
                    val info : CountryNames? = response?.body()
                    info?.get_name()?.let {
                        MAP[COUNTRY_CODES[i]] = it
                    }
                }

                override fun onFailure(call: Call<CountryNames?>?, t: Throwable?) {
                    Log.d("get country names", "onFailure: " + (t?.message ?: String))
                }
            })

        }

    }


}