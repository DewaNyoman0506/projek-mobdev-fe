package com.leksin.app.di

import com.leksin.app.data.repository.AuthRepositoryImpl
import com.leksin.app.data.repository.ChildAuthRepositoryImpl
import com.leksin.app.data.repository.ChildRepositoryImpl
import com.leksin.app.data.repository.GameRepositoryImpl
import com.leksin.app.data.repository.ParentRepositoryImpl
import com.leksin.app.domain.repository.AuthRepository
import com.leksin.app.domain.repository.ChildAuthRepository
import com.leksin.app.domain.repository.ChildRepository
import com.leksin.app.domain.repository.GameRepository
import com.leksin.app.domain.repository.ParentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindParentRepository(
        implementation: ParentRepositoryImpl,
    ): ParentRepository

    @Binds
    abstract fun bindChildRepository(
        implementation: ChildRepositoryImpl,
    ): ChildRepository

    @Binds
    abstract fun bindGameRepository(
        implementation: GameRepositoryImpl,
    ): GameRepository

    @Binds
    abstract fun bindAuthRepository(
        implementation: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun bindChildAuthRepository(
        implementation: ChildAuthRepositoryImpl,
    ): ChildAuthRepository
}
