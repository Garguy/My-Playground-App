package com.example.mycarrierapp.ui.screens.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.mycarrierapp.data.ApiResource
import com.example.mycarrierapp.data.remote.dto.Pokemon
import com.example.mycarrierapp.di.repository.PokemonRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepositoryImpl
): ViewModel() {

    suspend fun getPokemonDetails(pokemonName: String): ApiResource<Pokemon> {
        return repository.getPokemonDetails(pokemonName)
    }
}