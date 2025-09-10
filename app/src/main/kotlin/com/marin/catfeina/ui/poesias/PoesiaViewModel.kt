// =============================================================================
// Arquivo: com.marin.catfeina.ui.poesias.PoesiaViewModel.kt
// Descrição: ViewModel para a tela de listagem de Poesias.
//            Responsável por carregar, excluir e interagir com poesias (ex: favoritar, marcar como lida).
//            Utiliza a classe de exceção unificada `ExcecaoApp` para tratamento de erros.
// =============================================================================
package com.marin.catfeina.ui.poesias

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.R
import com.marin.catfeina.data.entity.Poesia // Modelo de domínio
import com.marin.catfeina.dominio.AlternarFavoritoPoesiaAction
import com.marin.catfeina.dominio.AlternarLidoPoesiaAction // Adicionar import
import com.marin.catfeina.dominio.ExcecaoApp
import com.marin.catfeina.dominio.ExcluirPoesiaAction
import com.marin.catfeina.dominio.ObterTodasPoesiasAction
import com.marin.catfeina.dominio.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

// Estado da UI para a tela de LISTA de poesias
data class PoesiaListScreenUiState(
    val poesias: List<Poesia> = emptyList(),
    val isLoading: Boolean = false,
    val itemParaExcluir: Poesia? = null,
    val mostrarDialogoExclusao: Boolean = false,
    @StringRes val globalErrorResId: Int? = null // Para erros que afetam a lista inteira
)

@HiltViewModel
class PoesiaViewModel @Inject constructor(
    private val obterTodasPoesiasAction: ObterTodasPoesiasAction,
    private val excluirPoesiaAction: ExcluirPoesiaAction,
    private val alternarFavoritoPoesiaAction: AlternarFavoritoPoesiaAction,
    private val alternarLidoPoesiaAction: AlternarLidoPoesiaAction // Injetar a nova action
) : ViewModel() {

    private val _uiState = MutableStateFlow(PoesiaListScreenUiState())
    val uiState: StateFlow<PoesiaListScreenUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        loadPoesias()
    }

    fun loadPoesias() { // Público para pull-to-refresh ou retry
        viewModelScope.launch {
            obterTodasPoesiasAction()
                .onStart { _uiState.update { it.copy(isLoading = true, globalErrorResId = null) } }
                .catch { e ->
                    // Utiliza ExcecaoApp para mensagens de erro mais específicas se disponíveis
                    val errorResId = if (e is ExcecaoApp) e.stringResId
                    else R.string.erro_carregar_registros
                    _uiState.update { it.copy(isLoading = false, globalErrorResId = errorResId) }
                    // Log apenas se não for uma ExcecaoApp conhecida (que já pode ter sido logada na origem)
                    if (e !is ExcecaoApp) {
                        Timber.e(e, "Erro desconhecido ao carregar poesias: ${e.message}")
                    } else {
                        // Opcional: Logar ExcecaoApp também, se desejar rastrear quando elas ocorrem aqui.
                        // Log.w("PoesiaVM", "Erro ExcecaoApp ao carregar poesias: ${e.message}", e.cause)
                    }
                }
                .collectLatest { listaPoesias ->
                    _uiState.update { it.copy(isLoading = false, poesias = listaPoesias) }
                }
        }
    }

    fun prepararParaExcluir(poesia: Poesia) {
        _uiState.update {
            it.copy(
                itemParaExcluir = poesia,
                mostrarDialogoExclusao = true,
                globalErrorResId = null // Limpa erro global ao preparar nova ação
            )
        }
    }

    fun confirmarExclusao() {
        val poesiaParaExcluir = _uiState.value.itemParaExcluir ?: return

        viewModelScope.launch {
            try {
                excluirPoesiaAction(poesiaParaExcluir)
                _eventFlow.emit(UiEvent.ShowToast(R.string.mensagem_excluido_com_sucesso))
                // A lista será atualizada automaticamente se obterTodasPoesiasAction for um Flow que reage a mudanças no DB
            } catch (e: ExcecaoApp) {
                _eventFlow.emit(UiEvent.ShowSnackbar(messageResId = e.stringResId ?: R.string.erro_excluir_poesia, args = e.args))
                e.cause?.let { Timber.w(e, "Causa raiz da ExcecaoApp durante exclusão: ${e.message}") }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowSnackbar(R.string.erro_excluir_poesia))
                Timber.e(e, "Erro inesperado ao excluir poesia: ${e.message}")
            } finally {
                cancelarExclusao()
            }
        }
    }

    fun cancelarExclusao() {
        _uiState.update {
            it.copy(
                itemParaExcluir = null,
                mostrarDialogoExclusao = false
            )
        }
    }

    fun toggleFavorito(poesia: Poesia) {
        viewModelScope.launch {
            try {
                // O objeto 'poesia' aqui reflete o estado ANTES da ação.
                val seraFavoritoAposAcao = !poesia.isFavorito
                alternarFavoritoPoesiaAction(poesia) // A Action lida com a lógica de (des)marcar

                // Feedback para o usuário baseado no estado que a poesia terá APÓS a ação.
                val feedbackMsgResId = if (seraFavoritoAposAcao) R.string.mensagem_poesia_favoritada
                else R.string.mensagem_poesia_desfavoritada
                _eventFlow.emit(UiEvent.ShowToast(feedbackMsgResId))
                // A UI será atualizada automaticamente pelo Flow de obterTodasPoesiasAction
            } catch (e: ExcecaoApp) {
                _eventFlow.emit(UiEvent.ShowSnackbar(messageResId = e.stringResId ?: R.string.erro_desconhecido, args = e.args))
                e.cause?.let { Timber.w(e, "Erro (ExcecaoApp) ao alternar favorito: ${e.message}")}
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowSnackbar(R.string.erro_desconhecido))
                Timber.w(e, "\"Erro inesperado ao alternar favorito: ${e.message}")
            }
        }
    }

    // Descomente e implemente quando a funcionalidade de "Lido" for necessária na UI
    fun toggleLido(poesia: Poesia) {
        viewModelScope.launch {
            try {
                // O objeto 'poesia' aqui reflete o estado ANTES da ação.
                val seraLidoAposAcao = !poesia.isLido
                alternarLidoPoesiaAction(poesia) // A Action lida com a lógica de (des)marcar

                // Feedback para o usuário baseado no estado que a poesia terá APÓS a ação.
                val feedbackMsgResId = if (seraLidoAposAcao) R.string.mensagem_poesia_marcada_lida
                else R.string.mensagem_poesia_desmarcada_lida
                _eventFlow.emit(UiEvent.ShowToast(feedbackMsgResId))
                // A UI será atualizada automaticamente pelo Flow de obterTodasPoesiasAction
            } catch (e: ExcecaoApp) {
                _eventFlow.emit(UiEvent.ShowSnackbar(messageResId = e.stringResId ?: R.string.erro_desconhecido, args = e.args))
                e.cause?.let {
                    Timber.w(e, "Erro (ExcecaoApp) ao alternar estado de leitura: ${e.message}") }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowSnackbar(R.string.erro_desconhecido))
                Timber.e(e, "Erro inesperado ao alternar favorito: ${e.message}")
            }
        }
    }
}

