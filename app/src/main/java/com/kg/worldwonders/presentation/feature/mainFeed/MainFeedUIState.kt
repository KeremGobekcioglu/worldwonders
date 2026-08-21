package com.kg.worldwonders.presentation.feature.mainFeed

import com.kg.worldwonders.domain.model.Webcam

data class MainFeedUIState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
    val webcams: List<Webcam> = emptyList()
)
