package com.example.clientapp.presentation.ui.homefragment

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.clientapp.domain.usecase.ShareUsecase
import com.example.clientapp.presentation.core.OperationType
import com.example.mostpopulararticlestask.presentation.core.SingleLiveEvent
import com.example.serverapp.OperationDto
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(val shareUseCase: ShareUsecase, val context: Context) :
    ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    var types: List<String> = mutableListOf(OperationType.Add.type, OperationType.Sub.type,OperationType.Mul.type , OperationType.Div.type)
    val first = MutableLiveData<String>()
    val second = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val typeItemPosition = MutableLiveData<Int>()

    val sucessAdd: MutableLiveData<Long> = MutableLiveData<Long>()
    val lastItem = MutableLiveData<OperationDto>()
    val items = MutableLiveData<List<OperationDto>>()

    val locationEvent = SingleLiveEvent<Boolean>()


    val typeValue: LiveData<String> = typeItemPosition.map {
        types.get(it)
    }




    fun calculate() {
        val firstNum = first.value
        val secondNum = second.value
        val time = time.value
        if (firstNum == null || secondNum == null || time == null) {
            Toast.makeText(context, "please fill all fields", Toast.LENGTH_LONG).show()
            return
        }

        var operationDto = OperationDto()
        operationDto.apply {
            id = "0"
            this.firstNum = firstNum.toInt()
            secNum = secondNum.toInt()
            type = types.get(typeItemPosition.value ?: 0)
            result = "pending"
        }
        addOperation(operationDto)
    }

    fun location(){
        locationEvent.value = true;
    }


    // local data
    fun addOperation(Operation: OperationDto) {
        val disposable: Disposable = Observable.fromCallable(
            { shareUseCase.addOperation(Operation) }
        ).subscribeOn(Schedulers.io())
            .doOnError(Consumer { throwable: Throwable ->
                Log.e("addOperationError", "Throwable " + throwable.message)
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sucessAdd.value = it
                getAllOperations()
            }, {

            }
            )
        compositeDisposable.add(disposable)
    }

    fun updateOperation(Operation: OperationDto) {
        val disposable: Disposable = Observable.fromCallable(
            { shareUseCase.updateOperation(Operation) }
        ).subscribeOn(Schedulers.io())
            .doOnError(Consumer { throwable: Throwable ->
                Log.e("updateOperationError", "Throwable " + throwable.message)
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getAllOperations()
                Log.d("dvdvdv", "sucesssss$it")
            }, {

            }
            )
        compositeDisposable.add(disposable)
    }


    fun getAllOperations() {
        val disposable: Disposable = shareUseCase.getAllOperations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Consumer { throwable: Throwable ->
                Log.e("getAllOperationsError", "Throwable " + throwable.message)
            })
            .subscribe({
                items.value = it
            }, {

            }
            )
        compositeDisposable.add(disposable)
    }

    fun getLastOperation() {
        val disposable: Disposable = shareUseCase.getLastOperation()
            .subscribeOn(Schedulers.io())
            .doOnError(Consumer { throwable: Throwable ->
                Log.e("getLastOperationError", "Throwable " + throwable.message)
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                lastItem.value = it
            }, {

            }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed && compositeDisposable != null) {
            compositeDisposable.dispose()
        }
    }
}