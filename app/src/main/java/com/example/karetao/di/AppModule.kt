package com.example.karetao.di

import android.app.Application
import androidx.room.Room
import com.example.karetao.data.database.AppDatabase
import com.example.karetao.data.repository.FlashCardRepository
import com.example.karetao.data.use_case.*
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
    fun provideAppDatabase(app: Application): AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFlashCardRepository(db: AppDatabase): FlashCardRepository{
        return FlashCardRepository(db.flashCardDao)
    }

    @Provides
    @Singleton
    fun provideFlashCardUseCases(repository: FlashCardRepository): FlashCardUseCases{
        return FlashCardUseCases(
            getFlashCards = GetFlashCardsUseCase(repository),
            deleteFlashCard = DeleteFlashCard(repository),
            addFlashCard = AddFlashCard(repository),
            getFlashCard = GetFlashCardUseCase(repository)
        )
    }
}