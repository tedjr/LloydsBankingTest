package com.android.interviewtask.randomuserapp.rest

import com.android.interviewtask.randomuserapp.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface RandomUserRepository {
    val userresponseFlow: StateFlow<UIState>
    suspend fun getUsersList()
}

class RandomUserRepositoryImpl(
    private val randomUserApi: RandomUserApi
):RandomUserRepository {
    private val _userresponseFlow: MutableStateFlow<UIState> = MutableStateFlow(UIState.LOADING())

    override val userresponseFlow: StateFlow<UIState>
        get() = _userresponseFlow

    override suspend fun getUsersList() {
        try {
            val response = randomUserApi.usersList()

            if (response.isSuccessful) {
                response.body()?.let {
                    _userresponseFlow.value = UIState.SUCCESS(it)
                } ?: run {
                    _userresponseFlow.value = UIState.ERROR(IllegalStateException("User details are coming as null!"))
                }
            } else {
                _userresponseFlow.value = UIState.ERROR(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            _userresponseFlow.value = UIState.ERROR(e)
        }
    }
}

