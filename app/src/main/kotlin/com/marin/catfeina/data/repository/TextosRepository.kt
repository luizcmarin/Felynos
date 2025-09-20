// =============================================================================
// Arquivo: com.marin.catfeina.data.repository.TextosRepository.kt
// Descrição: Repositório para gerenciar o acesso aos dados da entidade Texto.
//            Abstrai a fonte de dados (Room DAO) e fornece dados no formato
//            do modelo de domínio.
// =============================================================================

package com.marin.catfeina.data.repository

import com.marin.catfeina.data.dao.TextoDao
import com.marin.catfeina.data.entity.toDomain
import com.marin.catfeina.data.entity.toEntity
import com.marin.catfeina.data.entity.Texto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório para gerenciar dados relacionados a [Texto].
 *
 * Esta classe é responsável por abstrair as fontes de dados (neste caso, o [TextoDao])
 * e fornecer os dados no formato do modelo de domínio [Texto] para as camadas superiores
 * da aplicação (geralmente ViewModels).
 *
 * @property textoDao O Data Access Object para interagir com a tabela 'textos'.
 */
@Singleton // Útil se você quiser que o Hilt forneça a mesma instância do repositório.
class TextosRepository @Inject constructor(
    private val textoDao: TextoDao
) {

    /**
     * Recupera um [Texto] do banco de dados com base na sua chave única.
     *
     * Este método obtém o [TextoEntity] do DAO e o mapeia para o modelo de domínio [Texto].
     * Retorna um Flow que emite o [Texto] encontrado ou null se não existir.
     * O Flow emitirá um novo valor sempre que os dados do texto correspondente mudarem.
     *
     * @param chave A chave única do texto a ser recuperado (ex: "privacidade").
     * @return Um [Flow] que emite o [Texto] correspondente ou null.
     */
    fun getTextoPorChave(chave: String): Flow<Texto?> {
        return textoDao.getTextoPorChave(chave).map { textoEntity ->
            textoEntity?.toDomain() // Mapeia para o modelo de domínio se não for nulo
        }
    }

    /**
     * Insere ou atualiza um [Texto] no banco de dados.
     *
     * Este método converte o modelo de domínio [Texto] para [TextoEntity]
     * antes de passá-lo para o DAO para a operação de upsert.
     *
     * @param texto O [Texto] a ser inserido ou atualizado.
     */
    suspend fun upsertTexto(texto: Texto) {
        // Para usar o upsertTexto, precisamos da entidade.
        // Se o seu modelo de domínio Texto e TextoEntity são idênticos em campos
        // e você tem uma função texto.toEntity(), você pode usá-la aqui.
        // Assumindo que você tem uma função de extensão texto.toEntity()
        textoDao.upsertTexto(texto.toEntity())
    }

    // Se você não quiser expor diretamente o método upsert que aceita TextoEntity
    // do DAO e prefere que o Repository sempre lide com o modelo de domínio Texto,
    // o método acima é o correto.
    //
    // Se, em algum caso específico, você precisar chamar o upsert do DAO com uma
    // TextoEntity diretamente (talvez em um worker de sincronização que já trabalha
    // com entidades), você poderia ter um método separado ou o worker injetaria o DAO.
    // Mas, para consistência da API do Repository, trabalhar com modelos de domínio é o ideal.

}

