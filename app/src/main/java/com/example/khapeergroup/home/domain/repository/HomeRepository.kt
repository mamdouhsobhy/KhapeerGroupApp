package com.example.khapeergroup.home.domain.repository

import com.example.khapeergroup.core.presentation.base.BaseResult
import com.example.khapeergroup.home.data.responseremote.ModelGetUserPayrollsResponseRemote
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getUserPayroll(): Flow<BaseResult<ModelGetUserPayrollsResponseRemote>>

}

