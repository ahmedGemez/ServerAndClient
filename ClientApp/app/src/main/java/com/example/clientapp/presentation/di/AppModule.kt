package com.example.clientapp.presentation.di

import android.app.Application
import android.content.Context

import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context

}