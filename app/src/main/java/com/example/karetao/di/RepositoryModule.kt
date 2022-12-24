package com.example.karetao.di

import com.example.karetao.data.database.AppDatabase
import com.example.karetao.data.repository.FlashCardRepositoryImpl
import com.example.karetao.data.repositoryinterface.FlashCardRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFlashCardRepository(db: AppDatabase): FlashCardRepository {
        return FlashCardRepositoryImpl(db.flashCardDao)
    }
}