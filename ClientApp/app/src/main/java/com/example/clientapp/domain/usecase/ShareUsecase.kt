package com.example.clientapp.domain.usecase

import com.example.clientapp.domain.repos.Repo
import com.example.serverapp.OperationDto
import io.reactivex.Single
import javax.inject.Inject

class ShareUsecase @Inject constructor(val apiRepo: Repo) {


     fun addOperation(operation: OperationDto): Long {
        return apiRepo.addOperation(operation)
    }

     fun updateOperation(operation: OperationDto): Int {
         return apiRepo.updateOperation(operation)
    }

     fun getAllOperations(): Single<List<OperationDto>> {
        return apiRepo.getAllOperations()

    }

     fun getLastOperation(): Single<OperationDto> {
        return apiRepo.getLastOperation()
    }

}