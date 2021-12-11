package com.example.android.politicalpreparedness.ui.voter.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.common.base.BaseViewModel
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.VoterInfoEntity
import com.example.android.politicalpreparedness.domain.interactor.GetSavedElectionByIdUseCase
import com.example.android.politicalpreparedness.domain.interactor.GetVoterInfoUseCase
import com.example.android.politicalpreparedness.domain.interactor.RemoveElectionUseCase
import com.example.android.politicalpreparedness.domain.interactor.SaveElectionUseCase
import com.example.android.politicalpreparedness.ui.voter.VoterInfoFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class VoterInfoState {
    object Loading : VoterInfoState()
    data class Success(val value: VoterInfoEntity) : VoterInfoState()
    data class Error(
        val message: String? = null,
        val error: Throwable? = null
    ) : VoterInfoState()
}

sealed class FollowElectionState {
    object Followed : FollowElectionState()
    object Unfollowed : FollowElectionState()
    data class Error(
        val message: String? = null,
        val error: Throwable? = null
    ) : FollowElectionState()
}

@HiltViewModel
class VoterInfoViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
    private val getVoterInfoUseCase: GetVoterInfoUseCase,
    private val getElectionByIdUseCase: GetSavedElectionByIdUseCase,
    private val saveSelectionUseCase: SaveElectionUseCase,
    private val removeElectionUseCase: RemoveElectionUseCase,
) : BaseViewModel(context) {

    private val _voterInfoState = MutableStateFlow<VoterInfoState>(VoterInfoState.Loading)
//    val voterInfoState: StateFlow<VoterInfoState> get() = _voterInfoState
    val voterInfo: StateFlow<VoterInfoEntity?> get() = _voterInfoState.map {
        when(it){
            is VoterInfoState.Error -> null
            VoterInfoState.Loading -> null
            is VoterInfoState.Success -> it.value
        }
    }.stateIn(viewModelScope,SharingStarted.Eagerly, null)

    private lateinit var followElectionState: StateFlow<FollowElectionState>
    lateinit var followButtonStr: StateFlow<String>

    private val args = VoterInfoFragmentArgs.fromSavedStateHandle(savedStateHandle)
    val election = args.argElection

    init {
        loadFollowElectionStatus()
        launch {
            loadVoterInfo()
        }
    }

    private suspend fun loadVoterInfo() {
        _voterInfoState.value = VoterInfoState.Loading
        val param = GetVoterInfoUseCase.Param(election)
        val newState = when (val result = getVoterInfoUseCase.invoke(param)) {
            is ResultWrapper.Error -> VoterInfoState.Error(result.message, result.error)
            is ResultWrapper.Success -> VoterInfoState.Success(result.data)
        }
        _voterInfoState.compareAndSet(VoterInfoState.Loading, newState)
    }

    private fun loadFollowElectionStatus() {
        followElectionState = getElectionByIdUseCase.invoke(election.id).map {
            when (it) {
                is ResultWrapper.Success ->
                    if (it.data != null) FollowElectionState.Followed
                    else FollowElectionState.Unfollowed
                is ResultWrapper.Error -> FollowElectionState.Error(it.message, it.error)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, FollowElectionState.Unfollowed)

        followButtonStr = followElectionState.map {
            if (it is FollowElectionState.Followed) context?.getString(R.string.unfollow_election) ?: ""
            else context?.getString(R.string.follow_election) ?: ""
        }.stateIn(viewModelScope, SharingStarted.Lazily, "")
    }

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */
    fun toggleFollowElection() = launch {
        if (followElectionState.value is FollowElectionState.Followed) {
            val param = RemoveElectionUseCase.Param(election)
            removeElectionUseCase.invoke(param)
        } else {
            val param = SaveElectionUseCase.Param(election)
            saveSelectionUseCase.invoke(param)
        }
    }

}