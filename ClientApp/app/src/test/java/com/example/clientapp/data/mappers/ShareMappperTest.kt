package com.example.clientapp.data.mappers

import com.example.clientapp.data.entities.OperationEntity
import com.example.clientapp.presentation.core.OperationType
import com.example.serverapp.OperationDto
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShareMappperTest{

    private lateinit var operationDto: OperationDto
    private lateinit var operationEntity: OperationEntity
    private lateinit var shareMappper: ShareMappper
    @Before
    fun setupTest() {
        shareMappper = ShareMappper()
        operationDto = OperationDto().apply {
            id = "0"
            this.firstNum = 5
            secNum = 6
            type = OperationType.Add.type
            result = "pending"
        }
        operationEntity = OperationEntity().apply {
            id = 0
            this.firstNum = 5
            secNum = 6
            type = OperationType.Add.type
            result = "pending"
        }

    }


    @Test
    fun testToOperationDto() {
        val result = shareMappper.toOperationDto(operationEntity)
        Assert.assertEquals(operationDto.type,result.type)
    }

    @Test
    fun testToOperationEntity() {
        val result = shareMappper.toOperationEntity(operationDto)
        Assert.assertEquals(operationEntity.type,result.type)
    }

}