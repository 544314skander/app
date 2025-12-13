package com.example.droid.ui

import androidx.lifecycle.ViewModel
import com.example.droid.data.Droid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DroidUiState(
    val droids: List<Droid> = emptyList(),
    val showOnlyFavorites: Boolean = false
) {
    val visibleDroids: List<Droid>
        get() = if (showOnlyFavorites) droids.filter { it.isFavorite } else droids
}

class DroidViewModel : ViewModel() {

    private val startingDroids = listOf(
        Droid(
            id = 1,
            name = "Whiskers",
            model = "Siberian",
            status = "Napping",
            description = "Fluffy mountain cat who curls up wherever the warm spot is."
        ),
        Droid(
            id = 2,
            name = "Luna",
            model = "Bengal",
            status = "Exploring",
            description = "Spots a new corner every hour and chirps at birds from the window."
        ),
        Droid(
            id = 3,
            name = "Mochi",
            model = "Ragdoll",
            status = "Seeking snacks",
            description = "Follows the sound of treat bags and flops over for attention."
        ),
        Droid(
            id = 4,
            name = "Pixel",
            model = "Tabby",
            status = "Chasing laser",
            description = "Fast paws, quick turns, and relentless red-dot hunting skills."
        ),
        Droid(
            id = 5,
            name = "Cleo",
            model = "Sphynx",
            status = "Sunbathing",
            description = "Always finds the sunbeam and demands a soft blanket nearby."
        ),
        Droid(
            id = 6,
            name = "Nimbus",
            model = "Maine Coon",
            status = "Guarding the hall",
            description = "Looms like a fluffy lion and announces visitors with a chirp."
        )
    )

    private val _uiState = MutableStateFlow(DroidUiState(droids = startingDroids))
    val uiState: StateFlow<DroidUiState> = _uiState.asStateFlow()

    fun toggleFavorite(id: Int) {
        _uiState.update { state ->
            val updated = state.droids.map { droid ->
                if (droid.id == id) droid.copy(isFavorite = !droid.isFavorite) else droid
            }
            state.copy(droids = updated)
        }
    }

    fun toggleFavoritesFilter() {
        _uiState.update { state -> state.copy(showOnlyFavorites = !state.showOnlyFavorites) }
    }

    fun getDroid(id: Int): Droid? = _uiState.value.droids.firstOrNull { it.id == id }
}
