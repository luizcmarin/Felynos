// =============================================================================
// Arquivo: com.marin.catfeina.data.AppDatabase.kt
// Descrição: Classe principal do banco de dados Room para a entidade Poesia
//            da aplicação CATSFEINA.
// =============================================================================
package com.marin.catfeina.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marin.catfeina.AppInfo
import com.marin.catfeina.data.dao.PoesiaDao
import com.marin.catfeina.data.entity.PoesiaEntity

/**
 * A classe principal do banco de dados Room para a aplicação Catfeina.
 *
 * Esta classe define a configuração do banco de dados e fornece acesso aos DAOs.
 * Atualmente, gerencia apenas a entidade [PoesiaEntity]. A instanciação singleton
 * desta classe é gerenciada pelo Hilt através de um módulo de injeção de dependência.
 */
@Database(
    entities = [
        PoesiaEntity::class
        // Adicione outras entidades aqui conforme necessário no futuro
    ],
    version = 1, // Se este for o primeiro build com este esquema, 1 está OK.
    // Incremente se você já teve um banco com esquema diferente e precisa de Migrations.
    exportSchema = AppInfo.ROOM_EXPORT_SCHEMA // Define se o schema será exportado para um JSON
)
// @TypeConverters(Conversores::class) // Descomente e configure se você tiver TypeConverters globais
abstract class AppDatabase : RoomDatabase() {

    /**
     * Fornece acesso às operações de dados para a entidade Poesia.
     * @return O DAO para Poesias.
     */
    abstract fun poesiaDao(): PoesiaDao

}
