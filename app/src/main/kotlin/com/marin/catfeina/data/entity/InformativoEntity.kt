/*
 * Arquivo: com.marin.catfeina.data.entity.InformativoEntity.kt
 * @project Catfeina
 * @description Define a entidade Room para a tabela 'informativos' e suas
 *              funções de mapeamento para o modelo de domínio.
 *              Esta entidade representa um texto informativo a ser exibido no aplicativo,
 *              como políticas de privacidade, termos de uso, ou outros conteúdos estáticos
 *              que podem incluir formatação customizada e uma imagem de cabeçalho.
 */
package com.marin.catfeina.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Representa um registro na tabela 'informativos'.
 *
 * @property id Identificador único do informativo (autoincrementado).
 * @property chave Chave textual única usada para buscar o informativo (ex: "politica-de-privacidade").
 * @property imagem Caminho para a imagem de cabeçalho nos assets (ex: "informativos/imagem_informativo.webp"). Pode ser nulo.
 * @property titulo Título do informativo para exibição. Pode ser nulo (embora idealmente não seja para UI).
 * @property conteudo Conteúdo principal do informativo, podendo conter tags de formatação personalizadas.
 */
@Entity(
    tableName = "informativos",
    indices = [Index(value = ["chave"], unique = true)]
)
data class InformativoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "chave")
    val chave: String,

    @ColumnInfo(name = "imagem")
    val imagem: String?,

    @ColumnInfo(name = "titulo")
    val titulo: String?,

    @ColumnInfo(name = "conteudo")
    val conteudo: String,
)

/**
 * Modelo de domínio para um Informativo.
 * Usado nas camadas de ViewModel e UI.
 */
data class Informativo(
    val id: Int,
    val chave: String,
    val imagem: String?,
    val titulo: String?,
    val conteudo: String,
)

/**
 * Converte um [InformativoEntity] (modelo da camada de dados) para um [Informativo] (modelo de domínio).
 */
fun InformativoEntity.toDomain(): Informativo {
    return Informativo(
        id = this.id,
        chave = this.chave,
        imagem = this.imagem,
        titulo = this.titulo,
        conteudo = this.conteudo,
    )
}

/**
 * Converte um [Informativo] (modelo de domínio) para um [InformativoEntity] (modelo da camada de dados).
 * Útil se houver necessidade de inserir ou atualizar dados no banco de dados a partir do domínio.
 */
fun Informativo.toEntity(): InformativoEntity {
    return InformativoEntity(
        id = this.id,
        chave = this.chave,
        imagem = this.imagem,
        titulo = this.titulo,
        conteudo = this.conteudo,
    )
}
