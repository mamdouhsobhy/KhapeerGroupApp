package com.example.khapeergroup.home.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khapeergroup.core.presentation.base.BaseResult
import com.example.khapeergroup.home.data.responseremote.ModelGetUserPayrollsResponseRemote
import com.example.khapeergroup.home.domain.interactor.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val homeUseCase: HomeUseCase
) : ViewModel() {
    
    private val _getUserPayrollState =
        MutableStateFlow<HomeActivityState>(HomeActivityState.Init)
    val getUserPayrollState: StateFlow<HomeActivityState> get() = _getUserPayrollState
    
    private fun setLoading() {
        _getUserPayrollState.value = HomeActivityState.IsLoading(true)
    }

    private fun hideLoading() {
        _getUserPayrollState.value = HomeActivityState.IsLoading(false)
    }

    private fun showToast(message: String, isConnectionError: Boolean) {
        _getUserPayrollState.value = HomeActivityState.ShowToast(message, isConnectionError)
    }


    fun getUserPayroll() {
        viewModelScope.launch {
            homeUseCase.executeGetUserPayroll()
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
                }
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.ErrorState -> _getUserPayrollState.value =
                            HomeActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _getUserPayrollState.value = it.items.let { it1 ->
                            HomeActivityState.Success(
                                it1
                            )
                        }
                    }
                }
        }
    }

}

sealed class HomeActivityState {
    object Init : HomeActivityState()
    data class IsLoading(val isLoading: Boolean) : HomeActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : HomeActivityState()
    data class Success(val modelGetUserPayrolls: ModelGetUserPayrollsResponseRemote?) :
        HomeActivityState()

    data class ErrorLogin(val errorCode: Int, val errorMessage: String) : HomeActivityState()
}
