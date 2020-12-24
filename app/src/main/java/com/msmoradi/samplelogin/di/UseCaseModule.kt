package com.msmoradi.samplelogin.di

import com.msmoradi.samplelogin.data.datasourceimpl.LocalUserDataSourceImpl
import com.msmoradi.samplelogin.repository.LoginRepository
import com.msmoradi.samplelogin.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {

    @Provides
    fun bindLoginRepository(
        localUserDataSource: LocalUserDataSourceImpl
    ): LoginRepository {
        return LoginRepository(localUserDataSource)
    }

    @Provides
    fun bindLoginUseCase(
        loginRepository: LoginRepository
    ): LoginUseCase {
        return LoginUseCase((loginRepository))
    }
}