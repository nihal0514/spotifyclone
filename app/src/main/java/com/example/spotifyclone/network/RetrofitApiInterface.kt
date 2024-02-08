package com.example.spotifyclone.network

import com.example.spotifyclone.model.ArtistDetailResponse
import com.example.spotifyclone.model.ArtistTopTracksResponse
import com.example.spotifyclone.model.CurrentProfileresponse
import com.example.spotifyclone.model.MyPlaylistResponse
import com.example.spotifyclone.model.PlaylistDetailResponse
import com.example.spotifyclone.model.PopularArtistResp
import com.example.spotifyclone.model.PopularArtistResponse
import com.example.spotifyclone.model.PopularPlaylists
import com.example.spotifyclone.model.SearchResponse
import com.example.spotifyclone.model.TokenResponse
import com.example.spotifyclone.utils.ConstVariables
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query



interface RetrofitApiInterface {

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getToken(@Field("grant_type") grantType: String= "client_credentials",
                         @Field("client_id") clientId: String = "5853bd990b854a4a8a364817b56aa5c4",
                         @Field("client_secret") clientSecret: String ="6bb8c8d98d30487ebbfdde7b18975dc2"): TokenResponse



    @GET("v1/albums?ids=382ObEPsp2rxGrnsizN5TX%2C1A2GTWGtFfWp7KSQTwWOyo%2C2noRn2Aes5aoNVsU6iWThc")
    suspend fun getPopularArtist(@Header("Authorization") token : String= ConstVariables.api_key ): PopularArtistResponse

    @GET("v1/me/playlists")
    suspend fun getMyPlaylist(@Header("Authorization") token : String=ConstVariables.api_key ): MyPlaylistResponse

    @GET("v1/playlists/{id}/tracks")
    suspend fun getMyPlaylistDetail(@Path("id") id: String, @Header("Authorization") token : String=ConstVariables.api_key ): PlaylistDetailResponse

    @GET("/v1/search")
    suspend fun getTrackSearch( @Query("q") query: String,
                                @Query("type") type: String = "track", @Header("Authorization") token : String=ConstVariables.api_key ): SearchResponse

    @GET("v1/browse/featured-playlists")
    suspend fun getPopularPlaylists( @Header("Authorization") token : String=ConstVariables.api_key ): PopularPlaylists

    /*@GET("v1/artists/1wRPtKGflJrBx9BmLsSwlU")
    suspend fun getArtistDetails( @Header("Authorization") token : String=ConstVariables.api_key ): PopularPlaylists
*/

    @GET("v1/artists?ids=1wRPtKGflJrBx9BmLsSwlU%2C1vCWHaC5f2uS3yhpwWbIA6")
    suspend fun getPopularArtists( @Header("Authorization") token : String=ConstVariables.api_key ): PopularArtistResp


    @GET("v1/artists/{id}")
    suspend fun getArtistDetails(@Path("id") id:String,  @Header("Authorization") token : String=ConstVariables.api_key ): ArtistDetailResponse

    @GET("v1/artists/{id}/top-tracks?country=IN")
    suspend fun getArtistTopTracks(@Path("id") id:String,  @Header("Authorization") token : String=ConstVariables.api_key ): ArtistTopTracksResponse

    @GET("v1/me")
    suspend fun getCurrentUserProfile( @Header("Authorization") token : String=ConstVariables.api_key ): CurrentProfileresponse

}