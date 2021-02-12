// processInterfac.aidl
package com.example.serverapp;
import com.example.serverapp.OperationDto ;

// Declare any non-default types here with import statements

interface OperationAidl {

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     OperationDto calculateNumbers(inout  OperationDto operationDto, long seconds);//2 argument method to add
   /* void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);*/
}