package com.example.clientapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.clientapp.data.dao.OperationDao
import com.example.clientapp.data.entities.OperationEntity


@Database(entities = [OperationEntity::class], version = 1)
abstract class OperationDatabase : RoomDatabase() {
    abstract fun getOperationDao(): OperationDao

    companion object{
       var DB_NAME:String = "Operation"
    }
}