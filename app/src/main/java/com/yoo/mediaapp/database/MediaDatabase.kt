package com.yoo.mediaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Media::class], version = 1, exportSchema = false )
abstract class MediaDatabase: RoomDatabase(){

    abstract val dao: MediaDao

//    companion object{
//        @Volatile
//        private var instance:MediaDatabase?=null
//
//        operator fun invoke(context: Context) = instance?: synchronized(Any()){
//            instance?:createDatabase(context).also{
//                instance = it
//            }
//        }
//
//        private fun createDatabase(context: Context):MediaDatabase{
//            return Room.databaseBuilder(
//                context.applicationContext,
//                MediaDatabase::class.java,
//                "media.db"
//            ).fallbackToDestructiveMigration().build()
//        }
//
//    }
}