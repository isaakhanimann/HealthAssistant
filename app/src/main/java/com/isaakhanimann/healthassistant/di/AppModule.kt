package com.isaakhanimann.healthassistant.di

import android.content.Context
import androidx.room.Room
import com.isaakhanimann.healthassistant.data.room.AppDatabase
import com.isaakhanimann.healthassistant.data.room.experiences.ExperienceDao
import com.isaakhanimann.healthassistant.data.substances.PsychonautWikiAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePsychonautWikiAPI(): PsychonautWikiAPI {
        return Retrofit
            .Builder()
            .baseUrl("https://api.psychonautwiki.org/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(PsychonautWikiAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideExperiencesDao(appDatabase: AppDatabase): ExperienceDao =
        appDatabase.experienceDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "experiences_db"
        ).build()
}