// =============================================================================
// Arquivo: com.marin.catfeina.data.entity.TextoEntity.kt
// Descrição: Entidade Room para a tabela 'textos', seu modelo de domínio 'Texto',
//            e funções de mapeamento entre eles.
// =============================================================================

package com.marin.catfeina.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidade Room que representa um registro na tabela 'textos'.
 * Esta tabela armazena blocos de texto únicos (como política de privacidade,
 * termos de uso, etc.) que são recuperados por uma chave única.
 *
 * @property id O identificador único para o texto (chave primária, autogerada).
 * @property chave A chave textual única que identifica este bloco de texto (ex: "privacidade"). Deve ser única.
 * @property conteudoHtml O conteúdo do texto em formato HTML. Não pode ser nulo.
 * @property conteudoTts O conteúdo do texto otimizado para Text-to-Speech (texto limpo). Não pode ser nulo.
 */
@Entity(
    tableName = "textos",
    indices = [Index(value = ["chave"], unique = true)]
)
data class TextoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0, // SQLite INTEGER autoincrement é geralmente mapeado para Int ou Long. Int é suficiente se não espera bilhões de registros.

    @ColumnInfo(name = "chave")
    val chave: String,

    @ColumnInfo(name = "conteudo_html")
    val conteudoHtml: String,

    @ColumnInfo(name = "conteudo_tts")
    val conteudoTts: String
)

/**
 * Modelo de domínio que representa um Texto na lógica de negócios da aplicação.
 * Utilizado nas camadas de UI e ViewModel.
 *
 * @property id O identificador único para o texto.
 * @property chave A chave textual única que identifica este bloco de texto.
 * @property conteudoHtml O conteúdo do texto em formato HTML.
 * @property conteudoTts O conteúdo do texto otimizado para Text-to-Speech.
 */
data class Texto(
    val id: Int,
    val chave: String,
    val conteudoHtml: String,
    val conteudoTts: String
)

/**
 * Converte a entidade TextoEntity (Room) para o modelo de domínio Texto.
 *
 * @receiver A entidade TextoEntity a ser convertida.
 * @return O objeto de domínio Texto correspondente.
 */
fun TextoEntity.toDomain(): Texto {
    return Texto(
        id = this.id,
        chave = this.chave,
        conteudoHtml = this.conteudoHtml,
        conteudoTts = this.conteudoTts
    )
}

/**
 * Converte o modelo de domínio Texto para a entidade TextoEntity (Room).
 *
 * @receiver O modelo de domínio Texto a ser convertido.
 * @return O objeto de entidade TextoEntity correspondente.
 */
fun Texto.toEntity(): TextoEntity {
    return TextoEntity(
        id = this.id,
        chave = this.chave,
        conteudoHtml = this.conteudoHtml,
        conteudoTts = this.conteudoTts
    )
}

