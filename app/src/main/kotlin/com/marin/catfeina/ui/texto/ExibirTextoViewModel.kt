// =============================================================================
// Arquivo: com.marin.catfeina.ui.texto.ExibirTextoViewModel.kt
// Descrição: ViewModel para a tela que exibe o conteúdo de um Texto específico.
//            Recupera o Texto do TextosRepository com base em uma chave e
//            expõe os dados e o estado da UI para o Fragmento.
// =============================================================================

package com.marin.catfeina.ui.texto

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.data.entity.Texto
import com.marin.catfeina.data.repository.TextosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// Define os possíveis estados da UI para a tela de exibição de texto
sealed interface ExibirTextoUiState {
    data class Success(val texto: Texto) : ExibirTextoUiState
    data object Loading : ExibirTextoUiState
    data class Error(val mensagem: String? = null) : ExibirTextoUiState // Mensagem opcional para o erro
}

@HiltViewModel
class ExibirTextoViewModel @Inject constructor(
    private val textosRepository: TextosRepository,
    private val savedStateHandle: SavedStateHandle // Para acessar argumentos de navegação
) : ViewModel() {

    // Argumento de navegação para a chave do texto. Defina este nome no seu grafo de navegação.
    // Ex: <argument android:name="chave_texto" app:argType="string" />
    private val chaveTexto: String? = savedStateHandle.get<String>("chave_texto")

    private val _uiState = MutableStateFlow<ExibirTextoUiState>(ExibirTextoUiState.Loading)
    val uiState: StateFlow<ExibirTextoUiState> = _uiState.asStateFlow()

    init {
        carregarTexto()
    }

    private fun carregarTexto() {
        if (chaveTexto == null) {
            _uiState.value = ExibirTextoUiState.Error("Chave do texto não fornecida.")
            return
        }

        viewModelScope.launch {
            _uiState.value = ExibirTextoUiState.Loading
            try {
                textosRepository.getTextoPorChave(chaveTexto)
                    .distinctUntilChanged() // Só emite se o valor realmente mudar
                    .collect { texto ->
                        if (texto != null) {
                            _uiState.value = ExibirTextoUiState.Success(texto)
                        } else {
                            _uiState.value = ExibirTextoUiState.Error("Texto não encontrado para a chave: $chaveTexto")
                        }
                    }
            } catch (e: Exception) {
                // Em um app de produção, logar o erro e: e.printStackTrace()
                _uiState.value = ExibirTextoUiState.Error("Falha ao carregar o texto.")
            }
        }
    }

    /**
     * Permite que a UI solicite uma nova tentativa de carregar o texto.
     * Útil se houver uma falha e o usuário quiser tentar novamente.
     */
    fun tentarNovamenteCarregarTexto() {
        // Reseta para Loading antes de tentar novamente, para feedback visual
        if (_uiState.value is ExibirTextoUiState.Error) {
            carregarTexto()
        }
    }
}
