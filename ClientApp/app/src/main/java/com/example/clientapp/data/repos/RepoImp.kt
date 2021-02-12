package com.example.clientapp.data.repos

import com.example.clientapp.data.OperationDatabase
import com.example.clientapp.data.mappers.ShareMappper
import com.example.clientapp.domain.repos.Repo
import com.example.serverapp.OperationDto
import io.reactivex.Single
import javax.inject.Inject

class RepoImp @Inject constructor(
    private val operationDatabase: OperationDatabase,
    private val shareMapper: dagger.Lazy<ShareMappper>
) : Repo {

    override fun addOperation(operation: OperationDto): Long {
        return operationDatabase.getOperationDao()
            .addOperation(shareMapper.get().toOperationEntity(operation))
    }

    override fun updateOperation(operation: OperationDto): Int {
        return operationDatabase.getOperationDao()
            .updateOperation(shareMapper.get().toOperationEntity(operation))
    }

    override fun getAllOperations(): Single<List<OperationDto>> {
        return operationDatabase.getOperationDao().getAllOperations().map {
            shareMapper.get().entityListTDtoList(it)
        }

    }

    override fun getLastOperation(): Single<OperationDto> {
        return operationDatabase.getOperationDao().getLastOperation().map {
            shareMapper.get().toOperationDto(it)
        }
    }

}