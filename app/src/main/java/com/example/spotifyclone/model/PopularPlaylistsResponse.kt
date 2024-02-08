package com.example.spotifyclone.model

import com.google.gson.annotations.SerializedName

data class PopularPlaylists(

	@field:SerializedName("playlists")
	val playlists: Playlists? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ExternalUrlsPPResponse(

	@field:SerializedName("spotify")
	val spotify: String? = null
)

data class ImagesItemResponse(

	@field:SerializedName("width")
	val width: Any? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Any? = null
)

data class PlaylistItems(

	@field:SerializedName("owner")
	val owner: OwnerPPResponse? = null,

	@field:SerializedName("images")
	val images: List<ImagesItemResponse?>? = null,

	@field:SerializedName("snapshot_id")
	val snapshotId: String? = null,

	@field:SerializedName("collaborative")
	val collaborative: Boolean? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("primary_color")
	val primaryColor: Any? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("uri")
	val uri: String? = null,

	@field:SerializedName("tracks")
	val tracks: TracksPPResponse? = null,

	@field:SerializedName("public")
	val jsonMemberPublic: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrlsPPResponse? = null
)

data class TracksPPResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("href")
	val href: String? = null
)

data class Playlists(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("items")
	val items: List<PlaylistItems?>? = null
)

data class OwnerPPResponse(

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrlsPPResponse? = null,

	@field:SerializedName("uri")
	val uri: String? = null
)
