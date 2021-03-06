package com.isaakhanimann.healthassistant.data.room.experiences.entities

import java.util.*

data class SubstanceLastUsed(
    val substanceName: String,
    val lastUsed: Date,
    val color: SubstanceColor
)
