//package com.example.android.politicalpreparedness.ui.voter.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.android.politicalpreparedness.domain.repository.ElectionRepository
//
////TODO: Create Factory to generate VoterInfoViewModel with provided election datasource - OK
//// Not needed. Using Hilt to inject repository directly.
//class VoterInfoViewModelFactory(private val electionRepository: ElectionRepository): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
//            return VoterInfoViewModel(electionRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}