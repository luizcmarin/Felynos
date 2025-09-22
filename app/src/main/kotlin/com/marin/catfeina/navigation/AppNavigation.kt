/*
 * Arquivo: com.marin.catfeina.navigation.AppNavigation.kt
 * @project Catfeina
 * @description
 * Define as rotas de navegação, os destinos (telas placeholder) e a configuração
 * do NavHost para o aplicativo Catfeina.
 */
package com.marin.catfeina.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Objeto para agrupar as rotas de navegação
object AppDestinations {
    const val INICIO_ROUTE = "inicio" // Uma tela inicial/padrão
    const val POESIAS_ROUTE = "poesias"
    const val PERSONAGEM_ROUTE = "personagem"
    const val INFORMATIVOS_ROUTE = "informativos"
    const val PREFERENCIAS_ROUTE = "preferencias"
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

@Composable
fun InformativosScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tela de Informativos")
    }
}

@Composable
fun PreferenciasScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tela de Preferências")
    }
}
