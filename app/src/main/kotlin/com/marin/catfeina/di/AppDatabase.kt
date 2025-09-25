/*
 * Arquivo: com.marin.catfeina.di.AppDatabase.kt
 * @project Catfeina
 * @description Define a classe principal do banco de dados Room para o aplicativo.
 *Inclui a configuração das entidades e o método de acesso aos DAOs.
 *              Configurado para usar um banco de dados pré-populado dos assets.
 */
package com.marin.catfeina.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marin.catfeina.data.dao.InformativoDao
import com.marin.catfeina.data.entity.InformativoEntity

@Database(
    entities = [
        InformativoEntity::class
        // Adicione outras entidades aqui no futuro, se necessário
    ],
    version = 1, // Incremente se fizer alterações no esquema que não são retrocompatíveis
    exportSchema = false // Defina como true se quiser exportar o esquema para controle de versão
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun informativoDao(): InformativoDao
    // Adicione outros DAOs abstratos aqui no futuro

    companion object {
        // Nome do arquivo do banco de dados nos assets e o nome que será usado internamente
        const val DATABASE_NAME = "catfeina.db"
    }
}