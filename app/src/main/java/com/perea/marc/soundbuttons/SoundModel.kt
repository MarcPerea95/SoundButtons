package com.perea.marc.soundbuttons

data class SoundModel(
        var id: Int?,
        var title: String?,
        var file: String?
)

data class SoundResponse(
        var sounds: ArrayList<SoundModel>?
)