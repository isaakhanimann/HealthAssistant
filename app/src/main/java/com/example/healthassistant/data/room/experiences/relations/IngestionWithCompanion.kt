package com.example.healthassistant.data.room.experiences.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.healthassistant.data.room.experiences.entities.Ingestion
import com.example.healthassistant.data.room.experiences.entities.SubstanceCompanion

data class IngestionWithCompanion(
    @Embedded
    var ingestion: Ingestion,

    @Relation(
        parentColumn = "substanceName",
        entityColumn = "substanceName"
    )
    var substanceCompanion: SubstanceCompanion?
)