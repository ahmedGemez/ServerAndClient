package com.example.clientapp.data.mappers

import com.example.clientapp.data.entities.OperationEntity
import com.example.serverapp.OperationDto
import javax.inject.Inject


class ShareMappper  @Inject constructor() {


    fun toOperationDto(operationEntity: OperationEntity): OperationDto {
     val operationDto =  OperationDto()
        operationDto.apply {
           id =operationEntity.id.toString()
            firstNum = operationEntity.firstNum
            secNum = operationEntity.secNum
            result = operationEntity.result
            type = operationEntity.type

        }
        return operationDto
    }

    fun toOperationEntity(operationDto: OperationDto): OperationEntity {

        return OperationEntity(
            operationDto.id?.toLong()?:0,
            operationDto.firstNum,
            operationDto.secNum,
            operationDto.result,
            operationDto.type
        )
    }


    fun entityListTDtoList(allOperationEntity: List<OperationEntity>): List<OperationDto> {
        val resultList = mutableListOf<OperationDto>()
        for(item in allOperationEntity){
            resultList.add(toOperationDto(item))
        }
        return resultList
    }

}