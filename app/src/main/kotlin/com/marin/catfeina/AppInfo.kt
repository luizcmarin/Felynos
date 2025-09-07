// =============================================================================
// Arquivo: com.marin.catfeina.AppInfo.kt
// Descrição: Objeto Singleton que fornece acesso fácil e centralizado
//            às informações de build do aplicativo, como código da versão,
//            nome da versão, ID da aplicação e tipo de build.
//            Os valores são obtidos diretamente da classe BuildConfig gerada.
// =============================================================================
package com.marin.catfeina

/**
 * Objeto Singleton para acessar informações de build do aplicativo Catfeina.
 *
 * Este objeto serve como um wrapper conveniente para as constantes definidas
 * na classe `BuildConfig` (gerada automaticamente pelo sistema de build do Gradle).
 * Ele permite que outras partes do aplicativo acessem essas informações de forma
 * consistente e centralizada, sem precisar importar `BuildConfig` diretamente
 * em múltiplos locais.
 *
 * Isso pode ser útil para exibir informações da versão na UI, para logging,
 * ou para lógica condicional baseada no tipo de build.
 */
object AppInfo {

    /**
     * O código da versão do aplicativo.
     * Corresponde a `android.defaultConfig.versionCode` no arquivo `build.gradle`.
     * Este é um número inteiro que incrementa a cada nova versão liberada.
     *
     * @see BuildConfig.VERSION_CODE
     */
    const val VERSION_CODE: Int = BuildConfig.VERSION_CODE

    /**
     * O nome da versão do aplicativo, visível para o usuário.
     * Corresponde a `android.defaultConfig.versionName` no arquivo `build.gradle`.
     * Exemplo: "1.0", "1.0.1-beta".
     *
     * @see BuildConfig.VERSION_NAME
     */
    const val VERSION_NAME: String = BuildConfig.VERSION_NAME

    /**
     * O ID único da aplicação.
     * Corresponde a `android.defaultConfig.applicationId` no arquivo `build.gradle`.
     * Exemplo: "com.marin.catfeina".
     *
     * @see BuildConfig.APPLICATION_ID
     */
    const val APPLICATION_ID: String = BuildConfig.APPLICATION_ID

    /**
     * O tipo de build atual do aplicativo.
     * Exemplos comuns são "debug" ou "release". Outros tipos de build podem ser
     * definidos no arquivo `build.gradle`.
     *
     * @see BuildConfig.BUILD_TYPE
     */
    const val BUILD_TYPE: String = BuildConfig.BUILD_TYPE

    /**
     * Indica se o esquema do banco de dados Room deve ser exportado.
     * Este valor é controlado pela configuração `buildConfigField "boolean", "ROOM_EXPORT_SCHEMA", ...`
     * no arquivo `build.gradle.kts`.
     * É útil para desenvolvimento e testes de migração (quando `true`) e pode
     * ser desabilitado para releases (quando `false`).
     *
     * @see BuildConfig.ROOM_EXPORT_SCHEMA
     */
    const val ROOM_EXPORT_SCHEMA: Boolean = BuildConfig.ROOM_EXPORT_SCHEMA
}