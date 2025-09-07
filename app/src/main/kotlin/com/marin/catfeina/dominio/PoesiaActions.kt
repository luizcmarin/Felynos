// =============================================================================
// Arquivo: com.marin.catfeina.dominio.PoesiaActions.kt
// Descrição: Casos de uso/ações para a entidade Poesia.
// =============================================================================
package com.marin.catfeina.dominio

import com.marin.catfeina.data.entity.Poesia
import com.marin.catfeina.data.repository.PoesiaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Ação para obter todas as poesias do banco de dados em um fluxo.
 * O PoesiaRepository já garante que cada Poesia no Flow contenha sua imagem.
 */
open class ObterTodasPoesiasAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    operator fun invoke(): Flow<List<Poesia>> {
        return repository.obterTodasPoesias()
    }
}

/**
 * Ação para observar uma poesia específica pelo seu ID.
 * O PoesiaRepository já garante que a Poesia no Flow contenha sua imagem.
 */
class ObservarPoesiaPorIdAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    operator fun invoke(poesiaId: Long): Flow<Poesia?> {
        return repository.observarPoesiaPorId(poesiaId)
    }
}

/**
 * Ação para obter uma poesia específica pelo seu ID (one-shot).
 * O PoesiaRepository já garante que a Poesia retornada contenha sua imagem.
 */
class ObterPoesiaPorIdAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    suspend operator fun invoke(poesiaId: Long): Poesia? {
        return repository.obterPoesiaPorId(poesiaId)
    }
}


/**
 * Ação para obter todas as poesias de uma categoria específica.
 */
class ObterPoesiasPorCategoriaAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    operator fun invoke(categoria: CategoriaPoesiaEnum): Flow<List<Poesia>> {
        return repository.obterPoesiasPorCategoria(categoria)
    }
}

/**
 * Ação para obter todas as poesias favoritas.
 */
class ObterPoesiasFavoritasAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    operator fun invoke(): Flow<List<Poesia>> {
        return repository.obterPoesiasFavoritas()
    }
}

/**
 * Ação para obter todas as poesias lidas.
 */
class ObterPoesiasLidasAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    operator fun invoke(): Flow<List<Poesia>> {
        return repository.obterPoesiasLidas()
    }
}

/**
 * Ação para buscar poesias por um termo de busca.
 */
class BuscarPoesiasAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    operator fun invoke(termoBusca: String): Flow<List<Poesia>> {
        // Validação de termoBusca pode ser adicionada aqui, se necessário.
        // ex: if (termoBusca.length < 3 && termoBusca.isNotEmpty()) return flowOf(emptyList())
        return repository.buscarPoesias(termoBusca)
    }
}

/**
 * Ação para excluir uma poesia do banco de dados.
 * (ATENÇÃO: Se o banco de dados é empacotado e somente leitura, esta ação não terá efeito prático
 *  ou pode causar um erro se o banco não permitir escrita após a cópia.)
 * Se a exclusão for apenas um "estado lógico" (ex: marcar como excluído sem remover do DB),
 * a lógica precisaria ser diferente.
 * @throws ExcecaoApp se ocorrer erro ao excluir.
 */
open class ExcluirPoesiaAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    suspend operator fun invoke(poesia: Poesia) {
        repository.excluirPoesia(poesia)
    }
}

/**
 * Ação para marcar ou desmarcar uma poesia como favorita.
 * (Os dados de 'favorito' e 'lido' precisam ser persistidos no banco de dados copiado,
 *  o que significa que ele precisa ser gravável após a cópia inicial.)
 */
class AlternarFavoritoPoesiaAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    suspend operator fun invoke(poesia: Poesia) {
        if (poesia.isFavorito) {
            repository.desmarcarComoFavorita(poesia.id)
        } else {
            repository.marcarComoFavorita(poesia.id)
        }
    }
}

/**
 * Ação para marcar ou desmarcar uma poesia como lida.
 */
class AlternarLidoPoesiaAction @Inject constructor(
    private val repository: PoesiaRepository
) {
    suspend operator fun invoke(poesia: Poesia) {
        if (poesia.isLido) {
            repository.desmarcarComoLida(poesia.id)
        } else {
            repository.marcarComoLida(poesia.id)
        }
    }
}
