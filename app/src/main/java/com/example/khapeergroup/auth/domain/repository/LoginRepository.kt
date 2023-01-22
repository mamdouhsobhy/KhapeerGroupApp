package com.example.khapeergroup.auth.domain.repository

import com.example.khapeergroup.auth.data.responseremote.ModelLoginResponseRemote
import com.example.khapeergroup.core.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import java.util.HashMap

interface LoginRepository {

    suspend fun login(map: HashMap<String, String?>?) : Flow<BaseResult<ModelLoginResponseRemote>>

}