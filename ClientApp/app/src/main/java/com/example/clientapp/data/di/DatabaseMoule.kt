package com.example.clientapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.clientapp.data.OperationDatabase
import com.example.clientapp.data.dao.OperationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseMoule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): OperationDatabase {
        return Room
            .databaseBuilder(application, OperationDatabase::class.java, OperationDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideUserDao(operationDatabase: OperationDatabase): OperationDao {
        return operationDatabase.getOperationDao()
    }

}