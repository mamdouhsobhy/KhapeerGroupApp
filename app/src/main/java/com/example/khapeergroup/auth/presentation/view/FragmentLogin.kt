package com.example.khapeergroup.auth.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.khapeergroup.R
import com.example.khapeergroup.auth.data.responseremote.ModelLoginResponseRemote
import com.example.khapeergroup.auth.presentation.view.viewmodel.LoginActivityState
import com.example.khapeergroup.auth.presentation.view.viewmodel.LoginViewModel
import com.example.khapeergroup.core.presentation.base.BaseFragmentBinding
import com.example.khapeergroup.core.presentation.common.SharedPrefs
import com.example.khapeergroup.core.presentation.extensions.showGenericAlertDialog
import com.example.khapeergroup.core.presentation.extensions.showToast
import com.example.khapeergroup.core.presentation.utilities.isEmpty
import com.example.khapeergroup.databinding.FragmentLoginBinding
import com.example.khapeergroup.home.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentLogin : BaseFragmentBinding<FragmentLoginBinding>() {
    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: LoginViewModel by viewModels()
    private val map = HashMap<String, String?>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListenersOnViews()
        observeStateFlow()

    }

    private fun addListenersOnViews() {

        binding.btnLogin.setOnClickListener {
            if (validateForm()) {
                map["MobileNumber"] = binding.edPhone.text.toString()
                map["Password"] = binding.edPassword.text.toString()
                viewModel.makelogin(map)
            }

        }

    }


    private fun validateForm(): Boolean {
        return if (binding.edPhone.isEmpty(getString(R.string.enter_phone))) {
            false
        } else !binding.edPassword.isEmpty(getString(R.string.enter_password))
    }


    private fun observeStateFlow() {
        viewModel.loginState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: LoginActivityState) {
        when (state) {
            is LoginActivityState.Init -> Unit
            is LoginActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is LoginActivityState.Success -> handleSuccess(state.loginModel)
            is LoginActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is LoginActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading){
            showLoading()
        }else{
            dismissLoading()
        }
    }

    private fun handleSuccess(loginModel: ModelLoginResponseRemote) {
        sharedPrefs.saveToken(loginModel.Token)
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()

    }

    private fun handleErrorLogin(errorCode: Int, errorMessage: String) {
        requireActivity().showGenericAlertDialog(errorMessage)
    }

}