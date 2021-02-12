package com.example.clientapp.presentation.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clientapp.R
import com.example.serverapp.OperationAidl
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

}