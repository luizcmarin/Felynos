// =============================================================================
// Arquivo: com.marin.catfeina.dominio.ExcecaoApp.kt
// Descrição: Define uma exceção simples para o aplicativo, focada em exibir
//            uma mensagem de erro para o usuário (via recurso de string)
//            e opcionalmente registrar uma causa técnica.
// =============================================================================
package com.marin.catfeina.dominio

import androidx.annotation.StringRes

/**
 * Exceção simplificada para erros gerenciados no aplicativo Catfeina.
 *
 * O objetivo principal é fornecer uma mensagem de erro clara para o usuário
 * através de um recurso de string e, opcionalmente, encapsular a exceção
 * original (`cause`) para fins de logging ou depuração.
 *
 * @property stringResId O ID do recurso de string (R.string.<id>) que contém
 *                       a mensagem de erro a ser exibida ao usuário.
 * @property args Argumentos opcionais para formatar a string do recurso,
 *                se ela contiver placeholders (ex: %1$s, %2$d).
 * @param cause A exceção técnica original que causou este erro, opcional.
 *              A mensagem desta causa pode ser usada para logging.
 */
class ExcecaoApp(
    @StringRes val stringResId: Int,
    val args: List<Any> = emptyList(),
    cause: Throwable? = null
    // A mensagem da Exception base (Exception(message, cause)) usará a mensagem da 'cause', se houver.
    // A mensagem para o usuário virá sempre de stringResId + args.
) : Exception(cause?.message, cause) { // Passa a mensagem da causa para o construtor de Exception

    /**
     * Construtor de conveniência que aceita 'vararg' para os argumentos.
     *
     * @param stringResId O ID do recurso de string.
     * @param argsOsArgumentosDeFormatacao Argumentos para formatar a string do recurso.
     */
    constructor(
        @StringRes stringResId: Int,
        vararg argsOsArgumentosDeFormatacao: Any
    ) : this(
        stringResId = stringResId,
        args = argsOsArgumentosDeFormatacao.toList(),
        cause = null
    )

    /**
     * Construtor de conveniência que aceita 'vararg' para os argumentos e uma 'cause'.
     *
     * @param stringResId O ID do recurso de string.
     * @param causaDoErro A exceção técnica original.
     * @param argsOsArgumentosDeFormatacao Argumentos para formatar a string do recurso.
     */
    constructor(
        @StringRes stringResId: Int,
        causaDoErro: Throwable?,
        vararg argsOsArgumentosDeFormatacao: Any
    ) : this(
        stringResId = stringResId,
        args = argsOsArgumentosDeFormatacao.toList(),
        cause = causaDoErro
    )
}
