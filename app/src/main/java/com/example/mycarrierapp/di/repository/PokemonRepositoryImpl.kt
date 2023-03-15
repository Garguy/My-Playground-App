package com.example.mycarrierapp.di.repository

import com.example.mycarrierapp.data.ApiResource
import com.example.mycarrierapp.data.remote.PokemonApi
import com.example.mycarrierapp.data.remote.dto.Pokemon
import com.example.mycarrierapp.data.remote.dto.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi
): PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): ApiResource<PokemonList> {
        val response = try {
            pokemonApi.getPokemonList(limit, offset)
        } catch (e: Exception) {
            ApiResource.Failure(e)
        }

        return ApiResource.Success(response as PokemonList)
    }

    override suspend fun getPokemonDetails(name: String): ApiResource<Pokemon> {
        val response = try {
            pokemonApi.getPokemonDetail(name)
        } catch (e: Exception) {
            ApiResource.Failure(e)
        }
        return ApiResource.Success(response as Pokemon)
    }
}