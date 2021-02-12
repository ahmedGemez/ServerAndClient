package com.example.clientapp.presentation

import android.app.Application
import com.example.clientapp.presentation.di.AppComponent
import com.example.clientapp.presentation.di.DaggerAppComponent

class ClientApp : Application() {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent =  DaggerAppComponent.builder().setContext(this).build()
    }

    public fun getAppComponent():AppComponent{
        return appComponent;
    }
}