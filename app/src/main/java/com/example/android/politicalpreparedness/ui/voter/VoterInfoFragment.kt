package com.example.android.politicalpreparedness.ui.voter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.ui.voter.viewmodel.VoterInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoterInfoFragment : Fragment() {

    private val viewModel: VoterInfoViewModel by viewModels()
//    private val args: VoterInfoFragmentArgs by navArgs()

    private var _binding: FragmentVoterInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //TODO: Add ViewModel values and create ViewModel - OK

        //TODO: Add binding values
        _binding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        binding.followBtn.setOnClickListener {
            viewModel.toggleFollowElection()
        }
        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    //TODO: Create method to load URL intents

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}