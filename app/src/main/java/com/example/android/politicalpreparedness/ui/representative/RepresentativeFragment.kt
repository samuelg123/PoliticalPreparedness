package com.example.android.politicalpreparedness.ui.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android.politicalpreparedness.common.base.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.domain.entity.AddressEntity
import com.example.android.politicalpreparedness.ui.representative.adapter.RepresentativeListAdapter
import com.example.android.politicalpreparedness.ui.representative.viewmodel.RepresentativeViewModel
import com.example.android.politicalpreparedness.ui.representative.viewmodel.RepresentativesState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.asDeferred
import java.util.*

@AndroidEntryPoint
class RepresentativeFragment : BaseFragment() {

    companion object {
        //TODO: Add Constant for Location request - ?

    }

    private val resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
                resolutionRequestCompletable.complete(true)
            } else {
                resolutionRequestCompletable.complete(false)
            }
        }

    private val permissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            permissionRequestCompletable.complete(result)
            //TODO: Handle location permission result to get location on permission granted - ok
        }

    private lateinit var permissionRequestCompletable: CompletableDeferred<MutableMap<String, Boolean>>
    private lateinit var resolutionRequestCompletable: CompletableDeferred<Boolean>
    private val permissionMutex = Mutex()
    private val resolutionMutex = Mutex()

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    private var cancellationTokenSource = CancellationTokenSource()

    //TODO: Declare ViewModel
    private val viewModel: RepresentativeViewModel by viewModels()
    private var _binding: FragmentRepresentativeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //TODO: Establish bindings - OK
        _binding = FragmentRepresentativeBinding.inflate(inflater, container, false)

        //TODO: Define and assign Representative adapter - OK
        binding.viewModel = viewModel
        val adapter = RepresentativeListAdapter {
            /* no-op */
        }
        binding.representativeRecycler.apply {
            setAdapter(adapter)
        }
        binding.lifecycleOwner = viewLifecycleOwner

        //TODO: Populate Representative adapter - OK
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.representativesState.collect { uiState ->
                    if (binding.loadingProgressBar.isShown) binding.loadingProgressBar.hide()
                    when (uiState) {
                        RepresentativesState.Loading -> binding.loadingProgressBar.show()
                        is RepresentativesState.Error -> uiState.error?.message?.let {
                            showToast(it)
                        }
                        is RepresentativesState.Success -> adapter.submitList(uiState.value)
                    }
                }
            }
        }

        //TODO: Establish button listeners for field and location search - OK
        binding.buttonSearch.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                viewModel.fetchRepresentatives()
            }
        }
        binding.buttonLocation.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                val address = getLocation() ?: return@launchWhenStarted
                viewModel.setAddressFromGeoLocation(address)
            }
        }

        return binding.root
    }

    private suspend fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            //TODO: Request Location permissions - OK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permissions = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                requestForPermissions(*permissions)
                false
            } else true
        }
    }

    private suspend fun requestForPermissions(vararg permission: String): Boolean =
        permissionMutex.withLock {
            permissionRequestCompletable = CompletableDeferred()
            permissionResultLauncher.launch(permission)
            for ((_, isGranted) in permissionRequestCompletable.await().entries.asIterable()) {
                if (!isGranted) return@withLock false
            }
            return@withLock true
        }

    private fun isPermissionGranted(): Boolean {
        //TODO: Check if permission is already granted and return (true = granted, false = denied/other) - OK
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private suspend fun getLocation(): AddressEntity? {
        if (!checkLocationPermissions()) return null
        //TODO: Get location from LocationServices - OK
        val isEnabled = enableLocationServiceSettings()
        val location = (if (isEnabled) getLastLocation() else return null) ?: return null
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address - OK
        return geoCodeLocation(location)
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastLocation(): Location? =
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).asDeferred().await()

    suspend fun enableLocationServiceSettings(priority: Int = LocationRequest.PRIORITY_LOW_POWER): Boolean {
        val locationRequest = LocationRequest.create()
            .setPriority(priority)
            .setInterval((10 * 1000).toLong())
            .setFastestInterval((1 * 1000).toLong())

        val settingsBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)
        var isGranted = false

        try {
            LocationServices.getSettingsClient(requireContext())
                .checkLocationSettings(settingsBuilder.build())
                .asDeferred().await()
            isGranted = true
        } catch (ex: ResolvableApiException) {
            when (ex.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    //this should enable device gps
                    isGranted = ex.status.resolutionForResult()
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    /* no-op */
                }
                LocationSettingsStatusCodes.SUCCESS -> {
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                }
            }
        } catch (e: ResolvableApiException) {
            /* no-op */
        }
        return isGranted
    }

    private fun geoCodeLocation(location: Location): AddressEntity {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .map { address ->
                AddressEntity(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
            .first()
    }

    private suspend fun Status.resolutionForResult(): Boolean = resolutionMutex.withLock {
        return@withLock this.resolution?.let {
            resolutionRequestCompletable = CompletableDeferred()
            return try {
                val intentSenderRequest = IntentSenderRequest.Builder(it).build()
                resolutionForResult.launch(intentSenderRequest)
                resolutionRequestCompletable.await()
            } catch (e: IntentSender.SendIntentException) {
                // Ignore the error.
                false
            } catch (e: ClassCastException) {
                // Ignore, should be an impossible error.
                false
            } catch (e: Throwable) {
                false
            }
        } ?: false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}