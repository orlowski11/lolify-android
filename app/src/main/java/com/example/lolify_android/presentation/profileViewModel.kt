package com.example.lolify_android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lolify_android.data.ChampionsRepository
import com.example.lolify_android.data.ProfileRepository
import com.example.lolify_android.data.Result
import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.data.model.Profile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class profileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {

    private val _profile = MutableStateFlow<Profile>(Profile(
        "",
        false,
        "",
        emptyList(),
        emptyList()
        ))
    val profile = _profile.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch{
            profileRepository.getUserProfile().collectLatest { result ->
                when(result){
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { profile ->
                            _profile.update { profile }
                        }
                    }
                }
            }
        }
    }
}