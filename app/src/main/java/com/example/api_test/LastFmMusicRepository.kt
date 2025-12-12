package com.example.api_test

import com.example.api_test.data.lastFmApi.apiService.LastFmService
import com.example.api_test.data.lastFmApi.apiModels.AlbumResponse
import com.example.api_test.data.lastFmApi.apiModels.ArtistResponse
import com.example.api_test.data.lastFmApi.apiModels.TrackResponse

class LastFmMusicRepository(
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
