// =============================================================================
// Arquivo: com.marin.catfeina.dominio.UiEvent.kt
// Descrição: Define os eventos de UI que podem ser enviados do ViewModel
//            para a Screen para executar ações como exibir mensagens ou navegar.
//            Utiliza uma classe selada para um conjunto restrito e seguro de eventos.
// =============================================================================
package com.marin.catfeina.dominio

import androidx.annotation.StringRes

/**
 * Representa eventos de interface do usuário (UI) que são disparados por ViewModels
 * e observados pelas telas (Screens) para executar ações específicas na UI,
 * como exibir mensagens ou realizar navegação.
 *
 * Esta classe selada (sealed class) define um conjunto restrito de eventos possíveis,
 * garantindo um tratamento de eventos mais seguro e previsível.
 */
sealed class UiEvent {

    /**
     * Evento para solicitar a exibição de uma Snackbar na tela.
     *
     * @property messageResId O ID do recurso de string (R.string.*) que contém
     *                        a mensagem a ser exibida na Snackbar.
     * @property args Uma lista opcional de argumentos que serão usados para formatar
     *                a string de `messageResId`, caso ela contenha placeholders
     *                (ex: %1$s, %2$d). Padrão é uma lista vazia.
     */
    data class ShowSnackbar(
        @StringRes val messageResId: Int,
        val args: List<Any> = emptyList()
    ) : UiEvent()

    /**
     * Evento para solicitar a exibição de uma mensagem Toast na tela.
     *
     * @property messageResId O ID do recurso de string (R.string.*) que contém
     *                        a mensagem a ser exibida no Toast.
     * @property args Uma lista opcional de argumentos que serão usados para formatar
     *                a string de `messageResId`, caso ela contenha placeholders.
     *                Padrão é uma lista vazia.
     */
    data class ShowToast(
        @StringRes val messageResId: Int,
        val args: List<Any> = emptyList()
    ) : UiEvent()

    /**
     * Evento para solicitar a navegação para a tela anterior na pilha de navegação
     * (equivalente a uma ação de "voltar" ou "up").
     */
    object NavigateUp : UiEvent()

    // TODO (UiEvent_Expansion): Adicionar outros eventos de UI comuns conforme necessário.
    //  Exemplos:
    //  - Navegação para rotas específicas:
    //    `data class Navigate(val route: String, val popUpToRoute: String? = null, val inclusive: Boolean = false) : UiEvent()`
    //  - Controle de diálogos de carregamento:
    //    `object ShowLoadingDialog : UiEvent()`
    //    `object HideLoadingDialog : UiEvent()`
    //  - Solicitação de permissões:
    //    `data class RequestPermission(val permission: String) : UiEvent()`
    //  - Limpar campos de formulário:
    //    `object ClearForm : UiEvent()`
}
