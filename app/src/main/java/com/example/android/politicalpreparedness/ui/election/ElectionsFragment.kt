package com.example.android.politicalpreparedness.ui.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.common.base.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import com.example.android.politicalpreparedness.ui.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.ui.election.viewmodel.ElectionsViewModel
import com.example.android.politicalpreparedness.ui.election.viewmodel.SavedElectionState
import com.example.android.politicalpreparedness.ui.election.viewmodel.UpcomingElectionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ElectionsFragment : BaseFragment() {

    //TODO: Declare ViewModel - OK
    private val viewModel: ElectionsViewModel by viewModels()

    private var _binding: FragmentElectionBinding? = null
    val binding get() = _binding!!

    private lateinit var upcomingAdapter: ElectionListAdapter
    private lateinit var savedAdapter: ElectionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //TODO: Add ViewModel values and create ViewModel - OK

        //TODO: Add binding values - OK
        _binding = FragmentElectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        //TODO: Link elections to voter info - OK
        binding.viewModel = viewModel

        //TODO: Initiate recycler adapters - OK
        upcomingAdapter = ElectionListAdapter {
            navToVoterInfo(it)
        }
        savedAdapter = ElectionListAdapter {
            navToVoterInfo(it)
        }
        with(binding.upcomingElectionsRecycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = upcomingAdapter
            setHasFixedSize(false)
        }
        with(binding.savedElectionsRecycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = savedAdapter
            setHasFixedSize(false)
        }

        //TODO: Populate recycler adapters - OK
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.upcomingElectionState.collect { uiState ->
                        if (binding.loadingProgressBar.isShown) binding.loadingProgressBar.hide()
                        when (uiState) {
                            is UpcomingElectionState.Loading -> binding.loadingProgressBar.show()
                            is UpcomingElectionState.Success -> upcomingAdapter.submitList(uiState.value)
                            is UpcomingElectionState.Error -> uiState.error?.message?.let {
                                showToast(it)
                            }
                        }
                    }
                }
                launch {
                    viewModel.savedElectionState.collect { uiState ->
                        if (binding.loadingProgressBar.isShown) binding.loadingProgressBar.hide()
                        when (uiState) {
                            is SavedElectionState.Loading -> binding.loadingProgressBar.show()
                            is SavedElectionState.Success -> savedAdapter.submitList(uiState.value)
                            is SavedElectionState.Error -> {
                                uiState.error?.message?.let {
                                    showToast(it)
                                }
                            }
                        }
                    }
                }
            }
        }
        return binding.root
    }

    //TODO: Refresh adapters when fragment loads - OK

    private fun navToVoterInfo(election: ElectionEntity) {
        this.findNavController().navigate(
            ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}