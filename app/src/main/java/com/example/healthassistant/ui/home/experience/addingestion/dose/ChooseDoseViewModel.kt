package com.example.healthassistant.ui.home.experience.addingestion.dose

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.healthassistant.data.substances.AdministrationRoute
import com.example.healthassistant.data.substances.Substance
import com.example.healthassistant.data.substances.repositories.SubstanceRepository
import com.example.healthassistant.ui.main.routers.ADMINISTRATION_ROUTE_KEY
import com.example.healthassistant.ui.main.routers.SUBSTANCE_NAME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseDoseViewModel @Inject constructor(
    repository: SubstanceRepository,
    state: SavedStateHandle
) : ViewModel() {
    val substance: Substance?
    val administrationRoute: AdministrationRoute?

    init {
        substance = repository.getSubstance(state.get<String>(SUBSTANCE_NAME_KEY) ?: "LSD")
        val routeString = state.get<String>(ADMINISTRATION_ROUTE_KEY)
        administrationRoute = try {
            AdministrationRoute.valueOf(routeString ?: "")
        } catch (e: Exception) {
            null
        }
        assert(substance != null)
        assert(administrationRoute != null)
    }

}