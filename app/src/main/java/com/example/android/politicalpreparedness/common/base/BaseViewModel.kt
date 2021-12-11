package com.example.android.politicalpreparedness.common.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * Base class for View Models to declare the common LiveData objects in one place
 */
abstract class BaseViewModel(context: Context) : ViewModel(), CoroutineScope {
    private val contextReference: WeakReference<Context> = WeakReference(context)

    protected val context get() = contextReference.get()

    override val coroutineContext get() = viewModelScope.coroutineContext
}