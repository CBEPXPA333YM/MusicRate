package com.example.api_test.lastFmApi.apiModels
import com.example.api_test.lastFmApi.apiService.TrackListDeserializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    val album: Album?
)

data class Album(
    val name: String?,
    val artist: String?,
    val url: String?,
    val image: List<AlbumImage>?,
    val listeners: String?,
    val playcount: String?,
    val tracks: Tracks?,
    val wiki: Wiki?
)

data class AlbumImage(
    @SerializedName("#text")
    val url: String?,
    val size: String?
)

data class Tracks(
    @JsonAdapter(TrackListDeserializer::class)
    val track: List<Track> = emptyList()
)

data class Track(
    val name: String?,
    val duration: String?,
    val url: String?
)

data class Wiki(
    val published: String?,
    val summary: String?,
    val content: String?
)
