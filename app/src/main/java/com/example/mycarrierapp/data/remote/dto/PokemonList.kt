package com.example.mycarrierapp.data.remote.dto

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any?,
    val results: List<Result>
)