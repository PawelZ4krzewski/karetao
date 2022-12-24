package com.example.karetao.di

import android.app.Application
import androidx.room.Room
import com.example.karetao.data.DAO.CardGroupDao
import com.example.karetao.data.DAO.FlashCardDao
import com.example.karetao.data.DAO.UserCardDao
import com.example.karetao.data.database.AppDatabase
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
    fun provideCardGroupDao(db: AppDatabase): CardGroupDao{
        return db.cardGroupDao
    }

    @Provides
    @Singleton
    fun provideFlashCardDao(db: AppDatabase): FlashCardDao {
        return db.flashCardDao
    }

    @Provides
    @Singleton
    fun provideUserCardDao(db: AppDatabase): UserCardDao {
        return db.userCardDao
    }


}