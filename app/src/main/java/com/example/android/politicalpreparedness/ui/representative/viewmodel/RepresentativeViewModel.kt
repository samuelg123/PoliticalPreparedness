package com.example.android.politicalpreparedness.ui.representative.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import com.example.android.politicalpreparedness.common.base.BaseViewModel
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.AddressEntity
import com.example.android.politicalpreparedness.domain.entity.RepresentativeEntity
import com.example.android.politicalpreparedness.domain.interactor.GetListRepresentativesByAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RepresentativesState {
    object Initial : RepresentativesState()
    object Loading : RepresentativesState()
    data class Success(val value: List<RepresentativeEntity>) : RepresentativesState()
    data class Error(
        val message: String? = null,
        val error: Throwable? = null
    ) : RepresentativesState()
}

@HiltViewModel
class RepresentativeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getListRepresentativesByAddressUseCase: GetListRepresentativesByAddressUseCase
) : BaseViewModel(context) {

    private val _representativesState =
        MutableStateFlow<RepresentativesState>(RepresentativesState.Initial)
    val representativesState: StateFlow<RepresentativesState> = _representativesState

    val addressFilter = ObservableField<AddressEntity>()

    fun fetchRepresentatives() = launch {
        val address = getAddressFromFields() ?: return@launch
        _representativesState.value = RepresentativesState.Loading
        val param = GetListRepresentativesByAddressUseCase.Param(address)
        val newState = when (val result = getListRepresentativesByAddressUseCase.invoke(param)) {
            is ResultWrapper.Success -> RepresentativesState.Success(result.data)
            is ResultWrapper.Error -> RepresentativesState.Error(result.message, result.error)
        }
        _representativesState.compareAndSet(RepresentativesState.Loading, newState)
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    fun setAddressFromGeoLocation(address: AddressEntity) = addressFilter.set(address)

    private fun getAddressFromFields() = addressFilter.get()

}
