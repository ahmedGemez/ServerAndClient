package com.example.serverapp

import android.os.Parcel
import android.os.Parcelable

//@Parcelize
 class OperationDto() : Parcelable {

    var id: String? =""
    var firstNum:Int = 0
    var secNum:Int = 0
    var result: String? = ""
    var type: String? = ""




    private constructor(inParcel: Parcel) : this() {
        readFromParcel(inParcel)
    }

     fun readFromParcel(parcel: Parcel) {
        id = parcel.readString()
        firstNum = parcel.readInt()
        secNum = parcel.readInt()
       result =  parcel.readString()
        type =parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(firstNum)
        parcel.writeInt(secNum)
        parcel.writeString(result)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OperationDto> {
        override fun createFromParcel(parcel: Parcel): OperationDto {
            return OperationDto(parcel)
        }

        override fun newArray(size: Int): Array<OperationDto?> {
            return arrayOfNulls(size)
        }
    }
}


