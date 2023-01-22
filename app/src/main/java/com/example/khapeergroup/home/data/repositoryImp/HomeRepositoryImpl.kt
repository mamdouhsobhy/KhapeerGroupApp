package com.example.khapeergroup.home.data.repositoryImp

import com.example.khapeergroup.core.presentation.base.BaseResult
import com.example.khapeergroup.home.data.datasource.HomeService
import com.example.khapeergroup.home.data.responseremote.ModelGetUserPayrollsResponseRemote
import com.example.khapeergroup.home.domain.repository.HomeRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeService: HomeService) :
    HomeRepository {
    override suspend fun getUserPayroll(): Flow<BaseResult<ModelGetUserPayrollsResponseRemote>> {
        return flow {
            val response = homeService.getUserPayroll()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val type = object :
                    TypeToken<ModelGetUserPayrollsResponseRemote>() {}.type
                val err: ModelGetUserPayrollsResponseRemote =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                emit(BaseResult.ErrorState(400, err.ArabicMessage ?: "something went wrong"))
            }
        }
    }

}


