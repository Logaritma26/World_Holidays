package com.log.worldholidays.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.log.worldholidays.model.CountryDB
import com.log.worldholidays.model.HolidayDB
import com.log.worldholidays.model.WeekendDB
import com.log.worldholidays.services.Country.CountryDatabase
import com.log.worldholidays.services.Holiday.HolidayApiService
import com.log.worldholidays.services.Holiday.HolidayDatabase
import com.log.worldholidays.services.Weekend.WeekendApiService
import com.log.worldholidays.services.Weekend.WeekendDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CountryHolidaysViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private val holidaysApiService = HolidayApiService()
    private val weekendsApiService = WeekendApiService()


    val holidays = MutableLiveData<List<HolidayDB>>()
    val holiday_error = MutableLiveData<Boolean>()
    val holiday_loading = MutableLiveData<Boolean>()

    suspend fun getAllHolidays() = async {

        val dao = HolidayDatabase(getApplication()).holidayDao()
        val list = dao.getAllHolidays()

        return@async list
    }.await()

    suspend fun getHolidaysOfCountryApi(code2: String, year: Int) = async {
        holiday_loading.value = true
        holiday_error.value = false

        disposable.add(
            holidaysApiService.getHolidays(code2, year)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<HolidayDB>>() {
                    override fun onSuccess(t: List<HolidayDB>?) {
                        t?.let {
                            storeHolidaysDataToRoom(it)
                        }
                    }
                    override fun onError(e: Throwable?) {
                        holiday_error.value = true
                        holiday_loading.value = false
                        e?.printStackTrace()
                    }
                })
        )
    }.await()


    fun storeHolidaysDataToRoom(list: List<HolidayDB>) {

        launch {

            val dao = HolidayDatabase(getApplication()).holidayDao()
            val oldList = dao.getAllHolidays()
            val newList = arrayListOf<HolidayDB>()

            //newList.addAll(oldList)
            newList.addAll(list)

            val sizeOfOld = oldList.size
            for (i in 0 until newList.size - 1) {
                newList[i].uuid = sizeOfOld + i + 1
            }

            dao.insertAll(*newList.toTypedArray())


            /*dao.deleteAllHolidays()
            val shouldEmpty = dao.getAllHolidays()
            if (shouldEmpty.isEmpty()) {
                for (i in 0..newList.size - 1) {
                    newList[i].uuid = i + 1
                }

                dao.insertAll(*newList.toTypedArray())
                commitChangesHoliday(newList)
            }
*/
        }

    }

    fun changeFav(
        boolean: Boolean,
        code: String,
    ) {

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.updateFav_withCode2(code, boolean)

        }

    }


    suspend fun check_if_downloaded(code2: String) = async {
        val holidays = getHolidaysWithCode(code2)
        val weekends = getWeekendsWithCode(code2)

        return@async holidays.isNotEmpty() && weekends.isNotEmpty()
    }.await()

    suspend fun getFavs() = async {

        val dao = CountryDatabase(getApplication()).countryDao()
        val listShown: List<String> = dao.getFavs()

        return@async listShown
    }.await()

    suspend fun getFlagWithCode2(alphaCode2: String) = async {
        val dao = CountryDatabase(getApplication()).countryDao()
        val flag_url = dao.getFlagWithCode2(alphaCode2)


        return@async flag_url
    }.await()

    suspend fun getHolidaysWithCode(code2: String) = async {
        val dao = HolidayDatabase(getApplication()).holidayDao()
        val result = dao.getHolidaysWithCode(code2)
        return@async result
    }.await()

    suspend fun getDistinctCountries() = async {
        val dao = HolidayDatabase(getApplication()).holidayDao()
        val result = dao.distinct_countries()
        return@async result
    }.await()

/*
    fun deleteHolidayDataOfCountry(country: String) {
        launch {
            val dao = HolidayDatabase(getApplication()).holidayDao()
            dao.deleteHolidaysOfCountry(country)
        }
    }*/

    fun commitChangesHoliday(value: List<HolidayDB>) {
        holidays.value = value
        holiday_error.value = false
        holiday_loading.value = false
    }


    suspend fun getWeekendOfCountryApi(code2: String, year: Int) = async {
        disposable.add(
            weekendsApiService.getWeekends(code2, year)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<WeekendDB>>() {
                    override fun onSuccess(t: List<WeekendDB>?) {
                        if (t != null) {
                            for (i in 0..t.size - 1) {
                                t[i].alpha2Code = code2
                            }
                            storeWeekendsDataToRoom(t)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }

                })
        )
    }.await()

    fun storeWeekendsDataToRoom(list: List<WeekendDB>) {
        launch {
            val dao = WeekendDatabase(getApplication()).weekendDao()
            val oldList = dao.getAllWeekends()

            val newList = arrayListOf<WeekendDB>()
            //newList.addAll(oldList)
            newList.addAll(list)

            val sizeOfOld = oldList.size
            for (i in 0 until newList.size) {
                newList[i].uuid = sizeOfOld + i + 1
            }

            dao.insertAll(*newList.toTypedArray())



            /*dao.deleteAllWeekends()
            val shouldEmpty = dao.getAllWeekends()

            if (shouldEmpty.isEmpty()) {
                for (i in 0..newList.size - 1) {
                    newList[i].uuid = i + 1
                }

                dao.insertAll(*newList.toTypedArray())
            }*/
        }
    }

   /* fun deleteWeekendDataOfCountry(code2: String) {
        launch {
            val dao = WeekendDatabase(getApplication()).weekendDao()
            dao.deleteWeekendsOfCountry(code2)
        }
    }*/

    suspend fun getDistinctCountries_Weekend() = async {
        val dao = WeekendDatabase(getApplication()).weekendDao()
        val result = dao.distinct_countries()
        return@async result
    }.await()

    suspend fun getWeekendsWithCode(code2: String) = async {
        val dao = WeekendDatabase(getApplication()).weekendDao()
        val result = dao.getWeekendsWithCode(code2)
        return@async result
    }.await()


    suspend fun getCountryWithCode2(code2: String) = async {
        val dao = CountryDatabase(getApplication()).countryDao()
        val country: CountryDB = dao.getFromAlpha2(code2)
        return@async country
    }.await()

    suspend fun convert_code2_to_code3(code2: String) = async {
        val dao = CountryDatabase(getApplication()).countryDao()
        val code3: String = dao.convert_Code2_to_Code3(code2)

        return@async code3
    }.await()


}