package com.example.api_test

import com.example.api_test.api.apiService.LastFmService
import com.example.api_test.api.apiModels.AlbumResponse
import com.example.api_test.api.apiModels.ArtistResponse
import com.example.api_test.api.apiModels.TrackResponse

class MusicRepository(
    private val api: LastFmService
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
