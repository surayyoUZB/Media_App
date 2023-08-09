package com.yoo.mediaapp.repository

import com.yoo.mediaapp.database.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository{
        fun saveMedia(media: Media)
        fun updateMedia(media: Media)
        fun getMediaById(id:Int):Flow<Media?>
        fun getAllMedias():Flow<List<Media>>
}