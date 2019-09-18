package com.kiran.evoketest.android.network

import com.kiran.evoketest.android.model.User
import io.reactivex.Flowable
import retrofit2.http.GET

interface EvokeTestAPIService {
    @GET("users")
    fun getUsers(): Flowable<ArrayList<User>>
}