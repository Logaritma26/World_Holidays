package com.log.worldholidays


class DataSingleton {

    private var instance: DataSingleton? = null

    private fun Singleton() {}

    fun getInstance(): DataSingleton? {
        if (instance == null) {
            synchronized(DataSingleton::class.java) {
                if (instance == null) {
                    instance = DataSingleton()
                }
            }
        }
        return instance
    }




}