package com.example.android.politicalpreparedness.common.base

import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

/**
 * Base Fragment to observe on the common LiveData objects
 */
abstract class BaseFragment : Fragment() {

    protected val parentActivity: BaseActivity
        get() {
            val a = activity
            if (a is BaseActivity) return a
            throw Exception("Must be BaseActivity!")
        }

    protected fun showToast(message: String) =
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()

    protected fun showSnackBar(message: String) =
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()

}