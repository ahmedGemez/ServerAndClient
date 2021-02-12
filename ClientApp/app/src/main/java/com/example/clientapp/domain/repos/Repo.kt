package com.example.clientapp.domain.repos


import com.example.clientapp.data.entities.OperationEntity
import com.example.serverapp.OperationDto
import io.reactivex.Single

interface Repo {
    fun addOperation(operation: OperationDto) : Long
    fun updateOperation(operation: OperationDto) :Int
    fun getAllOperations() : Single<List<OperationDto>>
    fun getLastOperation() : Single<OperationDto>

}