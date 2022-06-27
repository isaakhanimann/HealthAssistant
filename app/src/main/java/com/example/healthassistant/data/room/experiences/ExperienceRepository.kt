package com.example.healthassistant.data.room.experiences

import com.example.healthassistant.data.room.experiences.entities.Experience
import com.example.healthassistant.data.room.experiences.entities.Ingestion
import com.example.healthassistant.data.room.experiences.entities.SubstanceCompanion
import com.example.healthassistant.data.room.experiences.entities.SubstanceLastUsed
import com.example.healthassistant.data.room.experiences.relations.ExperienceWithIngestionsAndCompanions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExperienceRepository @Inject constructor(private val experienceDao: ExperienceDao) {
    suspend fun addExperience(experience: Experience) = experienceDao.insert(experience)
    suspend fun addIngestion(ingestion: Ingestion) = experienceDao.insert(ingestion)
    suspend fun updateExperience(experience: Experience) = experienceDao.update(experience)
    suspend fun updateIngestion(ingestion: Ingestion) = experienceDao.update(ingestion)

    suspend fun deleteIngestion(ingestion: Ingestion) = experienceDao.deleteIngestion(ingestion)

    suspend fun deleteExperience(experience: Experience) =
        experienceDao.deleteExperience(experience)

    fun getSortedExperiencesWithIngestionsFlow(): Flow<List<ExperienceWithIngestionsAndCompanions>> =
        experienceDao.getSortedExperiencesWithIngestionsFlow()
            .flowOn(Dispatchers.IO)
            .conflate()

    fun getExperiencesFlow(): Flow<List<Experience>> =
        experienceDao.getExperiencesFlow()
            .flowOn(Dispatchers.IO)
            .conflate()

    fun getSortedIngestionsFlow(): Flow<List<Ingestion>> =
        experienceDao.getIngestionsSortedDescendingFlow()
            .flowOn(Dispatchers.IO)
            .conflate()

    fun getSubstanceWithLastDateDescendingFlow(): Flow<List<SubstanceLastUsed>> =
        experienceDao.getSubstanceWithLastDateDescendingFlow()
            .flowOn(Dispatchers.IO)
            .conflate()

    fun getLastUsedSubstanceNamesFlow(limit: Int): Flow<List<String>> =
        experienceDao.getLastUsedSubstanceNamesFlow(limit).flowOn(Dispatchers.IO).conflate()

    suspend fun getExperience(id: Int): Experience? = experienceDao.getExperience(id)
    fun getIngestionFlow(id: Int) = experienceDao.getIngestionFlow(id)
    fun getIngestionWithCompanionFlow(id: Int) = experienceDao.getIngestionWithCompanionFlow(id)
    fun getExperienceWithIngestions(experienceId: Int) =
        experienceDao.getExperienceWithIngestionsFlow(experienceId)
            .flowOn(Dispatchers.IO)
            .conflate()

    suspend fun getLastIngestion(substanceName: String) =
        experienceDao.getLastIngestion(substanceName)

    suspend fun getIngestionAfterDate(date: Date): List<Ingestion> =
        experienceDao.getIngestionAfterDate(date)

    suspend fun addSubstanceCompanion(substanceCompanion: SubstanceCompanion) =
        experienceDao.insert(substanceCompanion)

    suspend fun deleteSubstanceCompanion(substanceCompanion: SubstanceCompanion) =
        experienceDao.delete(substanceCompanion)

    suspend fun updateSubstanceCompanion(substanceCompanion: SubstanceCompanion) =
        experienceDao.update(substanceCompanion)

    fun getSortedIngestionsWithSubstanceCompanionsFlow() =
        experienceDao.getSortedIngestionsWithSubstanceCompanionsFlow()
            .flowOn(Dispatchers.IO)
            .conflate()

    fun getAllSubstanceCompanionsFlow() = experienceDao.getAllSubstanceCompanions()
            .flowOn(Dispatchers.IO)
            .conflate()

    fun getSubstanceCompanionFlow(substanceName: String) = experienceDao.getSubstanceCompanionFlow(substanceName)
}