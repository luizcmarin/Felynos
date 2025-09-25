/*
 * Arquivo: com.marin.catfeina.data.repository.InformativoRepository.kt
 * @project Catfeina
 * @description Define a interface e implementação do repositório para os dados de Informativos.
 *              Este repositório é responsável por fornecer acesso aos dados
 *              dos informativos, abstraindo a origem (Room DAO) e transformando
 *              entidades em modelos de domínio.
 */
package com.marin.catfeina.data.repository

import com.marin.catfeina.data.dao.InformativoDao
import com.marin.catfeina.data.entity.Informativo
import com.marin.catfeina.data.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interface para o repositório que gerencia os dados de Informativos.
 */
interface InformativoRepository {
    /**
     * Busca um informativo específico pela sua chave única.
     *
     * @param chave A chave única do informativo a ser buscado.
     * @return Um [Flow] que emite o [Informativo] (modelo de domínio) ou null se não encontrado.
     */
    fun getInformativoPorChave(chave: String): Flow<Informativo?>
}

/**
 * Implementação padrão de [InformativoRepository] que utiliza [InformativoDao].
 *
 * @param informativoDao DAO para acesso aos dados dos informativos no banco de dados.
 */
@Singleton
class InformativoRepositoryImpl @Inject constructor(
    private val informativoDao: InformativoDao
) : InformativoRepository {

    override fun getInformativoPorChave(chave: String): Flow<Informativo?> {
        return informativoDao.getInformativoPorChave(chave).map { entity ->
            entity?.toDomain()
        }
    }
}

