package com.example.api_test.lastFmViewModel

import com.example.api_test.lastFmApi.apiModels.AlbumResponse
import com.example.api_test.lastFmApi.apiModels.ArtistResponse
import com.example.api_test.lastFmApi.apiModels.TrackResponse
import com.example.api_test.lastFmApi.apiService.LastFmService

class MusicRepository(
    val api: LastFmService
) {

    /** Получить артиста */
    suspend fun getArtist(artist: String): ArtistResponse? {
        return api.getArtistInfo(artist)
    }

    /** Получить альбом */
    suspend fun getAlbum(artist: String, album: String): AlbumResponse? {
        return api.getAlbumInfo(artist, album)
    }

    /** Получить трек */
    suspend fun getTrack(artist: String, track: String): TrackResponse? {
        return api.getTrackInfo(artist, track)
    }
}