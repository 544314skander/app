package com.example.cat.ui

import androidx.lifecycle.ViewModel
import com.example.cat.data.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CatUiState(
    val cats: List<Cat> = emptyList(),
    val showOnlyFavorites: Boolean = false
) {
    val visibleCats: List<Cat>
        get() = if (showOnlyFavorites) cats.filter { it.isFavorite } else cats
}

class CatViewModel : ViewModel() {

    private val startingCats = listOf(
        Cat(
            id = 1,
            name = "Whiskers",
            model = "Siberian",
            status = "Napping",
            description = "Fluffy mountain cat who curls up wherever the warm spot is."
        ),
        Cat(
            id = 2,
            name = "Luna",
            model = "Bengal",
            status = "Exploring",
            description = "Spots a new corner every hour and chirps at birds from the window."
        ),
        Cat(
            id = 3,
            name = "Mochi",
            model = "Ragdoll",
            status = "Seeking snacks",
            description = "Follows the sound of treat bags and flops over for attention."
        ),
        Cat(
            id = 4,
            name = "Pixel",
            model = "Tabby",
            status = "Chasing laser",
            description = "Fast paws, quick turns, and relentless red-dot hunting skills."
        ),
        Cat(
            id = 5,
            name = "Cleo",
            model = "Sphynx",
            status = "Sunbathing",
            description = "Always finds the sunbeam and demands a soft blanket nearby."
        ),
        Cat(
            id = 6,
            name = "Nimbus",
            model = "Maine Coon",
            status = "Guarding the hall",
            description = "Looms like a fluffy lion and announces visitors with a chirp."
        )
    )

    private val _uiState = MutableStateFlow(CatUiState(cats = startingCats))
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()

    fun toggleFavorite(id: Int) {
        _uiState.update { state ->
            val updated = state.cats.map { cat ->
                if (cat.id == id) cat.copy(isFavorite = !cat.isFavorite) else cat
            }
            state.copy(cats = updated)
        }
    }

    fun toggleFavoritesFilter() {
        _uiState.update { state -> state.copy(showOnlyFavorites = !state.showOnlyFavorites) }
    }

    fun getCat(id: Int): Cat? = _uiState.value.cats.firstOrNull { it.id == id }
}
