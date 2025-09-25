/*
 * Arquivo: com.marin.catfeina.ui.informativo.InformativoViewModel.kt
 * @project Catfeina
 * @description ViewModel para a tela de detalhes de um Informativo.
 *              É responsável por buscar os dados do informativo com base em uma chave,
 *              gerenciar o estado da UI (como o informativo carregado, tamanho da fonte
 *              e tema) e lidar com as interações do usuário relacionadas a estas configurações.
 *              Também processa o conteúdo do informativo para exibição formatada.
 */
package com.marin.catfeina.ui.informativo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.data.entity.Informativo // OK
import com.marin.catfeina.data.repository.InformativoRepository // OK
// import com.marin.catfeina.ui.componentes.textoformatado.ParserTextoFormatado.ElementoConteudo // ANTIGO, PODE CAUSAR ERRO
import com.marin.catfeina.ui.componentes.textoformatado.ElementoConteudo // CORRETO
// import com.marin.catfeina.di.ParserTextoFormatado // LOCAL ERRADO PARA O PARSER EM SI
import com.marin.catfeina.ui.componentes.textoformatado.parser.ParserTextoFormatado // CORRETO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Define o estado da UI para a tela de Informativo
data class InformativoUiState(
    val informativo: Informativo? = null,
    val elementosConteudoFormatado: List<ElementoConteudo> = emptyList(), // Conteúdo processado pelo parser
    val isLoading: Boolean = false,
    val error: String? = null,
    val tooltipParaMostrar: String? = null
    // val tamanhoFonte: Float = 16f, // Exemplo, virá do DataStore
    // val isDarkTheme: Boolean = false // Exemplo, virá do DataStore
)

@HiltViewModel
class InformativoViewModel @Inject constructor(
    private val informativoRepository: InformativoRepository,
    private val parserTextoFormatado: ParserTextoFormatado,
    private val savedStateHandle: SavedStateHandle
    // private val preferenciasRepository: PreferenciasRepository // Comentado, OK
) : ViewModel() {

    private val _uiState = MutableStateFlow(InformativoUiState())
    val uiState: StateFlow<InformativoUiState> = _uiState.asStateFlow()

    private val chaveInformativo: String? = savedStateHandle.get<String>("chaveInformativo")

    init {
        carregarInformativo()
    }

    private fun carregarInformativo() {
        if (chaveInformativo == null) {
            _uiState.update { // Correto usar update
                it.copy(
                    error = "Chave do informativo não fornecida.",
                    isLoading = false
                )
            }
            return
        }

        viewModelScope.launch {
            informativoRepository.getInformativoPorChave(chaveInformativo)
                .onStart {
                    _uiState.update { currentState ->
                        currentState.copy(isLoading = true, error = null)
                    }
                }
                // REMOVIDO onStart DUPLICADO que existia na versão que eu tinha em cache
                .map { informativoEncontrado ->
                    // ERRO POTENCIAL AQUI se informativoEncontrado for null e parserTextoFormatado for chamado
                    val elementos = if (informativoEncontrado?.conteudo != null) {
                        parserTextoFormatado.parse(informativoEncontrado.conteudo)
                    } else {
                        emptyList()
                    }
                    Pair(informativoEncontrado, elementos)
                }
                .catch { exception ->
                    _uiState.update { currentState -> // Usar update para segurança
                        currentState.copy(
                            isLoading = false,
                            error = "Erro ao carregar informativo: ${exception.localizedMessage}"
                        )
                    }
                }
                .collect { (informativoEncontrado, elementosProcessados) ->
                    if (informativoEncontrado != null) {
                        _uiState.update { // Usar update
                            // Se informativoEncontrado é não nulo, elementosProcessados deve ter sido
                            // o resultado do parse do seu conteúdo, ou emptyList se o conteúdo era nulo.
                            // Não precisamos recriar InformativoUiState() do zero aqui se quisermos manter
                            // algum estado anterior (como tooltipParaMostrar)
                            it.copy(
                                informativo = informativoEncontrado,
                                elementosConteudoFormatado = elementosProcessados,
                                isLoading = false,
                                error = null
                            )
                        }
                    } else {
                        _uiState.update { // Usar update
                            it.copy( // Manter estado existente, apenas atualizar o erro e isLoading
                                isLoading = false,
                                error = "Informativo não encontrado para a chave: $chaveInformativo."
                            )
                        }
                    }
                }
        }
    }

    /**
     * Prepara o texto do informativo para ser lido pelo Text-To-Speech,
     * removendo todas as tags de formatação e usando a estrutura ElementoConteudo.
     */
    fun getConteudoParaTTS(): String {
        val elementos = _uiState.value.elementosConteudoFormatado
        val informativoCru = _uiState.value.informativo // Renomeado para evitar confusão

        // Se não há elementos formatados nem informativo base, não há nada para ler.
        if (elementos.isEmpty() && informativoCru?.conteudo == null) {
            return "Conteúdo não disponível."
        }

        // Se os elementos estiverem vazios, mas houver conteúdo cru no informativo,
        // tenta limpar o conteúdo cru como fallback.
        if (elementos.isEmpty() && informativoCru?.conteudo != null) {
            return informativoCru.conteudo
                .replace(Regex("<[^>]*>"), "") // Remove tags HTML/XML genéricas (fallback)
                .replace(Regex("::[^:]*:[^:]*::"), "") // Remove nossas tags customizadas (fallback)
                .trim()
                .ifBlank { "Conteúdo textual não disponível para leitura." } // Ajuste da mensagem
        }

        // Constrói o texto a partir dos elementos processados
        // Esta parte parece correta em lógica.
        return elementos.joinToString(separator = "\n") { elemento ->
            when (elemento) {
                is ElementoConteudo.Paragrafo -> elemento.textoCru // Usar textoCru que não tem AnnotatedString
                is ElementoConteudo.Cabecalho -> elemento.texto
                is ElementoConteudo.Citacao -> elemento.texto // Assumindo que Citacao tem .texto
                is ElementoConteudo.ItemLista -> elemento.textoItem // Assumindo que ItemLista tem .textoItem
                is ElementoConteudo.Imagem -> elemento.textoAlternativo ?: ""
                is ElementoConteudo.LinhaHorizontal -> "" // Linha horizontal não tem texto para TTS
                // Se ElementoConteudo for sealed e todos os tipos cobertos, 'else' não é necessário.
                // Caso contrário, um 'else -> ""' seria prudente.
            }
        }.trim().ifBlank { "Nenhum conteúdo textual para leitura." }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun mostrarTooltip(textoTooltip: String) {
        _uiState.update { it.copy(tooltipParaMostrar = textoTooltip) }
    }

    fun clearTooltip() {
        _uiState.update { it.copy(tooltipParaMostrar = null) }
    }
}

