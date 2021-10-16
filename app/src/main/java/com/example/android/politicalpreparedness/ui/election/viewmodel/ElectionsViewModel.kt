package com.example.android.politicalpreparedness.ui.election.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.data.models.Election
import dagger.hilt.android.lifecycle.HiltViewModel

//TODO: Construct ViewModel and provide election datasource
@HiltViewModel
class ElectionsViewModel(

) : ViewModel() {

    //TODO: Create live data val for upcoming elections
    private val upcomingElectionsLive = MutableLiveData<List<Election>>()

    //TODO: Create live data val for saved elections
    private val savedElectionsLive = MutableLiveData<List<Election>>()

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun showUpcomingElections() {
        //TODO: API implementation
        private val upcomingElections = mutableListOf<Election>()
    }

    fun showSavedElections() {
        //TODO: TAKE FROM LOCAL DB
        val savedElections = mutableListOf<Election>()
        savedElectionsLive.value = saved
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}