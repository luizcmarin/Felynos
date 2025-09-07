// Arquivo: com.marin.catfeina.data.repository.PoesiaRepositoryImpl.kt
package com.marin.catfeina.data.repository

import com.marin.catfeina.data.dao.PoesiaDao
import com.marin.catfeina.data.entity.Poesia // Modelo de Domínio
import com.marin.catfeina.data.entity.toDomain // Importar função de mapeamento de PoesiaEntity.kt
import com.marin.catfeina.data.entity.toEntity   // Importar função de mapeamento de PoesiaEntity.kt
import com.marin.catfeina.dominio.CategoriaPoesiaEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PoesiaRepositoryImpl @Inject constructor(
    private val poesiaDao: PoesiaDao
) : PoesiaRepository {

    override fun obterTodasPoesias(): Flow<List<Poesia>> {
        return poesiaDao.obterTodas().map { listaDeEntidadesDb ->
            listaDeEntidadesDb.map { it.toDomain() } // Usando a função de mapeamento
        }
    }

    override fun observarPoesiaPorId(id: Long): Flow<Poesia?> {
        return poesiaDao.observarPorId(id).map { entidadeDb ->
            entidadeDb?.toDomain() // Usando a função de mapeamento
        }
    }

    override suspend fun obterPoesiaPorId(id: Long): Poesia? {
        return poesiaDao.obterPorId(id)?.toDomain() // Usando a função de mapeamento
    }

    override fun obterPoesiasPorCategoria(categoria: CategoriaPoesiaEnum): Flow<List<Poesia>> {
        // O DAO espera uma String, então passamos o nome do enum
        return poesiaDao.obterPorCategoria(categoria.name).map { listaDeEntidadesDb ->
            listaDeEntidadesDb.map { it.toDomain() }
        }
    }

    override fun obterPoesiasFavoritas(): Flow<List<Poesia>> {
        return poesiaDao.obterFavoritas().map { listaDeEntidadesDb ->
            listaDeEntidadesDb.map { it.toDomain() }
        }
    }

    override fun obterPoesiasLidas(): Flow<List<Poesia>> {
        return poesiaDao.obterLidas().map { listaDeEntidadesDb ->
            listaDeEntidadesDb.map { it.toDomain() }
        }
    }

    override fun buscarPoesias(termoBusca: String): Flow<List<Poesia>> {
        return poesiaDao.buscarPoesias(termoBusca).map { listaDeEntidadesDb ->
            listaDeEntidadesDb.map { it.toDomain() }
        }
    }

    override suspend fun inserirOuAtualizarPoesia(poesia: Poesia): Long {
        val entidadeDb = poesia.toEntity() // Mapeando para entidade do DB
        return poesiaDao.inserirOuAtualizar(entidadeDb)
    }

    override suspend fun inserirTodasPoesias(poesias: List<Poesia>) {
        val entidadesDb = poesias.map { it.toEntity() } // Mapeando para lista de entidades do DB
        poesiaDao.inserirTodas(entidadesDb)
    }

    override suspend fun excluirPoesia(poesia: Poesia) {
        poesiaDao.excluir(poesia.toEntity()) // Mapeando para entidade do DB
    }

    override suspend fun excluirTodasAsPoesias() {
        poesiaDao.excluirTodas()
    }

    override suspend fun marcarComoFavorita(poesiaId: Long) {
        // Usamos System.currentTimeMillis() para o timestamp, como definido no DAO
        poesiaDao.marcarComoFavorita(poesiaId, System.currentTimeMillis())
    }

    override suspend fun desmarcarComoFavorita(poesiaId: Long) {
        poesiaDao.desmarcarComoFavorita(poesiaId)
    }

    override suspend fun marcarComoLida(poesiaId: Long) {
        // Usamos System.currentTimeMillis() para o timestamp, como definido no DAO
        poesiaDao.marcarComoLida(poesiaId, System.currentTimeMillis())
    }

    override suspend fun desmarcarComoLida(poesiaId: Long) {
        poesiaDao.desmarcarComoLida(poesiaId)
    }
}
