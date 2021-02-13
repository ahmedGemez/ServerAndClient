package com.example.clientapp.presentation.ui.homefragment

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.clientapp.domain.usecase.ShareUsecase
import com.example.clientapp.presentation.core.OperationType
import com.example.serverapp.OperationDto
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Callable

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest{

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var shareUseCase: ShareUsecase
    @Mock
    private lateinit var contextMock: Context
    private lateinit var viewModel: HomeViewModel
    private lateinit var operationDto: OperationDto

    @Before
    fun setupTest() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _: Callable<Scheduler?>? -> Schedulers.trampoline() }
        operationDto = OperationDto().apply {
            id = "0"
            this.firstNum = 5
            secNum = 6
            type = OperationType.Add.type
            result = "pending"
        }
        viewModel = HomeViewModel(shareUseCase, contextMock )
    }


    @Test
    fun testAddOperation() {
        Mockito.doReturn(1.toLong()).`when`(shareUseCase).addOperation(operationDto)
        viewModel.addOperation(operationDto)
        val value = viewModel.sucessAdd.getOrAwaitValue()
        Assert.assertEquals(1,value)
    }

    @Test
    fun testupdateAndGetAllOperation() {
        Mockito.doReturn(Single.just(listOf(operationDto))).`when`(shareUseCase).getAllOperations()
        viewModel.updateOperation(operationDto)
        val value = viewModel.items.getOrAwaitValue()
        Assert.assertEquals(1,value.size)
    }

    @Test
    fun testGetLastOperation() {
        Mockito.doReturn(Single.just(operationDto)).`when`(shareUseCase).getLastOperation()
        viewModel.getLastOperation()
        val value = viewModel.lastItem.getOrAwaitValue()
        Assert.assertEquals(5,value.firstNum)
    }


}

