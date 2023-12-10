package com.techstranding.retailx.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techstranding.retailx.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {
    val sentLogOutDataState = MutableStateFlow<SentLogOutDataState>(SentLogOutDataState.Idle)

    fun exitAccount() {
        viewModelScope.launch {
            sentLogOutDataState.value = SentLogOutDataState.Loading

            try {
                authService.exitAccount()

                sentLogOutDataState.value = SentLogOutDataState.Success
            } catch (e: Exception) {
                sentLogOutDataState.value = SentLogOutDataState.Error
            }
        }
    }

    fun resetState() {
        sentLogOutDataState.value = SentLogOutDataState.Idle
    }
}

sealed class SentLogOutDataState {
    object Idle : SentLogOutDataState()

    object Loading : SentLogOutDataState()

    object Success : SentLogOutDataState()

    object Error : SentLogOutDataState()
}