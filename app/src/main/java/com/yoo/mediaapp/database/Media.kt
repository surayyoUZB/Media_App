package com.yoo.mediaapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Media(
    val name:String="",
    val time: String="",
    val audio:Int=0,
    val isSaved:Boolean?=false,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
):Parcelable
