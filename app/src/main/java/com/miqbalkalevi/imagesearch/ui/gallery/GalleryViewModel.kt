package com.miqbalkalevi.imagesearch.ui.gallery

import androidx.lifecycle.ViewModel
import com.miqbalkalevi.imagesearch.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel()