package com.msmoradi.samplelogin.di

import android.content.Context
import androidx.room.Room
import com.msmoradi.samplelogin.data.UserDao
import com.msmoradi.samplelogin.data.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): UserDataBase {
        return Room.databaseBuilder(
            appContext,
            UserDataBase::class.java,
            "users.db"
        ).build()
    }

    @Provides
    fun provideUserDao(database: UserDataBase): UserDao {
        return database.userDao()
    }
}
