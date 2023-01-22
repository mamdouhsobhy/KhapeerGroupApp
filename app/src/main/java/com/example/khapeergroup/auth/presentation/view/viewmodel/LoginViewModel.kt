package com.example.khapeergroup.auth.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khapeergroup.auth.data.responseremote.ModelLoginResponseRemote
import com.example.khapeergroup.auth.domain.interactor.LoginUseCase
import com.example.khapeergroup.core.presentation.base.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginActivityState>(LoginActivityState.Init)
    val loginState: StateFlow<LoginActivityState> get() = _loginState


    private fun setLoading() {
        _loginState.value = LoginActivityState.IsLoading(true)
    }

    private fun hideLoading() {
        _loginState.value = LoginActivityState.IsLoading(false)
    }

    private fun showToast(message: String, isConnectionError: Boolean) {
        _loginState.value = LoginActivityState.ShowToast(message, isConnectionError)
    }


    fun makelogin(map: HashMap<String, String?>?) {
        viewModelScope.launch {
            loginUseCase.execute(map)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    if(exception.message!!.contains("Failed")) {
                        showToast(exception.message.toString(), true)
                    }else{
                        showToast(exception.message.toString(), false)
                    }
                }.buffer()
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.ErrorState -> _loginState.value =
                            LoginActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _loginState.value = it.items?.let { it1 ->
                            LoginActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

}


sealed class LoginActivityState {
    object Init : LoginActivityState()
    data class IsLoading(val isLoading: Boolean) : LoginActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : LoginActivityState()
    data class Success(val loginModel: ModelLoginResponseRemote) :
        LoginActivityState()

    data class ErrorLogin(val errorCode: Int, val errorMessage: String) : LoginActivityState()
}