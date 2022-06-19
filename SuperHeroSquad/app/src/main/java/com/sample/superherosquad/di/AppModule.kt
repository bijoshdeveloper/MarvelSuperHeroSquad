package com.sample.superherosquad.di

import android.app.Application
import androidx.room.Room
import com.sample.superherosquad.model.local_source.AppDatabase
import com.sample.superherosquad.model.remote_source.api.ApiService
import com.sample.superherosquad.model.repository.DetailRepository
import com.sample.superherosquad.model.repository.HomeRepository
import com.sample.superherosquad.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for dependency injection.
 **/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDetailRepository(apiService: ApiService, db: AppDatabase): DetailRepository {
        return DetailRepository(apiService = apiService, characterDao = db.characterDao)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(apiService: ApiService, db: AppDatabase): HomeRepository {
        return HomeRepository(apiService = apiService, characterDao = db.characterDao)
    }
}