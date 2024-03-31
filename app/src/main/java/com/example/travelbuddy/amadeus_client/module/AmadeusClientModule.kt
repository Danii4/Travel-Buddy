package com.example.travelbuddy.amadeus_client.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.travelbuddy.amadeus_client.AmadeusClient

@Module
@InstallIn(SingletonComponent::class)
object SpeechRecognitionModule {

    @Singleton
    @Provides
    fun provideSpeechRecognizer(): AmadeusClient {
        return AmadeusClient()
    }

}