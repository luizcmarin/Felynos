// =============================================================================
// Arquivo: com.marin.catfeina.data.dao.PoesiaDao.kt
// Descrição: DAO para operações na tabela 'poesias'.
// =============================================================================
package com.marin.catfeina.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marin.catfeina.data.entity.PoesiaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PoesiaDao {

    /**
     * Insere uma nova poesia no banco de dados. Se já existir uma poesia com o mesmo ID,
     * ela será substituída.
     * @param poesia A entidade de poesia a ser inserida ou atualizada.
     * @return O ID da linha da poesia inserida ou atualizada.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirOuAtualizar(poesia: PoesiaEntity): Long

    /**
     * Insere uma lista de poesias no banco de dados. Se alguma poesia já existir (mesmo ID),
     * será substituída.
     * @param poesias Lista de entidades de poesia a serem inseridas ou atualizadas.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTodas(poesias: List<PoesiaEntity>)

    /**
     * Atualiza uma poesia existente no banco de dados.
     * @param poesia A entidade de poesia a ser atualizada.
     */
    @Update
    suspend fun atualizar(poesia: PoesiaEntity)

    /**
     * Exclui uma poesia do banco de dados.
     * @param poesia A entidade de poesia a ser excluída.
     */
    @Delete
    suspend fun excluir(poesia: PoesiaEntity)

    /**
     * Exclui todas as poesias da tabela.
     */
    @Query("DELETE FROM poesias")
    suspend fun excluirTodas()

    /**
     * Retorna todas as poesias do banco de dados, ordenadas pela data de criação (mais recentes primeiro).
     * @return Um Flow contendo uma lista de todas as PoesiaEntity.
     */
    @Query("SELECT * FROM poesias ORDER BY data_criacao DESC")
    fun obterTodas(): Flow<List<PoesiaEntity>>

    /**
     * Retorna uma poesia específica do banco de dados pelo seu ID.
     * @param id O ID da poesia a ser buscada.
     * @return A PoesiaEntity com o ID correspondente, ou null se não for encontrada.
     */
    @Query("SELECT * FROM poesias WHERE id = :id")
    suspend fun obterPorId(id: Long): PoesiaEntity?

    /**
     * Retorna um Flow de uma poesia específica do banco de dados pelo seu ID.
     * Útil para observar mudanças em uma poesia específica.
     * @param id O ID da poesia a ser buscada.
     * @return Um Flow contendo a PoesiaEntity com o ID correspondente, ou null.
     */
    @Query("SELECT * FROM poesias WHERE id = :id")
    fun observarPorId(id: Long): Flow<PoesiaEntity?>

    /**
     * Retorna todas as poesias de uma categoria específica, ordenadas pela data de criação.
     * @param categoria A categoria (String correspondente ao Enum.name) para filtrar as poesias.
     * @return Um Flow contendo uma lista de PoesiaEntity da categoria especificada.
     */
    @Query("SELECT * FROM poesias WHERE categoria = :categoria ORDER BY data_criacao DESC")
    fun obterPorCategoria(categoria: String): Flow<List<PoesiaEntity>>
    // Sobrecarga para usar o Enum diretamente (requer que o Enum seja passado como String)
    // fun obterPorCategoria(categoria: CategoriaPoesiaEnum): Flow<List<PoesiaEntity>> =
    //    obterPorCategoria(categoria.name)


    /**
     * Retorna todas as poesias marcadas como favoritas, ordenadas pela data em que foram favoritadas (mais recentes primeiro).
     * @return Um Flow contendo uma lista de PoesiaEntity favoritas.
     */
    @Query("SELECT * FROM poesias WHERE data_favoritado IS NOT NULL ORDER BY data_favoritado DESC")
    fun obterFavoritas(): Flow<List<PoesiaEntity>>

    /**
     * Retorna todas as poesias marcadas como lidas, ordenadas pela data em que foram lidas (mais recentes primeiro).
     * @return Um Flow contendo uma lista de PoesiaEntity lidas.
     */
    @Query("SELECT * FROM poesias WHERE data_leitura IS NOT NULL ORDER BY data_leitura DESC")
    fun obterLidas(): Flow<List<PoesiaEntity>>

    /**
     * Marca uma poesia como favorita atualizando seu campo data_favoritado.
     * @param poesiaId O ID da poesia a ser favoritada.
     * @param timestamp O momento atual em que foi favoritada.
     */
    @Query("UPDATE poesias SET data_favoritado = :timestamp WHERE id = :poesiaId")
    suspend fun marcarComoFavorita(poesiaId: Long, timestamp: Long)

    /**
     * Remove a marcação de favorita de uma poesia (define data_favoritado como NULL).
     * @param poesiaId O ID da poesia a ser desfavoritada.
     */
    @Query("UPDATE poesias SET data_favoritado = NULL WHERE id = :poesiaId")
    suspend fun desmarcarComoFavorita(poesiaId: Long)

    /**
     * Marca uma poesia como lida atualizando seu campo data_leitura.
     * @param poesiaId O ID da poesia a ser marcada como lida.
     * @param timestamp O momento atual em que foi marcada como lida.
     */
    @Query("UPDATE poesias SET data_leitura = :timestamp WHERE id = :poesiaId")
    suspend fun marcarComoLida(poesiaId: Long, timestamp: Long)

    /**
     * Remove a marcação de lida de uma poesia (define data_leitura como NULL).
     * @param poesiaId O ID da poesia a ser desmarcada como lida.
     */
    @Query("UPDATE poesias SET data_leitura = NULL WHERE id = :poesiaId")
    suspend fun desmarcarComoLida(poesiaId: Long)

    /**
     * Busca poesias cujo título ou conteúdo contenham o termo de busca.
     * A busca é case-insensitive.
     * @param termoBusca O termo a ser procurado no título ou conteúdo.
     * @return Um Flow contendo uma lista de PoesiaEntity que correspondem ao critério.
     */
    @Query("SELECT * FROM poesias WHERE titulo LIKE '%' || :termoBusca || '%' OR conteudo LIKE '%' || :termoBusca || '%' ORDER BY data_criacao DESC")
    fun buscarPoesias(termoBusca: String): Flow<List<PoesiaEntity>>
}

