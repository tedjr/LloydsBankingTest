package com.android.interviewtask.randomuserapp.di

import com.android.interviewtask.randomuserapp.rest.RandomUserApi
import com.android.interviewtask.randomuserapp.rest.RandomUserRepository
import com.android.interviewtask.randomuserapp.rest.RandomUserRepositoryImpl
import com.android.interviewtask.randomuserapp.ui.userslist.UserViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    // provides the userlist repository implementation
    fun provideStackExchangeRepo(randomUserApi: RandomUserApi): RandomUserRepository = RandomUserRepositoryImpl(randomUserApi)

    // provide Gson object
    fun provideGson() = GsonBuilder().create()

    // provide logging interceptor
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    // provide okhttp client
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    // providing the retrofit builder
    fun provideStackExchangeApi(okHttpClient: OkHttpClient, gson: Gson): RandomUserApi =
        Retrofit.Builder()
            .baseUrl(RandomUserApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(RandomUserApi::class.java)

    single { provideGson() }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideStackExchangeApi(get(), get()) }
    single { provideStackExchangeRepo(get()) }
}

val viewModelModule = module {
    viewModel {
        UserViewModel(get())
    }
}