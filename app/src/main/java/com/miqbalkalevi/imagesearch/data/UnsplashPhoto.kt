package com.miqbalkalevi.imagesearch.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhoto(
    val color: String? = null,
    val createdAt: String? = null,
    val description: String? = null,
    val likedByUser: Boolean? = null,
    val urls: UnsplashPhotoUrls? = null,
    val width: Int? = null,
    val blurHash: String? = null,
    val links: UnsplashUserLinks? = null,
    val id: String? = null,
    val user: UnsplashUser? = null,
    val height: Int? = null,
    val likes: Int? = null
) : Parcelable {

    @Parcelize
    data class UnsplashUserLinks(
        val self: String? = null,
        val html: String? = null,
        val photos: String? = null,
        val likes: String? = null,
        val download: String? = null
    ) : Parcelable

    @Parcelize
    data class UnsplashPhotoUrls(
        val small: String? = null,
        val thumb: String? = null,
        val raw: String? = null,
        val regular: String? = null,
        val full: String? = null
    ) : Parcelable

    @Parcelize
    data class UnsplashUserProfileImage(
        val small: String? = null,
        val large: String? = null,
        val medium: String? = null
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val profileImage: UnsplashUserProfileImage? = null,
        val name: String? = null,
        val twitterUsername: String? = null,
        val lastName: String? = null,
        val links: UnsplashUserLinks? = null,
        val id: String? = null,
        val firstName: String? = null,
        val instagramUsername: String? = null,
        val portfolioUrl: String? = null,
        val username: String? = null
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}