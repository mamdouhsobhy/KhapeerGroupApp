package com.example.khapeergroup.auth.data.datasource

import com.example.khapeergroup.auth.data.responseremote.ModelLoginResponseRemote
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.HashMap

interface LoginService {
    @FormUrlEncoded
    @POST("LogIn")
    suspend fun login(@FieldMap map: HashMap<String, String?>?): Response<ModelLoginResponseRemote>
}