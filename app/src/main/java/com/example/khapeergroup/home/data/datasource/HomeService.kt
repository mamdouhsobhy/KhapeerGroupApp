package com.example.khapeergroup.home.data.datasource

import com.example.khapeergroup.home.data.responseremote.ModelGetUserPayrollsResponseRemote
import retrofit2.Response
import retrofit2.http.GET


interface HomeService {

    @GET("GetPayroll")
    suspend fun getUserPayroll(): Response<ModelGetUserPayrollsResponseRemote>

}
