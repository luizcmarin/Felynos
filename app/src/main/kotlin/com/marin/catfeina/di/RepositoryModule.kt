/*
 * Arquivo: com.marin.catfeina.di.RepositoryModule.kt
 * @project Catfeina
 * @description Módulo Hilt para fornecer dependências relacionadas aos repositórios da aplicação.
 *              Este módulo é responsável por fazer o binding das interfaces dos repositórios
 *              para suas implementações concretas, permitindo a injeção de dependência
 *              dessas interfaces em outras partes do aplicativo (ex: ViewModels).
 */
package com.marin.catfeina.di

import com.marin.catfeina.data.repository.InformativoRepository
import com.marin.catfeina.data.repository.InformativoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para fornecer dependências relacionadas aos repositórios da aplicação.
 * Inclui o binding da interface [InformativoRepository] para sua implementação [InformativoRepositoryImpl].
 *
 * Este módulo é instalado no [SingletonComponent], o que significa que os repositórios
 * aqui vinculados podem ser escopados como singletons e estarão disponíveis em toda a aplicação.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Faz o binding da interface [InformativoRepository] para sua implementação concreta [InformativoRepositoryImpl].
     * Quando um [InformativoRepository] é injetado, o Hilt fornecerá uma instância de [InformativoRepositoryImpl].
     *
     * A anotação [Singleton] garante que apenas uma instância do repositório será criada e
     * reutilizada em toda a aplicação.
     *
     * @param impl A implementação concreta do repositório ([InformativoRepositoryImpl]).
     *             O Hilt sabe como criar esta implementação devido ao seu construtor anotado com `@Inject`.
     * @return Uma instância de [InformativoRepository].
     */
    @Binds
    @Singleton
    abstract fun bindInformativoRepository(
        impl: InformativoRepositoryImpl
    ): InformativoRepository

    // Adicione os bindings para outros repositórios aqui também, seguindo o mesmo padrão.
    // Exemplo:
    // /**
    //  * Faz o binding da interface [MeuOutroRepository] para sua implementação [MeuOutroRepositoryImpl].
    //  * @param meuOutroRepositoryImpl A implementação concreta do outro repositório.
    //  * @return Uma instância de [MeuOutroRepository].
    //  */
    // @Binds
    // @Singleton
    // abstract fun bindMeuOutroRepository(
    //     meuOutroRepositoryImpl: MeuOutroRepositoryImpl
    // ): MeuOutroRepository
}
