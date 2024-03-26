package com.example.travelbuddy.di

import com.example.travelbuddy.data.AuthRepositoryImpl
import com.example.travelbuddy.data.CurrencyExchangeRepositoryImpl
import com.example.travelbuddy.data.DestinationRepositoryImpl
import com.example.travelbuddy.repository.AuthRepository
import com.example.travelbuddy.repository.CurrencyExchangeRepository
import com.example.travelbuddy.repository.DestinationRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesCurrencyExchangeRepositoryImpl(): CurrencyExchangeRepository {
        return CurrencyExchangeRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesDestinationRepositoryImpl(
        authRepository: AuthRepository
    ): DestinationRepository {
        return DestinationRepositoryImpl(
            authRepository = authRepository
        )
    }
}