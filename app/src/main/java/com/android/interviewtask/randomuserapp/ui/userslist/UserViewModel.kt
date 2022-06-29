package com.android.interviewtask.randomuserapp.ui.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.interviewtask.randomuserapp.rest.RandomUserRepository
import com.android.interviewtask.randomuserapp.utils.UIState
import com.android.interviewtask.randomuserapp.utils.getOutputData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class UserViewModel (private val randomUserRepository: RandomUserRepository,
                     private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
                     private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)
) : CoroutineScope by coroutineScope, ViewModel(){

    private val _usersLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING())
    val usersLiveData: LiveData<UIState> get() = _usersLiveData

    fun subscribeToUsersList() {
        _usersLiveData.postValue(UIState.LOADING())
        collectUsersList()
        launch {
            randomUserRepository.getUsersList()
        }
    }

    private fun collectUsersList() {
        launch {
            /******
             *  ! Important - Before start testing please uncomment the testing codes.
             */
            //_usersLiveData.postValue(UIState.SUCCESS(getOutputData()))

            /******
             *  ! Important - Before start testing please comment the testing codes.
             */
            randomUserRepository.userresponseFlow.collect { uiState ->
                when(uiState) {
                    is UIState.LOADING -> { _usersLiveData.postValue(uiState) }
                    is UIState.SUCCESS -> { _usersLiveData.postValue(uiState) }
                    is UIState.ERROR -> { _usersLiveData.postValue(uiState) }
                }
            }
        }
    }
}