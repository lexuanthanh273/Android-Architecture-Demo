package com.architecture.core.base

import android.content.res.Configuration
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.architecture.core.network.BusinessError
import com.architecture.core.network.HttpExceptionManager
import com.architecture.core.R
import com.architecture.core.base.dialog.CommonDialog
import com.architecture.core.base.dialog.CommonDialogType
import com.architecture.core.base.executor.AppExecutors
import com.architecture.core.bindingadapter.insetter.Insetter
import com.architecture.core.bindingadapter.insetter.windowInsetTypesOf
import com.architecture.domain.UiResource
import com.architecture.navigation.NavigationManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId), CoroutineScope {

    @Inject
    lateinit var appExecutors: AppExecutors

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onStart() {
        Glide.with(this).onStart()
        super.onStart()
    }

    override fun onStop() {
        Glide.with(this).onStop()
        super.onStop()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Glide.with(this).onConfigurationChanged(newConfig)
        super.onConfigurationChanged(newConfig)
    }

    override fun onDestroyView() {
        coroutineContext.cancelChildren()
        try {
            Glide.with(this).onDestroy()
        } catch (e: Exception) {
        }
        super.onDestroyView()
    }

    open fun isCanBackPress() = true

    open fun commonDialogWarningBack(onOk: () -> Unit) {
        val commonDialog = CommonDialog.Builder()
            .setTittle("Thoát màn hình")
            .setMessage("Bạn có muốn thoát không?")
            .setCancelable(false)
            .setCommonDialogType(CommonDialogType.Warning)
            .setOnPositive(getString(R.string.all_ok)) {
                onOk.invoke()
            }
            .setOnNegative(getString(R.string.all_cancel))
        commonDialog.build(requireContext()).show()
    }

    protected fun applyInsetterStatusBarsFor(view: View) {
        Insetter.builder()
            .padding(windowInsetTypesOf(statusBars = true))
            .applyToView(view)
    }

    open fun showApiErrorDialog(httpException: UiResource.Error.HttpException, message: String) {
        when (httpException) {
            HttpExceptionManager.NoConnectionException -> {
                showNetworkAuthenticationDialog()
            }
            HttpExceptionManager.BadRequestException -> {
                showBadRequestDialog()
            }
            HttpExceptionManager.UnauthorizedException -> {
                NavigationManager.navigateToAuthActivity(requireContext())
            }
            HttpExceptionManager.NotFoundException -> {
                showNotFoundDialog()
            }
            HttpExceptionManager.InternalServerErrorException -> {
                showInternalServerErrorDialog()
            }
            HttpExceptionManager.NotImplementedException -> {
                showNotImplementedDialog()
            }
            HttpExceptionManager.ServiceUnavailableException -> {
                showServiceUnavailableDialog()
            }
            HttpExceptionManager.RequestTimeoutException -> {
                showRequestTimeoutDialog()
            }
            HttpExceptionManager.NetworkAuthenticationException -> {
                showNetworkAuthenticationDialog()
            }
            is HttpExceptionManager.BusinessException -> {
                handleBusinessError(httpException.businessError)
            }
            else -> {
                showUnknownDialog(httpException, message)
            }
        }
    }

    protected open fun handleBusinessError(businessError: BusinessError) {
        when (businessError) {
            BusinessError.CodeIsNotExitsOrExpired -> {
                showCodeIsNotExitsOrExpiredDialog()
            }
            BusinessError.CannotAddFriendWithMyself -> {
                showCannotAddFriendWithMyselfDialog()
            }
            BusinessError.AlreadyFriend -> {
                showAlreadyFriendDialog()
            }
            BusinessError.PatternHasExisted -> {

            }
            BusinessError.PatternIsEmpty -> {

            }
            BusinessError.NumberHasExisted -> {

            }
            BusinessError.NumberHasEmpty -> {

            }
            else -> {

            }

        }
    }


    // error Business LotteryNumberSet




    // error Business AddFriend

    private fun showCodeIsNotExitsOrExpiredDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.all_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.message_error_business_code_ss_not_exit_or_expired))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }

    private fun showCannotAddFriendWithMyselfDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.all_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.message_error_business_cannot_add_friend_with_myself))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }

    private fun showAlreadyFriendDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.all_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.message_error_business_already_friend))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }

    // error server
    private fun showNoConnectionDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.all_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.error_message_an_unknown_error))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }


    private fun showBadRequestDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.title_error_invalid_request))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.error_message_the_requested_information_is_not_valid_please_check_again))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }

    private fun showNotFoundDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.all_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.error_message_request_do_not_exist))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }

    private fun showInternalServerErrorDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.title_error_server_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.error_message_the_server_has_a_problem_try_again))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }

    private fun showNotImplementedDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.title_error_server_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.error_message_server_does_not_support))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }

    private fun showServiceUnavailableDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.title_error_server_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.error_message_server_is_under_maintenance_or_overloaded_try_again))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }

    private fun showRequestTimeoutDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.all_error_connection))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.error_message_server_did_not_receive_the_request_try_again))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }


    private fun showUnknownDialog(httpException: UiResource.Error.HttpException, message: String) {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.all_error))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(
                String.format(
                    getString(R.string.error_message_an_unknown_error),
                    message,
                    httpException.httpCode
                )
            )
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }


    private fun showNetworkAuthenticationDialog() {
        val commonDialog = CommonDialog.Builder()
            .setTittle(getString(R.string.all_error_connection))
            .setCommonDialogType(CommonDialogType.Error)
            .setMessage(getString(R.string.error_message_check_your_network_connect))
            .setCancelable(false)
            .setOnPositive(getString(R.string.all_ok))
        commonDialog.build(requireContext()).show()
    }
}

fun <T : Any> Fragment.collectFlowOn(
    sharedFlow: SharedFlow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onResult: (t: T) -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
            sharedFlow.collectLatest {
                onResult.invoke(it)
            }
        }
    }
}

fun <T : Any> Fragment.collectFlowOn(
    sharedFlow: StateFlow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onResult: (t: T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
            sharedFlow.collect {
                onResult.invoke(it)
            }
        }
    }
}