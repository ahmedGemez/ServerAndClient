package com.example.serverapp


import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import androidx.annotation.Nullable

class ProcessService : Service() {
    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    private val mBinder: OperationAidl.Stub = object : OperationAidl.Stub() {

        @Throws(RemoteException::class)
        override fun calculateNumbers(operationDto: OperationDto, seconds: Long): OperationDto {

            try {
                Thread.sleep(seconds * 1000)
            } catch (e: Exception) {
            }
            val operationDtoResut = OperationDto()
            operationDtoResut.apply {
                id = operationDto.id
                firstNum = operationDto.firstNum
                secNum = operationDto.secNum
                type = operationDto.type
                when(type){
                    OperationType.Add.type ->  result =  (operationDto.firstNum + operationDto.secNum).toString()
                    OperationType.Div.type ->  result =  (operationDto.firstNum / operationDto.secNum).toString()
                    OperationType.Mul.type ->  result =  (operationDto.firstNum * operationDto.secNum).toString()
                    OperationType.Sub.type ->  result =  (operationDto.firstNum - operationDto.secNum).toString()

                }

            }


            return operationDtoResut
        }

    }
}