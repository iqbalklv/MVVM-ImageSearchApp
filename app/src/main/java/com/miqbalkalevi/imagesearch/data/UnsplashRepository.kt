package com.miqbalkalevi.imagesearch.data

import com.miqbalkalevi.imagesearch.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi)