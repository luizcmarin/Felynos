// =============================================================================
// Arquivo: com.marin.catfeina.data.entity.PoesiaEntity.kt
// Descrição: Entidade do Room para a tabela de poesias e seu modelo de domínio.
// =============================================================================
package com.marin.catfeina.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marin.catfeina.dominio.CategoriaPoesiaEnum
import kotlin.String

/**
 * Entidade do Room que representa uma poesia na tabela 'poesias'.
 *
 * @property id O ID único do poesia (chave primária, autogerado).
 * @property categoria A categoria do poesia (ex: LÍRICO, ÉPICO). Armazenado como String.
 * @property titulo O título do poesia.
 * @property textoBase Um texto curto ou resumo para exibição em listas/cards.
 * @property conteudo O corpo principal do poesia.
 * @property textoFinal Um texto de conclusão ou citação para exibição em listas/cards.
 * @property dataCriacao Timestamp (Long) da data de criação original do poesia.
 * @property dataFavoritado Timestamp (Long) da última vez que o poesia foi favoritado. Nulo se não favoritado.
 * @property dataLeitura Timestamp (Long) da última vez que o poesia foi marcado como lido. Nulo se não lido.
 * @property campoAudio Nome do arquivo de áudio associado (opcional, ex: "audio_poesia.mp3").
 * @property campoVideo Nome do arquivo de vídeo ou URL associado (opcional, ex: "video_poesia.mp4").
 * @property campoExtra Campo de texto genérico para informações adicionais (opcional).
 * @property campoUrl1 URL externa relacionada ao poesia (opcional).
 * @property campoUrl2 Outra URL externa relacionada ao poesia (opcional).
 * @property imagem URL/Path da imagem de capa principal.
 */
@Entity(
    tableName = "poesias",
    indices = [
        Index(value = ["categoria"]),
        Index(value = ["titulo"]),
        Index(value = ["data_criacao"]),
        Index(value = ["data_favoritado"]),
        Index(value = ["data_leitura"])
    ]
)
data class PoesiaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val categoria: String,
    val titulo: String,

    @ColumnInfo(name = "texto_base")
    val textoBase: String,

    val conteudo: String,

    @ColumnInfo(name = "texto_final")
    val textoFinal: String,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Long,

    @ColumnInfo(name = "data_favoritado")
    val dataFavoritado: Long? = null,

    @ColumnInfo(name = "data_leitura")
    val dataLeitura: Long? = null,

    @ColumnInfo(name = "campo_audio")
    val campoAudio: String? = null,

    @ColumnInfo(name = "campo_video")
    val campoVideo: String? = null,

    @ColumnInfo(name = "campo_extra")
    val campoExtra: String? = null,

    @ColumnInfo(name = "campo_url1")
    val campoUrl1: String? = null,

    @ColumnInfo(name = "campo_url2")
    val campoUrl2: String? = null,

    @ColumnInfo(name = "imagem")
    val imagem: String,
)

/**
 * Modelo de domínio que representa uma poesia na lógica de negócios da aplicação.
 * Utilizado nas camadas de UI e ViewModel.
 *
 * @property id O ID único do poesia.
 * @property categoria A categoria do poesia.
 * @property titulo O título do poesia.
 * @property textoBase Um texto curto ou resumo para exibição.
 * @property conteudo O corpo principal do poesia, em formato Markdown.
 * @property textoFinal Um texto de conclusão ou citação.
 * @property dataCriacao Timestamp da data de criação.
 * @property isFavorito Indica se o poesia está marcado como favorito.
 * @property dataFavoritado Timestamp da última vez que foi favoritado (presente se isFavorito for true).
 * @property isLido Indica se o poesia está marcado como lido.
 * @property dataLeitura Timestamp da última vez que foi lido (presente se isLido for true).
 * @property campoAudio Nome do arquivo de áudio associado.
 * @property campoVideo Nome do arquivo de vídeo ou URL associado.
 * @property campoExtra Campo de texto genérico para informações adicionais.
 * @property campoUrl1 URL externa relacionada.
 * @property campoUrl2 Outra URL externa relacionada.
 * @property imagem URL/Path da imagem de capa principal.
 */
data class Poesia(
    val id: Long,
    val categoria: CategoriaPoesiaEnum = CategoriaPoesiaEnum.POESIA,
    val titulo: String,
    val textoBase: String,
    val conteudo: String,
    val textoFinal: String,
    val dataCriacao: Long,
    val isFavorito: Boolean,
    val dataFavoritado: Long?,
    val isLido: Boolean,
    val dataLeitura: Long?,
    val campoAudio: String?,
    val campoVideo: String?,
    val campoExtra: String?,
    val campoUrl1: String?,
    val campoUrl2: String?,
    var imagem: String,
)

/**
 * Converte a entidade PoesiaEntity (Room) para o modelo de domínio Poesia.
 * Note que a imagemCapaUrl e imagensAdicionaisUrls NÃO são preenchidas aqui,
 * pois requerem dados da ImagemEntity. O Repository será responsável por isso.
 *
 * @return O objeto de domínio Poesia correspondente (sem informações de imagem).
 */
fun PoesiaEntity.toDomain(): Poesia {
    return Poesia(
        id = this.id,
        categoria = try {
            CategoriaPoesiaEnum.valueOf(this.categoria)
        } catch (e: IllegalArgumentException) {
            CategoriaPoesiaEnum.POESIA // Valor padrão em caso de erro
        },
        titulo = this.titulo,
        textoBase = this.textoBase,
        conteudo = this.conteudo,
        textoFinal = this.textoFinal,
        dataCriacao = this.dataCriacao,
        isFavorito = this.dataFavoritado != null,
        dataFavoritado = this.dataFavoritado,
        isLido = this.dataLeitura != null,
        dataLeitura = this.dataLeitura,
        campoAudio = this.campoAudio,
        campoVideo = this.campoVideo,
        campoExtra = this.campoExtra,
        campoUrl1 = this.campoUrl1,
        campoUrl2 = this.campoUrl2,
        imagem =  this.imagem,
    )
}

/**
 * Converte o modelo de domínio Poesia para a entidade PoesiaEntity (Room).
 *
 * @return O objeto de entidade PoesiaEntity correspondente.
 */
fun Poesia.toEntity(): PoesiaEntity {
    return PoesiaEntity(
        id = this.id,
        categoria = this.categoria.name,
        titulo = this.titulo,
        textoBase = this.textoBase,
        conteudo = this.conteudo,
        textoFinal = this.textoFinal,
        dataCriacao = this.dataCriacao,
        dataFavoritado = this.dataFavoritado,
        dataLeitura = this.dataLeitura,
        campoAudio = this.campoAudio,
        campoVideo = this.campoVideo,
        campoExtra = this.campoExtra,
        campoUrl1 = this.campoUrl1,
        campoUrl2 = this.campoUrl2,
        imagem =  this.imagem,
    )
}

