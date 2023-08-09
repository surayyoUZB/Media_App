package com.yoo.mediaapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveMedia(media: Media)

    @Update
    fun updateMedia(media: Media)

    @Query("select * from Media order by id desc")
    fun getAllMedia(): Flow<List<Media>>

    @Query("select * from Media where id = :id")
    fun getMediaById(id: Int): Flow<Media?>

}