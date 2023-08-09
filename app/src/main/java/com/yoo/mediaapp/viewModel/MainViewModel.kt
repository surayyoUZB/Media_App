package com.yoo.mediaapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoo.mediaapp.database.Media
import com.yoo.mediaapp.repository.MediaRepository
import com.yoo.mediaapp.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    var mediaList= mutableStateListOf<Media>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllMedias().collectLatest {
                mediaList.clear()
                mediaList.addAll(it)
            }
        }
    }


    fun onEvent(mediaEvent:OnEvent){
        when(mediaEvent){
            is OnEvent.OnSaveMedia->{
                viewModelScope.launch(Dispatchers.IO){
                    repository.saveMedia(mediaEvent.media)
                }
            }
            is OnEvent.OnUpdateMedia->{
                viewModelScope.launch(Dispatchers.IO){
                    repository.saveMedia(
                        Media(
                            name = mediaEvent.media.name,
                            time = mediaEvent.media.time,
                            audio = mediaEvent.media.audio,
                            isSaved = !mediaEvent.media.isSaved!!,
                            id = mediaEvent.media.id
                        )
                    )
                }
            }
            is OnEvent.OnGetMediaById->{
                viewModelScope.launch(Dispatchers.IO){
                    repository.getMediaById(mediaEvent.media.id)
                }
            }
        }
    }
}