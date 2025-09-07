// =============================================================================
// Arquivo: com.marin.catfeina.data.repository.PoesiaRepository.kt
// Descrição: Interface para o repositório de poesias.
// =============================================================================
package com.marin.catfeina.data.repository

import com.marin.catfeina.data.entity.Poesia // Modelo de domínio
import com.marin.catfeina.dominio.CategoriaPoesiaEnum
import kotlinx.coroutines.flow.Flow

interface PoesiaRepository {

    /**
     * Retorna um Flow de todas as poesias, convertidas para o modelo de domínio.
     * Ordenadas pela data de criação (mais recentes primeiro).
     */
    fun obterTodasPoesias(): Flow<List<Poesia>>

    /**
     * Retorna um Flow de uma poesia específica pelo seu ID, convertida para o modelo de domínio.
     * @param id O ID da poesia.
     */
    fun observarPoesiaPorId(id: Long): Flow<Poesia?>

    /**
     * Retorna uma poesia específica pelo seu ID (operação suspend).
     * @param id O ID da poesia.
     * @return A Poesia (modelo de domínio) ou null se não encontrada.
     */
    suspend fun obterPoesiaPorId(id: Long): Poesia?

    /**
     * Retorna um Flow de todas as poesias de uma categoria específica.
     * @param categoria A categoria para filtrar.
     */
    fun obterPoesiasPorCategoria(categoria: CategoriaPoesiaEnum): Flow<List<Poesia>>

    /**
     * Retorna um Flow de todas as poesias marcadas como favoritas.
     */
    fun obterPoesiasFavoritas(): Flow<List<Poesia>>

    /**
     * Retorna um Flow de todas as poesias marcadas como lidas.
     */
    fun obterPoesiasLidas(): Flow<List<Poesia>>

    /**
     * Busca poesias cujo título ou conteúdo contenham o termo de busca.
     * @param termoBusca O termo a ser procurado.
     */
    fun buscarPoesias(termoBusca: String): Flow<List<Poesia>>

    /**
     * Insere ou atualiza uma poesia no banco de dados.
     * @param poesia O modelo de domínio Poesia a ser inserido/atualizado.
     * @return O ID da poesia inserida/atualizada.
     */
    suspend fun inserirOuAtualizarPoesia(poesia: Poesia): Long

    /**
     * Insere uma lista de poesias no banco de dados.
     * @param poesias Lista de modelos de domínio Poesia.
     */
    suspend fun inserirTodasPoesias(poesias: List<Poesia>)

    /**
     * Exclui uma poesia do banco de dados.
     * @param poesia O modelo de domínio Poesia a ser excluído.
     */
    suspend fun excluirPoesia(poesia: Poesia)

    /**
     * Exclui todas as poesias do banco de dados.
     */
    suspend fun excluirTodasAsPoesias()

    /**
     * Marca uma poesia como favorita.
     * @param poesiaId O ID da poesia.
     */
    suspend fun marcarComoFavorita(poesiaId: Long)

    /**
     * Remove a marcação de favorita de uma poesia.
     * @param poesiaId O ID da poesia.
     */
    suspend fun desmarcarComoFavorita(poesiaId: Long)

    /**
     * Marca uma poesia como lida.
     * @param poesiaId O ID da poesia.
     */
    suspend fun marcarComoLida(poesiaId: Long)

    /**
     * Remove a marcação de lida de uma poesia.
     * @param poesiaId O ID da poesia.
     */
    suspend fun desmarcarComoLida(poesiaId: Long)
}
