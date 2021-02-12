package com.example.clientapp.presentation.di

import android.app.Application
import com.example.clientapp.data.di.DatabaseMoule
import com.example.clientapp.data.di.RepoMoule
import com.example.clientapp.presentation.ui.homefragment.HomeFragment

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseMoule::class,
        AppModule::class,
        ViewModelModule::class,
        RepoMoule::class
    ]
)
interface AppComponent  {
    fun inject(homeFragment: HomeFragment)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setContext(app:Application ): Builder
        fun build(): AppComponent
    }

}
