// =============================================================================
// Arquivo: com.marin.catfeina.di.RepositoryModule.kt
// Descrição: Módulo Hilt dedicado a fornecer as implementações para as
//            interfaces de repositório da aplicação CATSFEINA.
// =============================================================================
package com.marin.catfeina.di

import com.marin.catfeina.data.repository.PoesiaRepository
import com.marin.catfeina.data.repository.PoesiaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt que define como as interfaces de repositório devem ser injetadas
 * em toda a aplicação CATSFEINA. Este módulo utiliza a anotação `@Binds` para informar
 * ao Hilt qual implementação concreta usar para uma determinada interface de repositório.
 *
 * As dependências fornecidas por este módulo são instaladas no [SingletonComponent],
 * o que significa que estarão disponíveis durante todo o ciclo de vida da aplicação
 * e, se escopadas como `@Singleton`, serão instâncias únicas.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Informa ao Hilt para usar [PoesiaRepositoryImpl] quando [PoesiaRepository] for injetado.
     * @param impl A implementação concreta do repositório de poesias.
     * @return A interface do repositório de poesias.
     */
    @Binds
    @Singleton
    abstract fun bindPoesiaRepository(
        impl: PoesiaRepositoryImpl
    ): PoesiaRepository

}
