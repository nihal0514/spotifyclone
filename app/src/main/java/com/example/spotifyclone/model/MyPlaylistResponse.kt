package com.example.spotifyclone.model

import com.google.gson.annotations.SerializedName

data class MyPlaylistResponse(

	@field:SerializedName("next")
	val next: Any? = null,

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

data class Owner(

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: PlaylistExternalUrl? = null,

	@field:SerializedName("uri")
	val uri: String? = null
)

data class PlaylistTracks(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("href")
	val href: String? = null
)

data class PlaylistExternalUrl(

	@field:SerializedName("spotify")
	val spotify: String? = null
)

/*data class PlaylistItems(

	@field:SerializedName("owner")
	val owner: Owner? = null,

	@field:SerializedName("images")
	val images: List<PlaylistImages?>? = null,

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
	val tracks: PlaylistTracks? = null,

	@field:SerializedName("public")
	val jsonMemberPublic: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: PlaylistExternalUrl? = null
)*/

data class PlaylistImages(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
