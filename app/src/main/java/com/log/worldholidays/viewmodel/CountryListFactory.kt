package com.log.worldholidays.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CountryListFactory(val application: Application) : ViewModelProvider.NewInstanceFactory() {
/*

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java).newInstance(application)
    }
*/

    /*verride fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountrListViewModel(application) as T
    } */

}