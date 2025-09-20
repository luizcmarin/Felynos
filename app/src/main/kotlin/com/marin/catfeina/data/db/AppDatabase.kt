// =============================================================================
// Arquivo: com.marin.catfeina.data.db.AppDatabase.kt
// Descrição: Classe principal do banco de dados Room para a aplicação.
//            Define as entidades, a versão do banco e fornece acesso aos DAOs.
// =============================================================================

package com.marin.catfeina.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marin.catfeina.data.dao.TextoDao
import com.marin.catfeina.data.entity.TextoEntity
// import com.marin.catfeina.data.dao.PoesiaDao
// import com.marin.catfeina.data.entity.PoesiaEntity
// import com.marin.catfeina.data.dao.PersonagemDao
// import com.marin.catfeina.data.entity.PersonagemEntity
// import com.marin.catfeina.data.dao.NotaDao
// import com.marin.catfeina.data.entity.NotaEntity

@Database(
    entities = [
        TextoEntity::class
        // , PoesiaEntity::class
        // , PersonagemEntity::class
        // , NotaEntity::class
    ],
    version = 1, // INCREMENTE se você já tinha uma versão anterior e está mudando o schema
    exportSchema = true // Mantenha como true para exportar o schema para versionamento e migrações
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun textoDao(): TextoDao
    // abstract fun poesiaDao(): PoesiaDao
    // abstract fun personagemDao(): PersonagemDao
    // abstract fun notaDao(): NotaDao

}
