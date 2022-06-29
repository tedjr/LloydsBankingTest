package com.android.interviewtask.randomuserapp.rest

import com.android.interviewtask.randomuserapp.model.UsersList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RandomUserApi {

    @GET("api")
    suspend fun usersList(
        @Query(QUERY_RESULTS)results: String="25"
    ): Response<UsersList>


    companion object {
        const val BASE_URL = "https://randomuser.me/"
        private const val QUERY_RESULTS = "results"
    }
}