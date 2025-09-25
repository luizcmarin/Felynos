/*
 * Arquivo: com.marin.catfeina.data.dao.InformativoDao.kt
 * @project Catfeina
 * @description Define as operações de acesso a dados (DAO) para a entidade Informativo.
 *              Fornece métodos para interagir com a tabela 'informativos' no banco de dados.
 */package com.marin.catfeina.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.marin.catfeina.data.entity.InformativoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InformativoDao {

    /**
     * Busca um informativo específico pela sua chave única.
     * Retorna um Flow que emite o [InformativoEntity] correspondente quando encontrado,
     * ou null se nenhum informativo com a chave especificada existir.
     * O Flow emitirá um novo valor sempre que os dados do informativo mudarem.
     *
     * @param chave A chave única do informativo a ser buscado.
     * @return Um [Flow] que emite o [InformativoEntity] ou null.
     */
    @Query("SELECT * FROM informativos WHERE chave = :chave LIMIT 1")
    fun getInformativoPorChave(chave: String): Flow<InformativoEntity?>

}

