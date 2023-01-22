package com.example.khapeergroup.home.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.khapeergroup.R
import com.example.khapeergroup.auth.presentation.ActivityAuth
import com.example.khapeergroup.core.presentation.base.BaseFragmentBinding
import com.example.khapeergroup.core.presentation.common.SharedPrefs
import com.example.khapeergroup.core.presentation.extensions.showGenericAlertDialog
import com.example.khapeergroup.core.presentation.extensions.showToast
import com.example.khapeergroup.databinding.FragmentHomeBinding
import com.example.khapeergroup.home.data.responseremote.ModelGetUserPayrollsResponseRemote
import com.example.khapeergroup.home.presentation.view.viewmodel.HomeActivityState
import com.example.khapeergroup.home.presentation.view.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.mahozad.android.PieChart
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentHome : BaseFragmentBinding<FragmentHomeBinding>() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListenerOnView()
        observeStateFlow()
        getUserPayroll()

    }

    private fun addListenerOnView() {
        binding.swipeToRefresh.setOnRefreshListener {
            homeViewModel.getUserPayroll()
        }
        binding.imgBack.setOnClickListener {
            sharedPrefs.saveToken("")
            val intent = Intent(requireContext(), ActivityAuth::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun getUserPayroll() {
        homeViewModel.getUserPayroll()
    }

    private fun observeStateFlow() {
        homeViewModel.getUserPayrollState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: HomeActivityState) {
        when (state) {
            is HomeActivityState.Init -> Unit
            is HomeActivityState.ErrorLogin -> handleError(
                state.errorCode,
                state.errorMessage
            )
            is HomeActivityState.Success -> handleSuccess(state.modelGetUserPayrolls)
            is HomeActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is HomeActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }
    
    private fun handleLoading(isLoading: Boolean) {
        binding.swipeToRefresh.isRefreshing=false
        if(isLoading){
            binding.layoutHome.visibility=View.GONE
            binding.shimmerView.isVisible = true
            binding.shimmerView.startShimmerAnimation()
        }else{
            binding.layoutHome.visibility=View.VISIBLE
            binding.shimmerView.isVisible = false
            binding.shimmerView.stopShimmerAnimation()
        }
    }
    
    private fun handleError(errorCode: Int, errorMessage: String) {
        requireActivity().showGenericAlertDialog(errorMessage)
    }
    
    private fun handleSuccess(modelGetUserPayroll : ModelGetUserPayrollsResponseRemote?) {

        binding.tvUserName.text=modelGetUserPayroll?.Payroll!!.Employee[0].EMP_DATA_AR
        binding.tvJobTitle.text=modelGetUserPayroll.Payroll.Employee[0].JOBNAME_AR
        binding.tvTotalSalaryChartValue.text=((modelGetUserPayroll.Payroll.Deduction[0].SAL_VALUE.toInt()-modelGetUserPayroll.Payroll.Allowences[0].SAL_VALUE.toInt()).toFloat()).toString()+" ج "
        binding.tvDate.text=getDateFromFormat(modelGetUserPayroll.Payroll.Date)
        binding.tvTotalSalaryChartValue.text=modelGetUserPayroll.Payroll.Allowences[0].SAL_VALUE.toString()+" ج "
        binding.tvDeductionsValue.text=modelGetUserPayroll.Payroll.Deduction[0].SAL_VALUE.toString()+" ج "
        binding.tvTotalSalaryChartValue.text=((modelGetUserPayroll.Payroll.Deduction[0].SAL_VALUE.toInt()-modelGetUserPayroll.Payroll.Allowences[0].SAL_VALUE.toInt()).toFloat()).toString()+" ج "
        binding.includeCardSalary.tvBasicSalaryValue.text=modelGetUserPayroll.Payroll.Allowences[0].SAL_VALUE.toString()+" ج "
        binding.includeCardSalary.tvNaturalWorkValue.text=modelGetUserPayroll.Payroll.Allowences[1].SAL_VALUE.toString()+" ج "
        binding.includeCardSalary.tvOtherDeductionsValue.text=modelGetUserPayroll.Payroll.Deduction[0].SAL_VALUE.toString()+" ج "
        val percentage=(modelGetUserPayroll.Payroll.Deduction[0].SAL_VALUE-modelGetUserPayroll.Payroll.Allowences[0].SAL_VALUE)/modelGetUserPayroll.Payroll.Allowences[0].SAL_VALUE
        if(percentage>1-percentage){
            binding.pieChart.slices = listOf(
                PieChart.Slice(percentage.toFloat(), resources.getColor(R.color.color_yellow)),
                PieChart.Slice((1-percentage).toFloat(), Color.GRAY),
            )
        }else{
            binding.pieChart.slices = listOf(
                PieChart.Slice(percentage.toFloat(), Color.GRAY),
                PieChart.Slice((1-percentage).toFloat(), resources.getColor(R.color.color_yellow)),
            )
        }

    }

    private fun getDateFromFormat(date: String): CharSequence? {
        val dateFormat = SimpleDateFormat("dd/yyyy")
        val objDate: Date = dateFormat.parse(date) as Date
        val dateFormat2 = SimpleDateFormat("MMMM,yyyy")
        return dateFormat2.format(objDate)
    }

}