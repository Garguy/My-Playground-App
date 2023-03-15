package com.example.mycarrierapp.di.repository

import com.example.mycarrierapp.data.ApiResource
import com.example.mycarrierapp.data.remote.dto.Pokemon
import com.example.mycarrierapp.data.remote.dto.PokemonList

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): ApiResource<PokemonList>

    suspend fun getPokemonDetails(name: String): ApiResource<Pokemon>
}