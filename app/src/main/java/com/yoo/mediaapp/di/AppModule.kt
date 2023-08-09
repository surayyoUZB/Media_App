package com.yoo.mediaapp.di

import android.content.Context
import androidx.room.Room
import com.yoo.mediaapp.database.MediaDao
import com.yoo.mediaapp.database.MediaDatabase
import com.yoo.mediaapp.repository.MediaRepository
import com.yoo.mediaapp.repository.MediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Provides Singleton]
    fun provideMediaDatabase(
        @ApplicationContext context: Context
    ):MediaDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            MediaDatabase::class.java,
            "media.db"
        ).fallbackToDestructiveMigration().build()
    }
    @[Provides Singleton]
    fun provideDao(database: MediaDatabase):MediaDao{
        return database.dao
    }

    @Provides
    @Singleton
    fun provideMediaRepository(mediaDao: MediaDao):MediaRepository{
        return MediaRepositoryImpl(mediaDao)
    }


}