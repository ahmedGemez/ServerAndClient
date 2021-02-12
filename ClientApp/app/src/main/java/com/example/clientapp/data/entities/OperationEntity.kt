package com.example.clientapp.data.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "operation")
data class OperationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "first_num")
    var firstNum: Int = 0,
    @ColumnInfo(name = "sec_num")
    var secNum: Int = 0,
    @ColumnInfo(name = "result")
    var result: String? = null,
    @ColumnInfo(name = "type")
    var type: String? = null
)
