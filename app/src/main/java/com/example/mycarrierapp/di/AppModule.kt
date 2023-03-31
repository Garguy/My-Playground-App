package com.example.mycarrierapp.di

import android.content.Context
import android.content.pm.PackageManager
import com.example.mycarrierapp.data.AuthRepository
import com.example.mycarrierapp.data.AuthRepositoryImpl
import com.example.mycarrierapp.data.remote.PokemonApi
import com.example.mycarrierapp.di.repository.PokemonRepository
import com.example.mycarrierapp.di.repository.PokemonRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl

    @Provides
    @Singleton
    fun providePokemonRepository(pokemonApi: PokemonApi): PokemonRepository {
        return PokemonRepositoryImpl(pokemonApi)
    }

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pokeapi.co/api/v2/")
            .build()
            .create(PokemonApi::class.java)
    }

    //Should I remove this to an Application level Module?
    @Provides
    @ApplicationContext
    fun providesPackageManager(@ApplicationContext context: Context): PackageManager {
        return context.packageManager
    }
}