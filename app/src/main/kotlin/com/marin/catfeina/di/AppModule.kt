// =============================================================================
// Arquivo: com.marin.catfeina.di.AppModule.kt
// Descrição: Módulo Hilt unificado para a injeção de dependência em toda a aplicação.
// =============================================================================
package com.marin.catfeina.di

import android.content.Context
import androidx.room.Room
import com.marin.catfeina.data.AppDatabase
import com.marin.catfeina.data.PreferenciasRepository
import com.marin.catfeina.data.dao.PoesiaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Fornece uma instância singleton de PreferenciasRepository.
     */
    @Provides
    @Singleton
    fun providePreferenciasRepository(@ApplicationContext context: Context): PreferenciasRepository {
        return PreferenciasRepository(context)
    }

    /**
     * Fornece uma instância singleton de AppDatabase.
     * @param context O contexto da aplicação injetado pelo Hilt.
     * @return A instância única de AppDatabase.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "catfeina.db"
        )
            .createFromAsset("databases/catfeina.db")
            // .fallbackToDestructiveMigration() // Considere durante o desenvolvimento inicial
            .build()
    }

    /**
     * Fornece uma instância singleton de PoesiaDao.
     * @param appDatabase A instância do banco de dados injetada pelo Hilt.
     * @return A instância única de PoesiaDao.
     */
    @Provides
    @Singleton
    fun providePoesiaDao(appDatabase: AppDatabase): PoesiaDao {
        return appDatabase.poesiaDao()
    }
}

