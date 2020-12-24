package com.msmoradi.samplelogin.di

import com.msmoradi.samplelogin.data.datasourceimpl.LocalUserDataSourceImpl
import com.msmoradi.samplelogin.repository.LoginRepository
import com.msmoradi.samplelogin.repository.UserRepository
import com.msmoradi.samplelogin.usecase.LoginUseCase
import com.msmoradi.samplelogin.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginRepository(
        localUserDataSource: LocalUserDataSourceImpl
    ): LoginRepository {
        return LoginRepository(localUserDataSource)
    }

    @Provides
    fun provideUserRepository(
        localUserDataSource: LocalUserDataSourceImpl
    ): UserRepository {
        return UserRepository(localUserDataSource)
    }

    @Provides
    fun provideLoginUseCase(
        loginRepository: LoginRepository
    ): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    fun provideSignUpUseCase(
        userRepository: UserRepository
    ): SignUpUseCase {
        return SignUpUseCase(userRepository)
    }
}