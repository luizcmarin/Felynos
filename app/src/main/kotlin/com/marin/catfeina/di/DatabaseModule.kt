// =============================================================================
// Arquivo: com.marin.catfeina.di.DatabaseModule.kt
// Descrição: Módulo Hilt para prover dependências relacionadas ao banco de dados Room.
// =============================================================================

package com.marin.catfeina.di

import android.content.Context
import androidx.room.Room
import com.marin.catfeina.data.dao.TextoDao
import com.marin.catfeina.data.db.AppDatabase
import com.marin.catfeina.data.repository.TextosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "catfeina" // Este DEVE ser o nome EXATO do seu arquivo .db nos assets (sem a extensão .db aqui)
        )
            .createFromAsset("databases/catfeina.db")
              // .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Só se você precisar migrar DEPOIS da versão inicial do .db
//            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideTextoDao(appDatabase: AppDatabase): TextoDao {
        return appDatabase.textoDao()
    }

    @Provides
    @Singleton
    fun provideTextosRepository(textoDao: TextoDao): TextosRepository {
        return TextosRepository(textoDao)
    }
    // Adicione provedores para outros DAOs e Repositórios aqui quando forem criados
    // @Provides
    // @Singleton
    // fun providePoesiaDao(appDatabase: AppDatabase): PoesiaDao {
    //     return appDatabase.poesiaDao()
    // }
    //
    // @Provides
    // @Singleton
    // fun providePoesiasRepository(poesiaDao: PoesiaDao): PoesiasRepository {
    //     return PoesiasRepository(poesiaDao)
    // }
}
