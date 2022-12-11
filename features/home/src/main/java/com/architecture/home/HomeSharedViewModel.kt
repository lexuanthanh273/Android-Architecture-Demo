package com.architecture.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.architecture.core.base.BaseSharedViewModel
import com.architecture.domain.UiResource
import com.architecture.domain.file.UpdateFileUseCase
import com.architecture.domain.file.UploadFile
import com.architecture.domain.friend.Friend
import com.architecture.domain.friend.GetFriendsUseCase
import com.architecture.domain.websocket.WebsocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeSharedViewModel @Inject constructor(
    private val getFriendsUseCase: GetFriendsUseCase,
    private val updateFileUseCase: UpdateFileUseCase,
    private val websocketRepository: WebsocketRepository,
    private val handle: SavedStateHandle,
) : BaseSharedViewModel() {

    private val _updateFile = MutableSharedFlow<UiResource<UploadFile>>()
    val updateFile: SharedFlow<UiResource<UploadFile>> = _updateFile

    private val _friendBoardFlow = MutableStateFlow<UiResource<MutableList<Friend>>>(UiResource.Loading())
    val friendBoardFlow: StateFlow<UiResource<MutableList<Friend>>> = _friendBoardFlow

    init {
        fetchFriends()
        websocketRepository
    }

    fun fetchFriends() {
        viewModelScope.launch(Dispatchers.Main) {
            val friendsDeferred = async(Dispatchers.IO) {
                getFriendsUseCase.getFriendsNonAsync()
            }
            _friendBoardFlow.value = friendsDeferred.await()
        }
    }

    fun updateFile(file: File) {
        viewModelScope.launch(Dispatchers.Main) {
            _updateFile.emit(UiResource.Loading())
            _updateFile.emit(updateFileUseCase.invoke(file))
        }
    }

}