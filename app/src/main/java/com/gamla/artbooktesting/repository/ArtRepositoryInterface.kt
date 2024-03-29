package com.gamla.artbooktesting.repository

import androidx.lifecycle.LiveData
import com.gamla.artbooktesting.model.ImageResponse
import com.gamla.artbooktesting.roomdb.Art
import com.gamla.artbooktesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>

}