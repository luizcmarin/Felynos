// =============================================================================
// Arquivo: com.marin.catfeina.data.dao.TextoDao.kt
// Descrição: Data Access Object (DAO) para a entidade TextoEntity.
//            Define os métodos para interagir com a tabela 'textos', focando
//            na busca por chave e na inserção/atualização de dados.
// =============================================================================

package com.marin.catfeina.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.marin.catfeina.data.entity.TextoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para a entidade [TextoEntity].
 *
 * Fornece métodos para acessar e manipular dados da tabela 'textos',
 * com foco principal na recuperação de textos por sua chave única e
 * na capacidade de inserir ou atualizar esses textos (upsert).
 */
@Dao
interface TextoDao {

    /**
     * Insere um [TextoEntity] no banco de dados. Se o texto já existir
     * (com base na chave primária 'id' ou no índice único 'chave'),
     * ele será atualizado.
     *
     * Essencial para a carga inicial de dados e para a funcionalidade de
     * atualização dinâmica de conteúdo.
     *
     * @param texto O [TextoEntity] a ser inserido ou atualizado.
     */
    @Upsert
    suspend fun upsertTexto(texto: TextoEntity)

    /**
     * Recupera um [TextoEntity] do banco de dados com base na sua [TextoEntity.chave] única.
     * Retorna um Flow que emite o [TextoEntity] encontrado ou null se não existir.
     * O Flow emitirá um novo valor sempre que os dados do texto correspondente mudarem.
     *
     * Este é o método principal para obter o conteúdo a ser exibido (ex: política de
     * privacidade, termos de uso) quando um item de menu é selecionado.
     *
     * @param chave A chave única do texto a ser recuperado (ex: "privacidade", "termos_de_uso").
     * @return Um [Flow] que emite o [TextoEntity] correspondente ou null.
     */
    @Query("SELECT * FROM textos WHERE chave = :chave LIMIT 1")
    fun getTextoPorChave(chave: String): Flow<TextoEntity?>

}

