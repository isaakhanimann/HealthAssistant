package com.example.healthassistant.ui.ingestions.ingestion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthassistant.data.room.experiences.ExperienceRepository
import com.example.healthassistant.data.substances.repositories.SubstanceRepository
import com.example.healthassistant.ui.main.routers.INGESTION_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneIngestionViewModel @Inject constructor(
    private val experienceRepo: ExperienceRepository,
    private val substanceRepo: SubstanceRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val _ingestionWithDurationAndExperience =
        MutableStateFlow<IngestionWithDurationAndExperience?>(null)
    val ingestionWithDurationAndExperience = _ingestionWithDurationAndExperience.asStateFlow()

    init {
        val id = state.get<Int>(INGESTION_ID_KEY)!!
        viewModelScope.launch {
            experienceRepo.getIngestionFlow(id).collect { maybeNull ->
                val ingestion = maybeNull!!
                val experience =
                    if (ingestion.experienceId == null) null else experienceRepo.getExperience(
                        ingestion.experienceId
                    )
                _ingestionWithDurationAndExperience.value = IngestionWithDurationAndExperience(
                    ingestion,
                    roaDuration = substanceRepo.getSubstance(ingestion.substanceName)
                        ?.getRoa(ingestion.administrationRoute)?.roaDuration,
                    experience = experience
                )
            }
        }
    }
}