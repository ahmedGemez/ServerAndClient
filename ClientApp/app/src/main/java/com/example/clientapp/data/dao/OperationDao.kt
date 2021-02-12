package com.example.clientapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.clientapp.data.entities.OperationEntity
import io.reactivex.Single

@Dao
interface OperationDao {

    @Insert
    fun addOperation(operation: OperationEntity) : Long
    @Update
    fun updateOperation(operation: OperationEntity) :Int

    @Query("SELECT * FROM operation ORDER BY id DESC")
    fun getAllOperations() : Single<List<OperationEntity>>

    @Query("SELECT * FROM operation WHERE id = (SELECT MAX(ID)  FROM operation)")
    fun getLastOperation() : Single<OperationEntity>

}