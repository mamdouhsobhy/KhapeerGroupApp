package com.example.khapeergroup.auth.data.repositoryimb

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.khapeergroup.auth.data.datasource.LoginService
import com.example.khapeergroup.auth.data.responseremote.ModelLoginResponseRemote
import com.example.khapeergroup.auth.domain.repository.LoginRepository
import com.example.khapeergroup.core.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.HashMap
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(private val loginService: LoginService) :
    LoginRepository {
    override suspend fun login(meMap: HashMap<String, String?>?): Flow<BaseResult<ModelLoginResponseRemote>> {
        return flow {
            val response = loginService.login(meMap)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                val type = object :
                    TypeToken<ModelLoginResponseRemote>() {}.type
                val err: ModelLoginResponseRemote =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                emit(BaseResult.ErrorState(err.Code, err.ArabicMessage ?: "something went wrong"))
            }
        }

    }


}