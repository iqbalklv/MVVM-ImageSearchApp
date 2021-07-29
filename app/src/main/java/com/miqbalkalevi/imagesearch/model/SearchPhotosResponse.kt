package com.miqbalkalevi.imagesearch.model

data class Response(
	val total: Int? = null,
	val totalPages: Int? = null,
	val results: List<UnsplashPhoto?>? = null
)


