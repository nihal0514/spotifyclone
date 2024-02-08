package com.example.spotifyclone.model

import com.google.gson.annotations.SerializedName

data class CurrentProfileresponse(

	@field:SerializedName("images")
	val images: List<Any?>? = null,

	@field:SerializedName("followers")
	val followers: Followers? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null,

	@field:SerializedName("uri")
	val uri: String? = null
)
