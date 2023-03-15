package com.example.mycarrierapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mycarrierapp.data.ApiResource
import com.example.mycarrierapp.data.remote.dto.PokemonList
import com.example.mycarrierapp.data.remote.dto.Result
import com.example.mycarrierapp.di.repository.PokemonRepository
import com.example.mycarrierapp.ui.screens.pokemonlist.PokemonListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(PokemonRepository::class.java)
    private lateinit var viewModel: PokemonListViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)

        viewModel = PokemonListViewModel(repository)
    }

    @Test
    fun `when Pokemon list is fetched successfully, PokemonList is updated`() = runBlocking {
        // Prepare test data
        val testData = createTestPokemonList()

        // Stub repository response
        `when`(repository.getPokemonList(Mockito.anyInt(), Mockito.anyInt())).thenReturn(
            ApiResource.Success(testData)
        )

        // Perform the operation
        viewModel.loadPokemonPaginated()

        // Assert values
        assert(viewModel.pokemonList.value.isNotEmpty())
        assert(viewModel.loadError.value.isEmpty())
        assert(!viewModel.isLoading.value)
    }

    // Add more tests for different scenarios

    private fun createTestPokemonList(): PokemonList {
        val result1 = Result(
            "Bulbasaur",
            "https://pokeapi.co/api/v2/pokemon/1/"
        )
        val result2 = Result(
            "Ivysaur",
            "https://pokeapi.co/api/v2/pokemon/2/"
        )
        val results = listOf(result1, result2)

        return PokemonList(
            count = 2,
            next = "https://pokeapi.co/api/v2/pokemon?offset=2&limit=2",
            previous = null,
            results = results
        )
    }
}