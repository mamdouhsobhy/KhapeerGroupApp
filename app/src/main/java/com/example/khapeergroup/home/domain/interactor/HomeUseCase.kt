package com.example.khapeergroup.home.domain.interactor


import com.example.khapeergroup.core.presentation.base.BaseResult
import com.example.khapeergroup.home.data.responseremote.ModelGetUserPayrollsResponseRemote
import com.example.khapeergroup.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend fun executeGetUserPayroll(): Flow<BaseResult<ModelGetUserPayrollsResponseRemote>>{
        return homeRepository.getUserPayroll()
    }

}