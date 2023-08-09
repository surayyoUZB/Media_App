package com.yoo.mediaapp.viewModel

import com.yoo.mediaapp.database.Media

sealed interface OnEvent {
    data class OnSaveMedia(val media: Media):OnEvent
    data class OnUpdateMedia(val media: Media):OnEvent
    data class OnGetMediaById(val media: Media):OnEvent
}