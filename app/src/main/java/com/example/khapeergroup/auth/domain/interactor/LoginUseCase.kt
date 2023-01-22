package com.example.khapeergroup.auth.domain.interactor

import com.example.khapeergroup.auth.data.responseremote.ModelLoginResponseRemote
import com.example.khapeergroup.core.presentation.base.BaseResult
import com.example.khapeergroup.auth.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import java.util.HashMap
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun execute(map: HashMap<String, String?>?): Flow<BaseResult<ModelLoginResponseRemote>> {
        return loginRepository.login(map)
    }
}