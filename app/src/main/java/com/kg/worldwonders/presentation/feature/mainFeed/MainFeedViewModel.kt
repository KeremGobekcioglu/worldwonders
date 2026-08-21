package com.kg.worldwonders.presentation.feature.mainFeed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kg.worldwonders.data.repository.WebcamRepositoryImpl
import com.kg.worldwonders.domain.repository.WebcamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val webcamRepository: WebcamRepository
) : ViewModel()
{
    private val _state = MutableStateFlow(MainFeedUIState())
    val state : StateFlow<MainFeedUIState> = _state.asStateFlow()
}