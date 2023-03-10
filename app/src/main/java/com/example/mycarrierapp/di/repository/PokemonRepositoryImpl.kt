package com.example.mycarrierapp.di.repository

import com.example.mycarrierapp.data.ApiResource
import com.example.mycarrierapp.data.remote.PokemonApi
import com.example.mycarrierapp.data.remote.responses.Pokemon
import com.example.mycarrierapp.data.remote.responses.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi
) {

    suspend fun getPokemonList(limit: Int, offset: Int): ApiResource<PokemonList> {
        val response = try {
            pokemonApi.getPokemonList(limit, offset)
        } catch (e: Exception) {
            ApiResource.Failure(e)
        }
        return ApiResource.Success(response as PokemonList)
    }

    suspend fun getPokemonDetails(name: String): ApiResource<Pokemon> {
        val response = try {
            pokemonApi.getPokemonDetail(name)
        } catch (e: Exception) {
            ApiResource.Failure(e)
        }
        return ApiResource.Success(response as Pokemon)
    }
}