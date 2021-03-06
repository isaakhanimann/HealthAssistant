package com.isaakhanimann.healthassistant.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaakhanimann.healthassistant.data.DataStorePreferences
import com.isaakhanimann.healthassistant.data.substances.repositories.SubstanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

val Date.asTextWithDateAndTime: String get() {
    val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    return formatter.format(this)
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    dataStorePreferences: DataStorePreferences,
    private val substanceRepository: SubstanceRepository
) : ViewModel() {

    val dateStringFlow = dataStorePreferences.dateFlow.mapNotNull { it.asTextWithDateAndTime }

    var isUpdating by mutableStateOf(false)

    fun updateSubstances(onSuccess: () -> Unit, onError: () -> Unit) {
        isUpdating = true
        viewModelScope.launch(Dispatchers.IO) {
            val isSuccess = substanceRepository.update()
            withContext(Dispatchers.Main) {
                if (isSuccess) {
                    onSuccess()
                } else {
                    onError()
                }
                isUpdating = false
            }
        }
    }

    fun resetSubstances() {
        isUpdating = true
        viewModelScope.launch(Dispatchers.IO) {
            substanceRepository.reset()
            withContext(Dispatchers.Main) {
                isUpdating = false
            }
        }
    }
}