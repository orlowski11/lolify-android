package com.example.lolify_android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lolify_android.data.ChampionsRepository
import com.example.lolify_android.data.Result
import com.example.lolify_android.data.model.Champion
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class championListViewModel(
    private val championsRepository: ChampionsRepository
): ViewModel() {

    private val _champions = MutableStateFlow<List<Champion>>(emptyList())
    val champions = _champions.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch{
            championsRepository.getChampionList().collectLatest { result ->
                when(result){
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { champions ->
                            _champions.update { champions }
                        }
                    }
                }
            }
        }
    }
}