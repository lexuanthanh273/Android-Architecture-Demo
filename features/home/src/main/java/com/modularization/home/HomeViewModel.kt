package com.modularization.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.modularization.common.base.BaseViewModel
import com.modularization.common.utils.Event
import com.modularization.featurehome.R
import com.modularization.home.domain.GetTopUsersUseCase
import com.modularization.model.User
import com.modularization.repository.AppDispatchers
import com.modularization.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [HomeFragment].
 */
class HomeViewModel(private val getTopUsersUseCase: GetTopUsersUseCase,
                    private val dispatchers: AppDispatchers) : BaseViewModel() {

    // FOR DATA
    private val _users = MediatorLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>> get() = _users
    private var usersSource: LiveData<Resource<List<User>>> = MutableLiveData()

    init {
        getUsers(false)
    }

    // PUBLIC ACTIONS ---
    fun userClicksOnItem(user: User)
            = navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(user.login))

    fun userRefreshesItems()
            = getUsers(true)

    // ---

    private fun getUsers(forceRefresh: Boolean) = viewModelScope.launch(dispatchers.main) {
        _users.removeSource(usersSource) // We make sure there is only one source of livedata (allowing us properly refresh)
        withContext(dispatchers.io) {
            usersSource = getTopUsersUseCase(forceRefresh = forceRefresh)
        }
        _users.addSource(usersSource) {
            _users.value = it
            if (it.status == Resource.Status.ERROR) _snackbarError.value = Event(R.string.an_error_happened)
        }
    }
}