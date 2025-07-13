package com.starz.play.coding.challenge.core.ui.shared

import androidx.lifecycle.ViewModel
import com.starz.play.coding.domain.model.media.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedMediaViewModel @Inject constructor() : ViewModel() {

    private val _selectedMedia = MutableStateFlow<MediaItem?>(null)
    val selectedMedia: StateFlow<MediaItem?> = _selectedMedia.asStateFlow()

    fun select(item: MediaItem) {
        _selectedMedia.value = item
    }

    fun clearSelection() {
        _selectedMedia.value = null
    }
}