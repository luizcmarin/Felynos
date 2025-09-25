/*
 * Arquivo: com.marin.catfeina.di.DatabaseModule.kt
 * @project Catfeina
 * @description Módulo Hilt para fornecer dependências relacionadas ao banco de dados Room.
 *              Inclui a configuração da instância do AppDatabase (com pré-população)
 *              e dos Data Access Objects (DAOs).
 */
package com.marin.catfeina.di

import android.content.Context
import androidx.room.Room
import com.marin.catfeina.data.dao.InformativoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // As dependências aqui terão escopo de aplicação
object DatabaseModule {

    @Provides
    @Singleton // Garante uma única instância do banco de dados
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .createFromAsset("databases/${AppDatabase.DATABASE_NAME}") // Caminho para o DB nos assets
            // .fallbackToDestructiveMigration() // Adicione se não quiser fornecer migrações manuais
            .build()
    }

    @Provides
    @Singleton // Os DAOs também podem ser singletons, pois são obtidos de um DB singleton
    fun provideInformativoDao(appDatabase: AppDatabase): InformativoDao {
        return appDatabase.informativoDao()
    }

    // Adicione @Provides para outros DAOs aqui no futuro
}
