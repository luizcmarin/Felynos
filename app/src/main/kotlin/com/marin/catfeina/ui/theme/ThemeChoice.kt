/*
 * Arquivo: com.marin.catfeina.ui.theme.ThemeChoice.kt
 * @project Catfeina
 * @description
 * Define as opções de tema selecionáveis pelo usuário para o aplicativo Catfeina.
 * Este enum é usado para determinar qual conjunto de estilos visuais aplicar.
 */
package com.marin.catfeina.ui.theme

/**
 * Representa as diferentes opções de tema de aplicativo que o usuário pode selecionar.
 *
 * Cada valor neste enum corresponde a um conjunto distinto de paletas de cores
 * (e potencialmente tipografia e formas no futuro) que podem ser aplicadas
 * à interface do usuário do aplicativo Catfeina.
 *
 * É utilizado pelo [CatfeinaAppTheme] para determinar qual tema específico
 * (como [com.marin.catfeina.ui.theme.padrao.CatfeinaTheme] ou
 * [com.marin.catfeina.ui.theme.verao.CatfeinaThemeVerao]) deve ser aplicado.
 *
 * A preferência do usuário por um [ThemeChoice] é tipicamente armazenada
 * usando Jetpack DataStore e observada por um ViewModel.
 */
enum class ThemeChoice {
    /**
     * O tema padrão do aplicativo, geralmente com uma paleta de cores baseada em tons de roxo.
     * Corresponde ao [com.marin.catfeina.ui.theme.padrao.CatfeinaTheme].
     */
    PADRAO,

    /**
     * Um tema alternativo com uma paleta de cores vibrante, inspirada no verão.
     * Corresponde ao [com.marin.catfeina.ui.theme.verao.CatfeinaThemeVerao].
     */
    VERAO,
}
