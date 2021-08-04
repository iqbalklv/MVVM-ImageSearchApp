package com.miqbalkalevi.imagesearch.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miqbalkalevi.imagesearch.data.UnsplashPhoto
import com.miqbalkalevi.imagesearch.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    private val galleryEventChannel = Channel<GalleryEvent>()
    val galleryEvent = galleryEventChannel.receiveAsFlow()

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = currentQuery.switchMap { query ->
        repository.getSearchResults(query).cachedIn(viewModelScope)
    }

    fun onSearchMenuSubmit(query: String) {
        currentQuery.value = query
    }

    fun onPhotoClicked(photo: UnsplashPhoto) = viewModelScope.launch {
        galleryEventChannel.send(GalleryEvent.NavigateToDetailsScreen(photo))
    }


    companion object {
        private const val DEFAULT_QUERY = "star wars"
    }

    sealed class GalleryEvent {
        data class NavigateToDetailsScreen(val photo: UnsplashPhoto) : GalleryEvent()
    }
}