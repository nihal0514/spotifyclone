package com.example.spotifyclone.model

import com.google.gson.annotations.SerializedName

data class PopularArtistResp(

	@field:SerializedName("artists")
	val artists: List<ArtistsItem?>? = null
)

data class ArtistsItem(

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("followers")
	val followers: Followers? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("popularity")
	val popularity: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null,

	@field:SerializedName("uri")
	val uri: String? = null
)


data class ExternalUrls(

	@field:SerializedName("spotify")
	val spotify: String? = null
)

data class ImagesItem(

	@field:SerializedName("width")
	val width: Any? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Any? = null
)
