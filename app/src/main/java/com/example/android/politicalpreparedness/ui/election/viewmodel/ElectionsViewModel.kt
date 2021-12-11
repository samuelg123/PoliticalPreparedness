package com.example.android.politicalpreparedness.ui.election.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.common.base.BaseViewModel
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.data.datasource.local.database.dao.ElectionDao
import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import com.example.android.politicalpreparedness.domain.interactor.GetElectionsUseCase
import com.example.android.politicalpreparedness.domain.interactor.GetListSavedElectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SavedElectionState {
    object Loading : SavedElectionState()
    data class Success(val value: List<ElectionEntity>) : SavedElectionState()
    data class Error(
        val message: String? = null,
        val error: Throwable? = null
    ) : SavedElectionState()
}

sealed class UpcomingElectionState {
    object Loading : UpcomingElectionState()
    data class Success(val value: List<ElectionEntity>) : UpcomingElectionState()
    data class Error(
        val message: String? = null,
        val error: Throwable? = null
    ) : UpcomingElectionState()
}

@HiltViewModel
class ElectionsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val getListSavedElectionsUseCase: GetListSavedElectionsUseCase,
    val getElectionsUseCase: GetElectionsUseCase,
) : BaseViewModel(context) {
    lateinit var savedElectionState: StateFlow<SavedElectionState>

    private val _upcomingElectionState =
        MutableStateFlow<UpcomingElectionState>(UpcomingElectionState.Loading)
    val upcomingElectionState: StateFlow<UpcomingElectionState> = _upcomingElectionState

    init {
        loadSavedElections()
        launch {
            loadUpcomingElections()
        }
    }

    private suspend fun loadUpcomingElections() {
        _upcomingElectionState.value = UpcomingElectionState.Loading
        val newState = when (val result = getElectionsUseCase.invoke()) {
            is ResultWrapper.Success -> UpcomingElectionState.Success(result.data)
            is ResultWrapper.Error -> UpcomingElectionState.Error(result.message, result.error)
        }
        _upcomingElectionState.compareAndSet(UpcomingElectionState.Loading, newState)
    }

    private fun loadSavedElections() {
        savedElectionState = getListSavedElectionsUseCase.invoke().map {
            when (it) {
                is ResultWrapper.Success -> SavedElectionState.Success(it.data)
                is ResultWrapper.Error -> SavedElectionState.Error(it.message, it.error)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, SavedElectionState.Loading)
    }

}