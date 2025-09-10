// =============================================================================
// Arquivo: com.marin.catfeina.ui.poesias.PoesiaDetailViewModel.kt
// Descrição: ViewModel para a tela de detalhes da poesia.
//            Responsável por carregar os dados da poesia selecionada,
//            gerenciar o estado da UI (PoesiaDetailUiState) e lidar com
//            as interações do usuário, como marcar como favorita ou lida.
//            Utiliza Hilt para injeção de dependência e SavedStateHandle
//            para receber o ID da poesia através dos argumentos de navegação.
// =============================================================================
package com.marin.catfeina.ui.poesias

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.R
import com.marin.catfeina.data.PreferenciasRepository
import com.marin.catfeina.data.entity.Poesia
import com.marin.catfeina.data.repository.PoesiaRepository
import com.marin.catfeina.dominio.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Define o estado da UI para a tela de detalhes da poesia.
 *
 * @param poesia A entidade [Poesia] a ser exibida. Nula se ainda não carregada ou não encontrada.
 * @param isLoading Indica se os dados da poesia estão sendo carregados.
 * @param error Mensagem de erro, se ocorrer alguma falha ao carregar os dados.
 */
data class PoesiaDetailUiState(
    val poesia: Poesia? = null,
    val isLoading: Boolean = true,
    val error: String? = null // Usar String Resource ID seria melhor para internacionalização, mas String direto é ok para erros de dev
)

@HiltViewModel
class PoesiaDetailViewModel @Inject constructor(
    private val poesiaRepository: PoesiaRepository,
    private val preferenciasRepository: PreferenciasRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PoesiaDetailUiState())
    val uiState: StateFlow<PoesiaDetailUiState> = _uiState.asStateFlow()
    val fontSizeMultiplier: StateFlow<Float> = preferenciasRepository.fontSizeMultiplierFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 1.0f // Valor inicial importante
        )
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Argumento de navegação esperado: "poesiaId"
    // Validar e obter o ID da poesia. Se for nulo ou inválido, um erro será lançado
    // o que é um comportamento aceitável, pois a tela não pode funcionar sem ele.
    private val poesiaId: Long = savedStateHandle.get<Long>("poesiaId")
        ?: throw IllegalStateException("poesiaId não encontrado nos argumentos de navegação.")

    init {
        Timber.d("ViewModel inicializado para poesiaId: $poesiaId")
        loadPoesiaDetails()
    }

    /**
     * Carrega os detalhes da poesia com base no [poesiaId].
     * Atualiza o [uiState] com os dados carregados, o estado de carregamento ou erros.
     */
    private fun loadPoesiaDetails() {
        viewModelScope.launch {
            _uiState.value = PoesiaDetailUiState(isLoading = true) // Reseta para o estado inicial de carregamento
            poesiaRepository.observarPoesiaPorId(poesiaId)
                .catch { e ->
                    Timber.e(e, "Erro ao observar poesiaId: $poesiaId")
                    _uiState.value = PoesiaDetailUiState(
                        isLoading = false,
                        error = "Erro ao carregar poesia." // Mensagem genérica para o usuário
                    )
                    _eventFlow.emit(UiEvent.ShowSnackbar(R.string.erro_ao_carregar_detalhes))
                }
                .collectLatest { poesia ->
                    if (poesia != null) {
                        _uiState.value = PoesiaDetailUiState(
                            poesia = poesia,
                            isLoading = false
                        )
                    } else {
                        Timber.w("Poesia não encontrada para id: $poesiaId")
                        _uiState.value = PoesiaDetailUiState(
                            isLoading = false,
                            error = "Poesia não encontrada." // Pode ser exibido na UI
                        )
                        // Considerar se um Snackbar é necessário se a UI já mostra "não encontrada"
                        // _eventFlow.emit(UiEvent.ShowSnackbar(R.string.poesia_nao_encontrada))
                    }
                }
        }
    }

    /**
     * Alterna o estado de "favorito" da poesia atual.
     * Envia um [UiEvent] para notificar o usuário sobre o resultado da operação.
     */
    fun toggleFavorito() {
        val currentPoesia = _uiState.value.poesia ?: run {
            Timber.w("Tentativa de alternar favorito com poesia nula.")
            return
        }
        viewModelScope.launch {
            try {
                val isCurrentlyFavorito = currentPoesia.isFavorito
                if (isCurrentlyFavorito) {
                    poesiaRepository.desmarcarComoFavorita(currentPoesia.id)
                } else {
                    poesiaRepository.marcarComoFavorita(currentPoesia.id)
                }
                // O Flow de observarPoesiaPorId deve atualizar a UI automaticamente
                // A mensagem do Snackbar deve refletir a AÇÃO REALIZADA, não o estado anterior.
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        if (isCurrentlyFavorito) R.string.poesia_desmarcada_favorita
                        else R.string.poesia_marcada_favorita
                    )
                )
            } catch (e: Exception) {
                Timber.e(e, "Erro ao alternar favorito para poesiaId: ${currentPoesia.id}")
                _eventFlow.emit(UiEvent.ShowSnackbar(R.string.erro_atualizar_favorito))
            }
        }
    }

    /**
     * Alterna o estado de "lido" da poesia atual.
     * Envia um [UiEvent] para notificar o usuário sobre o resultado da operação.
     */
    fun toggleLido() {
        val currentPoesia = _uiState.value.poesia ?: run {
            Timber.w("Tentativa de alternar lido com poesia nula.")
            return
        }
        viewModelScope.launch {
            try {
                val isCurrentlyLido = currentPoesia.isLido
                if (isCurrentlyLido) {
                    poesiaRepository.desmarcarComoLida(currentPoesia.id)
                } else {
                    poesiaRepository.marcarComoLida(currentPoesia.id)
                }
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        if (isCurrentlyLido) R.string.poesia_desmarcada_lida
                        else R.string.poesia_marcada_lida
                    )
                )
            } catch (e: Exception) {
                Timber.e(e, "Erro ao alternar lido para poesiaId: ${currentPoesia.id}")
                _eventFlow.emit(UiEvent.ShowSnackbar(R.string.erro_atualizar_leitura))
            }
        }
    }
}
