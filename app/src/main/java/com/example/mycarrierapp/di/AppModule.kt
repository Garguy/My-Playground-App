package com.example.mycarrierapp.di

import com.example.mycarrierapp.data.AuthRepository
import com.example.mycarrierapp.data.AuthRepositoryImpl
import com.example.mycarrierapp.data.remote.PokemonApi
import com.example.mycarrierapp.di.repository.PokemonRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl

    @Provides
    fun providePokemonRepository(pokemonApi: PokemonApi) = PokemonRepositoryImpl(pokemonApi)

    @Provides
    fun providePokemonApi(): PokemonApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pokeapi.co/api/v2/")
            .build()
            .create(PokemonApi::class.java)
    }
}