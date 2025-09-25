/*
 * Arquivo: com.marin.catfeina.navigation.AppNavigation.kt
 * @project Catfeina
 * @description
 * Define as rotas de navegação, os destinos (telas placeholder e funcionais)
 * e a configuração do NavHost para o aplicativo Catfeina.
 */
package com.marin.catfeina

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Objeto para agrupar as rotas de navegação
object AppDestinations {
    const val INICIO_ROUTE = "inicio"
    const val POESIAS_ROUTE = "poesias"
    const val PERSONAGEM_ROUTE = "personagem"

    // Novas rotas para DETALHES do informativo
    const val INFORMATIVO_DETALHE_ROUTE_BASE = "informativo_detalhe"
    const val INFORMATIVO_ARG_CHAVE = "chaveInformativo" // Deve corresponder ao usado no InformativoViewModel
    // Template da rota: informativo_detalhe/{chaveInformativo}
    const val INFORMATIVO_DETALHE_ROUTE_TEMPLATE = "$INFORMATIVO_DETALHE_ROUTE_BASE/{$INFORMATIVO_ARG_CHAVE}"

    const val CHAVE_POLITICA_DE_PRIVACIDADE = "politica-de-privacidade"
    const val CHAVE_TERMOS_DE_USO = "termos-de-uso"

    const val PREFERENCIAS_ROUTE = "preferencias"

    // Função auxiliar para construir a rota de detalhes com a chave
    fun informativoDetalheComChave(chave: String) = "$INFORMATIVO_DETALHE_ROUTE_BASE/$chave"
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tela Inicial (Conteúdo Principal Aqui)")
    }
}

@Composable
fun PoesiasScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tela de Poesias")
    }
}

@Composable
fun PersonagemScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tela de Personagem")
    }
}
