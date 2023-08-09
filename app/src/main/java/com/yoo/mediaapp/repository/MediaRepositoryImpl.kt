package com.yoo.mediaapp.repository

import com.yoo.mediaapp.database.Media
import com.yoo.mediaapp.database.MediaDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaDao: MediaDao
):MediaRepository {
    override fun saveMedia(media: Media) {
        mediaDao.saveMedia(media)
    }

    override fun updateMedia(media: Media) {
        mediaDao.updateMedia(media)
    }

    override fun getMediaById(id: Int): Flow<Media?> {
        return mediaDao.getMediaById(id)
    }

    override fun getAllMedias(): Flow<List<Media>> {
        return mediaDao.getAllMedia()
    }
}